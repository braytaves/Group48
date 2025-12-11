package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CandidateSelector {

    public static void generateCandidates(FileData left, FileData right) {
        int nRight = right.getLineObjects().size();

        // 10% of right file, but at least 15, at most 50
        int k = Math.max(15, Math.min((int) (0.10 * nRight), 50));

        List<LineData> leftLines  = left.getLineObjects();
        List<LineData> rightLines = right.getLineObjects();
        List<Point> anchors       = left.getAnchorPoints(); // (leftIdx, rightIdx)

        int nLeft = left.getNumberOfLines();

        for (LineData L : leftLines) {

            // skip ignorable / non-changed lines on the left
            if (L.isBlankLine || L.isComment || L.wasIdenticallyMatched()) {
                L.setCandidates(new ArrayList<>());
                continue;
            }

            int leftIdx = L.getIndex();

            // ---- determine region bounds from anchors ----
            Point prev = new Point(0, 0);                 // default start anchor
            Point next = new Point(nLeft + 1, nRight + 1); // default end anchor

            for (Point p : anchors) {
                if (p.x < leftIdx) {
                    // still before this left line: update prev
                    prev = p;
                } else if (p.x > leftIdx) {
                    // first anchor after this left line: that's our next
                    next = p;
                    break;
                } else {
                    // p.x == leftIdx → this line itself is an anchor → we already skip identicallyMatched
                    // so this case should not matter here
                }
            }

            int rightStart = prev.y + 1;
            int rightEnd   = next.y - 1;

            List<Candidate> all = new ArrayList<>();

            // ---- scan only right lines inside this region ----
            for (LineData R : rightLines) {

                int rIdx = R.getIndex();

                // enforce region firewall
                if (rIdx < rightStart || rIdx > rightEnd) {
                    continue;
                }

                // skip blanks, comments, and already matched lines on the right
                if (R.isBlankLine || R.isComment || R.wasIdenticallyMatched())
                    continue;

                int cDist = SimHash.hammingDistance(L.getContentHash(), R.getContentHash());
                double cSim = 1.0 - (cDist / 64.0);

                int xDist = SimHash.hammingDistance(L.getContextHash(), R.getContextHash());
                double xSim = 1.0 - (xDist / 64.0);

                double sim = 0.6 * cSim + 0.4 * xSim;

                if (Flags.CheckPairSimilarity) {
                    if (R.getIndex() == Main.R_INDEX && L.getIndex() == Main.L_INDEX) {
                        System.out.println("L: " + L.getIndex()
                                + " vs R:" + R.getIndex() + " = " + sim);
                    }
                    if (R.getIndex() == Main.R_INDEX2 && L.getIndex() == Main.L_INDEX) {
                        System.out.println("L: " + L.getIndex()
                                + " vs R:" + R.getIndex() + " = " + sim);
                    }
                }

                all.add(new Candidate(rIdx, sim));
            }

            // sort by similarity DESC (highest first)
            all.sort((a, b) -> Double.compare(b.sim, a.sim));

            List<Integer> topK = new ArrayList<>();
            for (int i = 0; i < Math.min(k, all.size()); i++) {
                topK.add(all.get(i).index);
            }

            L.setCandidates(topK);
        }
    }

    // helper small class
    private static class Candidate {
        int index;
        double sim;

        Candidate(int index, double sim) {
            this.index = index;
            this.sim = sim;
        }
    }
}
