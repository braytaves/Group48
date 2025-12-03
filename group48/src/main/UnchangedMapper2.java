//In the asdf example the first 3 lines are the only ones that are unchanged
//this class works for that
//its purpose is to update setMapsToIndex for those lines only, the rest remain 0 still
//ChangedMapper only deals with the lines that still have a value of 0

package main;

public class UnchangedMapper2 {

    //this method simple updates MapsToIndex Variable for unchanged lines
    public static void map(FileData file1, FileData file2) {
        int totalLines = Math.min(file1.getNumberOfLines(), file2.getNumberOfLines());

        for (int i = 0; i < totalLines; i++) {
            LineData line1 = file1.getLineObjectAtIndex(i);
            LineData line2 = file2.getLineObjectAtIndex(i);

            //skip the blank lines
            if (line1.getContent().isEmpty() || line2.getContent().isEmpty()) {
                continue;
            }

            //compare the two lines
            if (line1.getContent().equals(line2.getContent())) {
                //Set the setMapsToIndex attributes
                line1.setMapsToIndex(i + 1);
                line1.setDifferenceType("UNCHANGED");

                line2.setMapsToIndex(i + 1);
                line2.setDifferenceType("UNCHANGED");
            }
        }
    }
}

