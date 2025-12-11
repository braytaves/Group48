package main;

import java.util.ArrayList;
import java.util.List;

public class ConflictResolver {

    private static final double THRESHOLD = 0.58;

    public static void resolve(FileData left, FileData right) {

        List<LineData> linesA = left.getLineObjects();
        List<LineData> linesB = right.getLineObjects();
        List<Integer> consumedCandidates = new ArrayList<>();
        for (LineData lineA : linesA) {

            if (isResolved(lineA))
                continue;

            List<Integer> candidates = lineA.getCandidates();

            double bestSim = -1.0;
            int bestRightIdx = -1;

            // --- evaluate ALL candidates ---
            for (int candidateIndex : candidates) {
                if (consumedCandidates.contains(candidateIndex))
                    continue;
                LineData lineB = linesB.get(candidateIndex - 1);

                double sim = TextSimilarity.getSimilarity(lineA, lineB);
                System.out.printf("%-3d-> %-3d %4.4f%n",
                         lineA.getIndex(),
                         candidateIndex,
                         sim);

                if (sim > bestSim) {
                    bestSim = sim;
                    bestRightIdx = candidateIndex;
                }
            }

            // --- assign mapping if threshold passes ---
            if (bestSim >= THRESHOLD) {
                lineA.clearMappings();
                lineA.addMapping(bestRightIdx);
                consumedCandidates.add(bestRightIdx);
            } else {
                lineA.clearMappings(); // no good candidates
                lineA.addMapping(-1); // no good candidates
            }
        }

        // Step 5 (splits) will attach extra right lines to lineA if needed
        // detectPotentialSplits(left, right);
    }

    private static boolean isResolved(LineData line) {
        return (line.wasIdenticallyMatched() || line.isBlankLine || line.getCandidates().isEmpty());
    }
}
