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

public class main {
        public static void main(String[] args) {

                //comparing asdf_1 to asdf_2
                String path8 = "data/provided/asdf_1.java";
                String path9 = "data/provided/asdf_2.java";
                String path6 = "data/provided/CPListLabelProvider_1.java";
                String path7 = "data/provided/CPListLabelProvider_2.java";


                FileData file1 = new FileData(path8);
                FileData file2 = new FileData(path9);
                FileData file3 = new FileData(path6);
                FileData file4 = new FileData(path7);

                Preprocessor.process(file3);
                Preprocessor.process(file4);

                //mapping unchanged lines
                UnchangedMapper2.map(file1, file2);

                //System.out.println("File 1 mappings after UnchangedMapper2:");
                //for (int i = 0; i < file1.getNumberOfLines(); i++) {
                        //LineData line = file1.getLineObjectAtIndex(i);
                        //System.out.println("Line " + (i + 1) + ": mapsToIndex = " + line.getMapsToIndex());
                //}

                ContentSimHash2.compute(file1);
                ContentSimHash2.compute(file2);
                ContextSimHash.compute(file1);
                ContextSimHash.compute(file2);

                //mapping changed lines
                ChangedMapper.map(file1, file2);

                //show the mapping
                //for (int i = 0; i < file1.getNumberOfLines(); i++) {
                        //LineData line = file1.getLineObjectAtIndex(i);
                        //System.out.println("Line " + (i + 1) + ": mapsToIndex = " + line.getMapsToIndex() +
                                //" | content = \"" + line.getContent() + "\"");
                //}

                //display the desired output
                System.out.println("\nMapping file1 to file2:");
                for (int i = 0; i < file1.getNumberOfLines(); i++) {
                        LineData line = file1.getLineObjectAtIndex(i);

                        //skip blank lines
                        if (line.getContent().isBlank()) {
                                continue;
                        }
                        int mapped = line.getMapsToIndex();
                        System.out.println("line " + (i + 1) + " ---> line " + mapped);
                }
        }
}
