package main;

import java.util.List;
import java.awt.Point;

public class Logger {
    public static void log(FileData file, int s, int e) {
        //display the desired output
        int start = Flags.finalOutputRange ? s - 1 : 0;
        int end = Flags.finalOutputRange ? e : file.getNumberOfLines();
        end = Math.min(end, file.getNumberOfLines());

        List<Point> anchorList = file.getAnchorPoints();
        int lowerAnchorIdx = 0;
        if (Flags.DebugLogger) {
            System.out.println("\n||==== START LOGGER ===||\n");
            for (int i = start; i < end; i++) {
                LineData line = file.getLineObjectAtIndex(i);
                System.out.println("-- " + (i + 1)
                        + " ------------------------------------------------------------------------------------------");
                // System.out.println("BlankLine: " + line.isBlankLine + " IsComment: " +
                // line.isComment);
                // skip blank lines
                if (Flags.onlyLogNonIdentical) {
                    if (line.wasIdenticallyMatched())
                        continue;
                }
                if (line.isBlankLine || line.isComment)
                    continue;
                if (Flags.IdenticalMatch) {
                    if (line.wasIdenticallyMatched()) {
                        System.out.println("Identical match: " + (i + 1) + " --> " + line.getMappings().getFirst());
                    }
                }
                if (Flags.Content)
                    line.PrintContent();
                if (Flags.ContentTokens)
                    line.PrintContentTokens();
                if (Flags.Context)
                    line.PrintContextTokens();
                if (Flags.AnchorRegions) {
                    // init region bounds

                    while (i > anchorList.get(lowerAnchorIdx + 1).x) {
                        lowerAnchorIdx++;
                    }
                    System.out.println("Anchor Region: " + FileData.pointString(anchorList.get(lowerAnchorIdx)) + " | "
                            + FileData.pointString(anchorList.get(lowerAnchorIdx + 1)));

                }
                if (Flags.Candidates && !line.wasIdenticallyMatched())
                    line.printCandidates();

            }
        System.out.println("\n||===== END LOGGER ====||");

        }
        if (Flags.FinalOutput) {
            System.out.println("\n|----Final Results----|\n");
            for (int i = start; i < end; i++) {
                LineData line = file.getLineObjectAtIndex(i);

                List<Integer> mappings = line.getMappings();
                for (Integer mapped : mappings) {
                    System.out.print("line " + (i + 1));
                    System.out.println(" ---> line " + mapped);
                }
            }
        }

    }

    public static void logLine(FileData file, int lineIndex) {
        LineData r = file.getLineObjectAtIndex(lineIndex - 1);

        System.out.println("right " + lineIndex +
                " blank=" + r.isBlankLine +
                " comment=" + r.isComment +
                " ident=" + r.wasIdenticallyMatched());
        r.printCandidates();
    }
}
