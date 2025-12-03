package main;

import java.util.List;
import java.util.ArrayList;

public class FileData {
    private String path;
    private List<LineData> lineObjects = new ArrayList<>();

    public FileData(String path) {
        this.path = path;
    }

    public void printMappings() {
        for (LineData lineData : lineObjects) {
            if (lineData.getDifferenceType() != null && lineData.getDifferenceType().equals("UNCHANGED")) {
                lineData.PrintMapping();
            }
        }
    }
    public void printContextsTokens() {
        System.out.println("\nContext tokens of file: '" + path + "'\n");

        for (LineData lineData : lineObjects) {
            lineData.PrintContext();
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

    public int getNumberOfLines() {
        return lineObjects.size();
    }

    public LineData getLineObjectAtIndex(int index) {
        return lineObjects.get(index);
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
