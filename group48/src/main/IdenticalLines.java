package main;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Line;

import java.util.Collections;

public class IdenticalLines {
    // Use LCS (Longest-Common-Subsequence) Algorithm to determine correct mappings
    public static void map(FileData file1, FileData file2) {
        List<LineData> linesObjs1 = file1.getLineObjects();
        List<LineData> linesObjs2 = file2.getLineObjects();
        int rows = linesObjs1.size() + 1; // # of rows in table
        int cols = linesObjs2.size() + 1; // # of columns in table

        // dp = dynamic programming table
        int[][] dp = new int[rows][cols];

        // BUILD/POPULATE TABLE

        for (int i = 1; i < rows; i++) { // loop for ith line of file 1
            for (int j = 1; j < cols; j++) { // loop for jth line of file 2

                LineData oldLineObj = file1.getLineObjectAtIndex(i - 1);
                LineData newLineObj = file2.getLineObjectAtIndex(j - 1);
                String oldLine = oldLineObj.getContent(); // get ith line of file 1
                String newLine = newLineObj.getContent(); // get jth line of file 2

                if (oldLine.equals(newLine)) { // match
                    dp[i][j] = dp[i - 1][j - 1] + 1; // value of upper-left cell + 1
                } else { // non match
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // max of left and upper cell
                }
            }

        }
        // for(int i = 0; i < rows; i++){ //print db table for debugging
        // for (int j = 0; j < cols; j++){
        // System.out.print(dp[i][j]+" ");
        // }
        // System.out.println();
        // }

        // BACKTRACK THROUGH TABLE ... DETERMINE MAPPINGS

        int row = rows - 1; // start by checking the most bottom right cell of the table
        int col = cols - 1;

        while (row > 0 && col > 0) {
            LineData oldLineObj = file1.getLineObjectAtIndex(row - 1);
            LineData newLineObj = file2.getLineObjectAtIndex(col - 1);
            String oldLine = oldLineObj.getContent(); // get ith line of file 1
            String newLine = newLineObj.getContent(); // get jth line of file 2

            if (oldLine.equals(newLine)) { // match = mapping found !
                // line (row) of file 1 maps to line (col) of file 2
                //System.out.printf("Mapping found: line %d ---> line %d%n", row, col);
                oldLineObj.addMapping(col);
                col--; // move up-left
                row--;
                continue;
            } else {
                // move to cell (left or up) holding larger value, up if tie
                int upVal = dp[row - 1][col];
                int leftVal = dp[row][col - 1];
                if (leftVal > upVal) {
                    col--; // go left
                } else {
                    row--; // go up
                }
            }
        }
    }
}
