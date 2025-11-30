package main; 
import java.util.List;
public class main {
        public static void main(String[] args) {
                String path1 = "data/contributed/Vector_old"; //path for file 1
                String path2 = "data/contributed/Vector_new"; //path for file 2
                String path3 = "data/contributed/GLRenderer_old.java"; //path for file 1
                String path4 = "data/contributed/LCS_test_old"; //path for file 1
                String path5 = "data/contributed/LCS_test_new"; //path for file 1

                //STEP 1: PREPROCESSING

                List<String> pFile1 = Preprocessor.process(path1);  //processed files
                List<String> pFile2 = Preprocessor.process(path2);  //as lists of strings

                // for (String line : processedFile1){
                //         System.out.println(line);
                // }

                //STEP 2: MAPPING UNCHANGED LINES

                List<UnchangedLMap> lineMappings = UnchangedMapper.map(pFile1, pFile2);
                for (UnchangedLMap mapping : lineMappings){
                        mapping.PrintMapping();
                }
        }
}
