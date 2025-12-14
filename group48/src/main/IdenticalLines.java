package main;

import java.util.List;
import java.util.Collections;

public class IdenticalLines {
    //Use LCS (Longest-Common-Subsequence) Algorithm to determine correct mappings
    public static void map(FileData file1, FileData file2) {
        List<LineData> linesObjs1 = file1.getLineObjects();
        List<LineData> linesObjs2 = file2.getLineObjects();
        int rows = linesObjs1.size() + 1; // # of rows in table
        int cols = linesObjs2.size() + 1; // # of columns in table

        int[][] dp = new int[rows][cols];

        //BUILD / POPULATE TABLE
        for (int i = 1; i < rows; i++) {          // ith line of file1
            LineData oldLineObj = file1.getLineObjectAtIndex(i - 1);
            String oldLine = oldLineObj.getContent();

            boolean skipOld =
                    oldLineObj.isBlankLine ||
                    oldLineObj.isComment;

            for (int j = 1; j < cols; j++) {      // jth line of file2
                LineData newLineObj = file2.getLineObjectAtIndex(j - 1);
                String newLine = newLineObj.getContent();

                if (!skipOld && oldLine.equals(newLine)) { // match
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } 
                else {                                   // non-match
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        //backtrack throug table
        int row = rows - 1; //start at bottom-right
        int col = cols - 1;

        while (row > 0 && col > 0) {
            LineData oldLineObj = file1.getLineObjectAtIndex(row - 1);
            LineData newLineObj = file2.getLineObjectAtIndex(col - 1);
            String oldLine = oldLineObj.getContent();
            String newLine = newLineObj.getContent();

            boolean skipOld =
                    oldLineObj.isBlankLine ||
                    oldLineObj.isComment;

            if (!skipOld && oldLine.equals(newLine)) {

                file1.addAnchorPoint(new java.awt.Point(row, col));
                oldLineObj.addMapping(col);
                oldLineObj.markIdenticallyMatched();

                row--;
                col--;
            } 
            else {
                //Main.identicalMatches.add(new java.awt.Point(-1, -1));

                int upVal = dp[row - 1][col];
                int leftVal = dp[row][col - 1];
                if (leftVal > upVal) {
                    col--; // go left
                } else {
                    row--; // go up
                }
            }
        }
        Collections.reverse(file1.getAnchorPoints());
    }
}
