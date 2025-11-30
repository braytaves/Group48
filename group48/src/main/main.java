package main; 
import java.util.List;
public class main {
        public static void main(String[] args) {
                String filePath1 = "data/contributed/Vector_old.java"; //path for file 1
                String filePath2 = "data/contributed/Vector_new.java"; //path for file 2
                String filePath3 = "data/contributed/GLRenderer_old.java"; //path for file 1

                //STEP 1: PREPROCESSING
                
                List<String> processedLines = Preprocessor.process(filePath3);

                for (String line : processedLines){
                        System.out.println(line);
                }
        }
}
