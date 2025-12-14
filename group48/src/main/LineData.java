package main;

import java.util.ArrayList;
import java.util.List;

public class LineData {
    int index;
    private List<Integer> mapsTo = new ArrayList<>();

    String differenceType;
    String content;
    List<String> contentTokens = new ArrayList<>();
    List<String> contextTokens = new ArrayList<>();

    List<Integer> candidates = new ArrayList<>();
    long contentHash;
    long contextHash;
    boolean identicallyMatched = false; //mark lines as resolved after being matched in step 2 (IdenticalLines)
    boolean isBlankLine = false;
    boolean isComment = false;

    public void markBlankLine() {
        isBlankLine = true;
    }
    public void markIdenticallyMatched() {
        identicallyMatched = true;
    }
    public void markComment() {
        isComment = true;
    }
 

    public boolean wasIdenticallyMatched() {
        return identicallyMatched;
    } public void setCandidates(List<Integer> candidateIndices) {
        this.candidates = candidateIndices;
    }

    public LineData(int index, String content, List<String> contentTokens) {
        this.index = index;
        this.content = content;
        this.contentTokens = contentTokens;
    }
    public void printCandidates() {
        System.out.println("Candidates: " + candidates);
    }
    public List<Integer> getCandidates() {
        return candidates;
    }
    public int getIndex() {
        return index;
    }

    public List<Integer> getMappings() {
        return mapsTo;
    }

    public void addMapping(int newIndex) {
        mapsTo.add(newIndex);
    }

    public void printMappings() {
        System.out.println(mapsTo);
    }

    public void clearMappings() {
        mapsTo.clear();
    }

    public String getDifferenceType() {
        return differenceType;
    }

    public void setDifferenceType(String differenceType) {
        this.differenceType = differenceType;
    }

    public String getContent() {
        return content;
    }

    public long getContentHash() {
        return contentHash;
    }
    public void setContentHash(long hash) {
        this.contentHash = hash;
    }
    public void setContextHash(long hash) {
        this.contextHash = hash;
    }
    public void printContentHash() {
        System.out.println("Content Hash of line " + index + ": " + contentHash);
    }

    public long getContextHash() {
        return contextHash;
    }

    public List<String> getContentTokens() {
        return contentTokens;
    }
    public List<String> getContextTokens() {
        return contextTokens;
    }

    public void setContentTokens(List<String> tokens) {
        this.contentTokens = tokens;
    }

    public void setContextTokens(List<String> tokens) {
        this.contextTokens = tokens;
    }

    public void PrintMapping() {
        System.out.println("line " + index + " ---> line " + mapsTo);
    }

    public void PrintContentTokens() {
        if(!contentTokens.isEmpty()) {
        System.out.println("Content Token " + index + ": ");
            for (String token : contentTokens) {
                System.out.print("[ " + token + " ], ");
            }
            System.out.println("");
        }
    }

    public void PrintContent() {
        System.out.println('"'+ content+'\n');
    }

    public void PrintContextTokens() { //added "Tokens" to the variable name to match "PrintContentTokens"
        System.out.print("Context: ");
        for (String token : contextTokens) {
            System.out.print("[ " + token + " ], ");
        }
        System.out.println("\n");
    }
}
