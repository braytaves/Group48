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

import java.util.List;

public class main {
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


                // FileData file1 = new FileData(path8);
                // FileData file2 = new FileData(path9);
                // FileData file1 = new FileData(path3);
                // FileData file2 = new FileData(path4);
                FileData file1 = new FileData(path8);
                FileData file2 = new FileData(path9);

                Preprocessor.process(file1);
                Preprocessor.process(file2);
                //file1.printContentTokens();

                // mapping unchanged lines
                IdenticalLines.map(file1, file2);

                // compute SimHash values

                SimHash.compute(file1);
                SimHash.compute(file2);
                CandidateSelector.generateCandidates(file1, file2, 15);
                //file1.printCandidates();
                SimHash.compute(file1);
                // mapping changed lines
                ConflictResolver.resolve(file1, file2);

                //displayResults(file1, file2);
        }

        private static void displayResults(FileData file1, FileData file2) {
                // display the desired output
                System.out.println("\nMapping file1 to file2:");
                for (int i = 0; i < file1.getNumberOfLines(); i++) {
                        LineData line = file1.getLineObjectAtIndex(i);

                        // skip blank lines
                        if (line.getContent().isBlank()) {
                                continue;
                        }
                        List<Integer> mappings = line.getMappings();
                        for (Integer mapped : mappings) {
                                System.out.print("line " + (i + 1));
                                System.out.println(" ---> line " + mapped);
                        }
                }
        }
}
