package main;

import java.util.List;

public class MergeDetector {

    // You can tweak this; I started near your sim threshold (0.7)
    private static final double MERGE_SIM_THRESHOLD = 0.75;

    /**
     * Detect cases where multiple consecutive lines in file1
     * have been merged into a single line in file2.
     *
     * Assumes:
     * - UnchangedMapper2 and ChangedMapper already ran.
     * - Deleted lines in file1 currently have mapsToIndex == -1.
     */
    public static void detectMerges(FileData file1, FileData file2) {
        List<LineData> left = file1.getLineObjects();
        List<LineData> right = file2.getLineObjects();

        int nLeft = left.size();
        int nRight = right.size();

        for (int i = 0; i < nLeft; i++) {
            LineData startLine = left.get(i);

            // Only try to "rescue" lines currently considered deleted.
            if (startLine.getMappings().isEmpty()) {
                continue;
            }

            int bestRightIndex = -1;
            double bestSim = 0.0;
            int bestEnd = -1;

            // Try each line on the right as a potential merge target.
            for (int j = 0; j < nRight; j++) {
                LineData rightLine = right.get(j);
                String rightText = rightLine.getContent();

                // Grow a block of consecutive left lines [i..k],
                // as long as they are still "deleted" (mapsToIndex == -1).
                StringBuilder blockBuilder = new StringBuilder();
                double prevSim = -1.0;

                for (int k = i; k < nLeft; k++) {
                    LineData leftK = left.get(k);
                    if (!leftK.getMappings().isEmpty()) {
                        // once we hit a mapped line, stop this block
                        break;
                    }

                    if (blockBuilder.length() > 0) {
                        blockBuilder.append(" ");
                    }
                    blockBuilder.append(leftK.getContent());

                    // double sim = TextSimilarity.normalizedSimilarity(
                    //         blockBuilder.toString(),
                    //         rightText);

                    // Slide condition style: if similarity increases, keep growing; if it drops,
                    // stop.
                    // if (sim >= prevSim) {
                    //     prevSim = sim;

                    //     // Only care about blocks with at least 2 lines (true merge)
                    //     if (k > i && sim > bestSim) {
                    //         bestSim = sim;
                    //         bestRightIndex = j;
                    //         bestEnd = k;
                    //     }
                    // } else {
                    //     break; // similarity decreased
                    // }
                }
            }

            // If we found a good merge block [i..bestEnd] -> right[bestRightIndex]
            if (bestRightIndex != -1 && bestEnd > i && bestSim >= MERGE_SIM_THRESHOLD) {
                int targetLineNum = bestRightIndex + 1; // 1-based

                for (int k = i; k <= bestEnd; k++) {
                    LineData leftK = left.get(k);
                    leftK.addMapping(targetLineNum);
                    // you could optionally set a type:
                    // leftK.setDifferenceType("MERGED");
                }

                LineData rightLine = right.get(bestRightIndex);

                // If right-line has no mappings yet, add the representative original index
                if (rightLine.getMappings().isEmpty()) {
                    rightLine.addMapping(i + 1); // i is start of block
                }

                // Skip over processed block
                i = bestEnd;

            }
        }
    }
}
