package main;

import java.util.ArrayList;
import java.util.List;

public class ConflictResolver {

    private static final double THRESHOLD = 0.5;

    private static class Pair {
        int leftIdx;
        int rightIdx;
        double sim;

        Pair(int l, int r, double s) {
            this.leftIdx = l;
            this.rightIdx = r;
            this.sim = s;
        }
    }

    public static void resolve(FileData left, FileData right) {
        List<LineData> linesA = left.getLineObjects();
        List<LineData> linesB = right.getLineObjects();

        List<Pair> pairs = new ArrayList<>();

        //build all (left, right, sim) pairs from candidates
        for (LineData lineA : linesA) {
            if (isResolved(lineA)) continue;

            int aIdx = lineA.getIndex();

            for (int candidateIndex : lineA.getCandidates()) {
                LineData lineB = linesB.get(candidateIndex - 1);

                double sim = TextSimilarity.getSimilarity(lineA, lineB);

                if (Flags.logAllCandidateSims) {
                    System.out.printf("%-3d-> %-3d %4.4f%n",
                            aIdx, candidateIndex, sim);
                }

                pairs.add(new Pair(aIdx, candidateIndex, sim));
            }
        }

        //sort all pairs by similarity DESC
        pairs.sort((p1, p2) -> Double.compare(p2.sim, p1.sim));

        boolean[] leftUsed  = new boolean[linesA.size() + 1]; // 1-based
        boolean[] rightUsed = new boolean[linesB.size() + 1];

        //greedily assign best pairs
        for (Pair p : pairs) {
            if (p.sim < THRESHOLD) break; // everything else is worse

            if (leftUsed[p.leftIdx] || rightUsed[p.rightIdx]) continue;

            LineData lineA = linesA.get(p.leftIdx - 1);

            lineA.clearMappings();
            lineA.addMapping(p.rightIdx);

            leftUsed[p.leftIdx] = true;
            rightUsed[p.rightIdx] = true;
        }

        //any unresolved left lines (that aren’t blank/identical) get -1
        for (LineData lineA : linesA) {
            if (isResolved(lineA)) continue;

            int idx = lineA.getIndex();
            if (!leftUsed[idx]) {
                lineA.clearMappings();
                lineA.addMapping(-1);
            }
        }
    }

    private static boolean isResolved(LineData line) {
        return line.wasIdenticallyMatched()
                || line.isBlankLine
                || line.getCandidates().isEmpty();
    }
}

