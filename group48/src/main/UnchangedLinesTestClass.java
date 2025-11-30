//Enter in the path of two files
//It will do mapping for unchanged lines only
//Will produce an output like this:

//Line 1 ---> Line 1
//Line 2 ---> Line 2
//Line 3 ---> Line 3
//Line 4 ---> Line 4
//Line 10 ---> Line 5

package main; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UnchangedLinesTestClass {

    // public static void main(String[] args) {
    //     String filePath1 = "data/contributed/Vector_old.java"; //path for file 1
    //     String filePath2 = "data/contributed/Vector_new.java"; //path for file 2

    //     try (
    //         BufferedReader br1 = new BufferedReader(new FileReader(filePath1));
    //     ) {
    //         String line1;
    //         int lineNumber1 = 0;

    //         //Outer loop to go through each line in file1
    //         while ((line1 = br1.readLine()) != null) {
    //             System.out.println(lineNumber1 + ". " + line1);
    //             lineNumber1++;

    //             //Inner loop (compares each line in file1 to file2 until match is found or no match found)
    //             try (BufferedReader br2 = new BufferedReader(new FileReader(filePath2))) {
    //                 String line2;
    //                 int lineNumber2 = 0;

    //                 //look over each line in file2 for a match
    //                 while ((line2 = br2.readLine()) != null) {
    //                     lineNumber2++;

    //                     if (line1.equals(line2)) {
    //                         System.out.println("Line " + lineNumber1 + " ---> Line " + lineNumber2);
    //                         break; //Stop searching file2, move to next line in file1
    //                     }
    //                 }

    //                 //If no match found don't, no output created, move onto next line in file1

    //             } catch (IOException e) {
    //                 e.printStackTrace();
    //             }
    //         }

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}
