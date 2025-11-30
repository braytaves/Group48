package main; 
import java.util.List;
public class main {
        public static void main(String[] args) {
                String path1 = "data/contributed/Vector_old"; //path for file 1
                String path2 = "data/contributed/Vector_new"; //path for file 2
                String path3 = "data/contributed/GLRenderer_old.java"; //path for file 1
                String path4 = "data/contributed/LCS_test_old"; //path for file 1
                String path5 = "data/contributed/LCS_test_new"; //path for file 1
                String path6 = "data/contributed/LCS_demo_old"; //path for file 1
                String path7 = "data/contributed/LCS_demo_new"; //path for file 1
                String path8 = "data/provided/asdf_1.java"; //path for file 1
                String path9 = "data/provided/asdf_2.java"; //path for file 1

                //STEP 1: PREPROCESSING

                List<String> pFile1 = Preprocessor.process(path6);  //processed files
                List<String> pFile2 = Preprocessor.process(path7);  //as lists of strings

                for (String line : pFile1){
                        System.out.println(line);
                }
                for (String line : pFile2){
                        System.out.println(line);
                }

                //STEP 2: MAPPING UNCHANGED LINES

                List<UnchangedLMap> lineMappings = UnchangedMapper.map(pFile1, pFile2);
                for (UnchangedLMap mapping : lineMappings){
                        mapping.PrintMapping();
                }
        }
}
