package main;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

public class FileData {
    private String path;
    private List<LineData> lineObjects = new ArrayList<>();
    private List<Point> anchorPoints = new ArrayList<>();

    public FileData(String path) {
        this.path = path;
        anchorPoints.add(new Point(0, 0));
    }

    public void printMappings() {
        for (LineData lineData : lineObjects) {
            if (lineData.getDifferenceType() != null && lineData.getDifferenceType().equals("UNCHANGED")) {
                lineData.PrintMapping();
            }
        }
    }
    public void printAnchorPoints() {
        System.out.println("\nAnchor Points of file: '" + path + "'\n");

        for (Point p : anchorPoints) {
            System.out.println(pointString(p));
        }
    }
    public List<Point> getAnchorPoints() {
        return anchorPoints;
    }

    public void addAnchorPoint(Point p) {
        anchorPoints.add(p);
    }

    public void printContextTokens() { // added "Tokens" to variable name to match "printContentTokens" variable
        System.out.println("\nContext tokens of file: '" + path + "'\n");

        for (LineData lineData : lineObjects) {
            lineData.PrintContextTokens();
        }
    }

    public void printContentTokens() {
        for (LineData lineData : lineObjects) {
            lineData.PrintContentTokens();
        }
    }

    public void printContents() {
        System.out.println("Normalized Contents of file: '" + path + "'");
        for (LineData lineData : lineObjects) {
            lineData.PrintContent();
        }
    }

    public String getPath() {
        return path;
    }

    public List<LineData> getLineObjects() {
        return lineObjects;
    }

    public void printCandidates() {
        for (LineData lineData : lineObjects) {
            if (lineData.getCandidates().isEmpty())
                continue;
            lineData.printCandidates();
        }
    }

    public int getNumberOfLines() {
        return lineObjects.size();
    }

    public LineData getLineObjectAtIndex(int index) {
        return lineObjects.get(index);
    }

    public static String pointString(Point p) {
        return String.format("(%d, %d)", p.x, p.y);
    }

    public String getLineContentAtIndex(int index) {
        return lineObjects.get(index).getContent();
    }

    public void setLineObjects(List<LineData> lineObjects) {
        this.lineObjects = lineObjects;
    }

    public void addLineObject(LineData lineData) {
        this.lineObjects.add(lineData);
    }
}