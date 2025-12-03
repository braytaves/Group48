package main;
import java.util.ArrayList;
import java.util.List;

public class LineData {
    int index;
    int mapsToIndex;
    String differenceType;
    String content;
    List<String> contentTokens = new ArrayList<>();
    List<String> contextTokens = new ArrayList<>();
    long contentHash;
    long contextHash;

    public LineData(int index, String content, List<String> contentTokens){
        this.index = index;
        this.content = content;
        this.contentTokens = contentTokens;
    }
    private void calculateSimhashes(){

    }
    public int getIndex(){
        return index;
    }
    public int getMapsToIndex(){
        return mapsToIndex;
    }
    public void setMapsToIndex(int mapsToIndex){
        this.mapsToIndex = mapsToIndex;
    }
    public String getDifferenceType(){
        return differenceType;
    }
    public void setDifferenceType(String differenceType){
        this.differenceType = differenceType;
    }
    public String getContent(){
        return content;
    }
  
    public long getContentHash(){
        return contentHash;
    }
    public long getContextHash(){
        return contextHash;
    }
    public List<String> getContentTokens(){
        return contentTokens;
    }
    public void setContentTokens(List<String> tokens){
        this.contentTokens = tokens;
    }
    public void setContextTokens(List<String> tokens){
        this.contextTokens = tokens;
    }
    public void PrintMapping(){
        System.out.println("line " + index + " ---> line " + mapsToIndex);
    }
    public void PrintContentTokens(){
        System.out.println("Content Tokens " + index + ": ");
        for (String token : contentTokens){
            System.out.print("[ " +token + " ], ");
        }
        System.out.println("\n");
    }
    public void PrintContent(){
        System.out.println(index + ": " + content);
    }
    public void PrintContextTokens(){ //added "Tokens" to the variable name to match "PrintContentTokens"
        System.out.println("Line " + index + ": ");
        for (String token : contextTokens){
            System.out.print("[ " +token + " ], ");
        }
        System.out.println("\n");
    }
}
