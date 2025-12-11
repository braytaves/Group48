//Desired output when comparing asdf_1 to asdf_2:

//Mapping file1 to file2:
//line 1 ---> line 1
//line 2 ---> line 2
//line 3 ---> line 3
//line 5 ---> line 5
//line 7 ---> line -1
//line 9 ---> line -1
//line 11 ---> line 9
//line 12 ---> line 11
package main;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class Main {
        public static final int L_START = 242;
        public static final int L_END = 254;
        public static final int R_START = 159;
        public static final int R_END = 159;
        public static final int L_INDEX = 213;
        public static final int R_INDEX = 159;
        public static final int R_INDEX2 = 458;

        public static List<Point> identicalMatches = new ArrayList<>();

        public static void main(String[] args) {

                // comparing asdf_1 to asdf_2
                String path1 = "data/provided/ASTResolving_1.java";
                String path2 = "data/provided/ASTResolving_2.java";
                String path3 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\asdf_1.java";
                String path4 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\asdf_2.java";
                String path6 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\TabFolder_1.java";
                String path7 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\TabFolder_2.java";
                String path8 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\JavaPerspectiveFactory_1.java";
                String path9 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\JavaPerspectiveFactory_2.java";
                String path10 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\SaveManager_1.java";

                String path11 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\SaveManager_2.java";

                // FileData file1 = new FileData(path8);
                // FileData file2 = new FileData(path9);
                // FileData file1 = new FileData(path3);
                // FileData file2 = new FileData(path4);
                FileData file1 = new FileData(path6);
                FileData file2 = new FileData(path7);

                Preprocessor.process(file1);
                Preprocessor.process(file2);
                if (Flags.normalized) {
                        System.out.println("----- Normalized File 1 -----");
                        file1.printContents();
                        System.out.println("----- Normalized File 2 -----");
                        file2.printContents();
                }
                // file1.printContentTokens();

                // mapping unchanged lines
                IdenticalLines.map(file1, file2);

                // compute SimHash values

                SimHash.compute(file1);
                SimHash.compute(file2);
                CandidateSelector.generateCandidates(file1, file2);
                SimHash.compute(file1);
                // mapping changed lines
                ConflictResolver.resolve(file1, file2);

                int start = 242;
                int end = 254;
                // if(Flags.finalOutput){
                // displayResults(file1, file2, start, end);
                // }
                if (Flags.LoggerEnabled) {
                        Logger.log(file1, L_START, L_END);
                        //Logger.log(file2, R_START, R_END);
                }
                // Logger.logLine(file1, 213);

                // Logger.logLine(file2, 159);

        }

        private static void displayResults(FileData file1, FileData file2, int s, int e) {
                // display the desired output
                System.out.println("\nMapping file1 to file2:");
                int start = Flags.finalOutputRange ? s : 0;
                int end = Flags.finalOutputRange ? e : file1.getNumberOfLines();
                for (int i = start; i < end; i++) {
                        LineData line = file1.getLineObjectAtIndex(i);

                        // skip blank lines
                        if (Flags.onlyLogNonIdentical) {
                                if (line.wasIdenticallyMatched())
                                        continue;
                        }
                        if (line.isBlankLine || line.isComment)
                                continue;

                        List<Integer> mappings = line.getMappings();
                        for (Integer mapped : mappings) {
                                System.out.print("line " + (i + 1));
                                System.out.println(" ---> line " + mapped);
                        }
                }

        }
}
