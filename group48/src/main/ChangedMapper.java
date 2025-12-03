package main;

import java.util.List;

public class ChangedMapper {

    private static final double similarity_threshold = 0.7;

    public static void map(FileData file1, FileData file2) {
        List<LineData> lines1 = file1.getLineObjects();
        List<LineData> lines2 = file2.getLineObjects();

        for (int i = 0; i < lines1.size(); i++) { //loop through the lines in file1
            LineData line1 = lines1.get(i);

            //skip the lines that are already mapped from UnchangedMapper2 or that are blank
            if (line1.getMapsToIndex() != 0 || line1.getContent().isBlank()) {
                continue;
            }
            //default value for these two is -1
            double bestSim = -1;
            int bestIndex = -1;

            for (int j = 0; j < lines2.size(); j++) { //loop through the lines in file2
                LineData line2 = lines2.get(j);

                //skip the lines that are already mapped from UnchangedMapper2 or that are blank
                if (line2.getMapsToIndex() != 0 || line2.getContent().isBlank()) {
                    continue;
                }

                //compute the content similarity (used ContentSimHash2 class)
                int contentDist = ContentSimHash.hammingDistance(line1.contentHash, line2.contentHash);
                double contentSim = 1.0 - contentDist / 64.0;

                //compute the context similarity (Uses ContextSimHash class)
                int contextDist = ContextSimHash.hammingDistance(line1.contextHash, line2.contextHash);
                double contextSim = 1.0 - contextDist / 64.0;

                double combinedSim = 0.6 * contentSim + 0.4 * contextSim; //these numbers were used in project powerpoint

                if (combinedSim > bestSim) {
                    bestSim = combinedSim;
                    bestIndex = j;
                }
            }

            // assign mapsToIndex based on best match
            if (bestSim >= similarity_threshold && bestIndex != -1) {
                line1.setMapsToIndex(bestIndex + 1); //add 1 because line numbers start at 1
                lines2.get(bestIndex).setMapsToIndex(i + 1);
            } else {
                line1.setMapsToIndex(-1); //no good match found so might be a deleted line, assign -1
            }
        }
    }
}
