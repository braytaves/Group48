package main;

import java.util.ArrayList;
import java.util.List;

public class CandidateSelector {
    public static void generateCandidates(FileData left, FileData right, int k) {

        List<LineData> leftLines = left.getLineObjects();
        List<LineData> rightLines = right.getLineObjects();

        for (LineData L : leftLines) {

            // skip ignorable / blank lines
            if (L.isBlankLine || L.wasIdenticallyMatched() ) {
                L.setCandidates(new ArrayList<>());
                continue;
            }

            // compute similarity (via simhash hamming distance) to every right line
            List<Candidate> all = new ArrayList<>();

            for (LineData R : rightLines) {

                if (R.isBlankLine || R.wasIdenticallyMatched())
                    continue; // skip blanks

                // content similarity from simhash
                int cDist = SimHash.hammingDistance(L.getContentHash(), R.getContentHash());
                double cSim = 1.0 - (cDist / 64.0);

                // context similarity from simhash
                int xDist = SimHash.hammingDistance(L.getContextHash(), R.getContextHash());
                double xSim = 1.0 - (xDist / 64.0);

                // combined similarity (same weights as LHDiff step 3)
                double sim = 0.6 * cSim + 0.4 * xSim;

                all.add(new Candidate(R.getIndex(), sim));
            }

            // Sort by similarity DESC
            all.sort((a, b) -> Double.compare(a.sim, b.sim));

            // keep top k only
            List<Integer> topK = new ArrayList<>();
            for (int i = 0; i < Math.min(k, all.size()); i++) {
                topK.add(all.get(i).index);
            }

            // store in LineData
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
