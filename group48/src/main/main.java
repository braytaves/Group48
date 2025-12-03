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
            System.out.println("\n");
                System.out.println("\n");
                
                //STEP 1: PREPROCESSING
                FileData file1 = new FileData(path8);
                FileData file2 = new FileData(path9);
                Preprocessor.process(file1);  //processed files
                Preprocessor.process(file2);  //as lists of strings
                file1.printContents();
                file1.printContexts();
                
                //file1.printContentTokens();
                // for (String line : pFile1){
                //         System.out.println(line);
                // }
                // for (String line : pFile2){
                //         System.out.println(line);
                // }

                //STEP 2: MAPPING UNCHANGED LINES

                UnchangedMapper.map(file1, file2);
                // for (UnchangedLMap mapping : lineMappings){
                //         mapping.PrintMapping();
                // }
                // file1.printMappings();
                // file2.printMappings();
                ContextSimHash.compute(file1);
                ContextSimHash.compute(file2);
                
                ContentSimHash.compute(file1);
                ContentSimHash.compute(file2);

        }
}