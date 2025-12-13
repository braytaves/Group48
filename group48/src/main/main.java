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
        public static final int L_START = 1;          //enable range for logger
        public static final int L_END = 30;            //to only log specific lines


        public static final int R_START = 159;         //FOR DEBUGGING
        public static final int R_END = 159;
        public static final int L_INDEX = 213;
        public static final int R_INDEX = 159;
        public static final int R_INDEX2 = 458;

        public static List<Point> identicalMatches = new ArrayList<>();

        public static void main(String[] args) {

                // Set file paths here
                String path1 = "<path>/<to>/<old_file>.<ext>";
                String path2 = "<path>/<to>/<new_file>.<ext>";
                String path3 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\JavaPerspectiveFactory_1.java";
                String path4 = "C:\\Users\\Matteus\\Desktop\\Uni\\fall\\intro\\Group48\\data\\JavaPerspectiveFactory_2.java";
              
                FileData file1 = new FileData(path3);
                FileData file2 = new FileData(path4);

                Preprocessor.process(file1);
                Preprocessor.process(file2);            

                // mapping unchanged lines
                IdenticalLines.map(file1, file2);

                // compute SimHash values
                SimHash.compute(file1);
                SimHash.compute(file2);

                // generate candidates
                CandidateSelector.generateCandidates(file1, file2);
                
                // mapping changed lines
                ConflictResolver.resolve(file1, file2);

              
                if (Flags.LoggerEnabled) {
                        Logger.log(file1, L_START, L_END);
                }
        }

}
