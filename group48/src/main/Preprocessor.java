package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Preprocessor {

    //Wrapper method for full processing. Input: Path of file to process,
    //Output: Normalized file as List of Strings (all lines) normalized
    public static void process(FileData file) {
        List<String> rawLines = readFile(file.getPath()); //get Lines from file

        if (rawLines.size() == 0) { // handle empty file
            System.out.println("File at path: " + file.getPath() + " is empty.");
            return;
        }
        normalize(file, rawLines);
        distributeContext(file); //distribute context to each line
    }
    //normilization function to remove excess whitespace
    public static void normalize(FileData file, List<String> rawLines) {
    List<String> normalizedLines = new ArrayList<>();
    List<List<String>> contentTokens = new ArrayList<>();
    int index = 1;

    for (String line : rawLines) {
        line = line.replaceAll("\\p{C}", "").trim();

        //If blank → no tokens
        if (line.isEmpty()) {
            LineData ld = new LineData(index, "", new ArrayList<>());
            file.addLineObject(ld);
            normalizedLines.add("");
            ld.markBlankLine();
            contentTokens.add(new ArrayList<>());
            index++;
            continue;
        }

        boolean isComment =
                line.startsWith("//")  ||
                line.startsWith("/*")  ||
                line.startsWith("/**") ||
                line.startsWith("*")   ||    //part of a block comment
                line.startsWith("*/");       //block comment close

        List<String> parts = new ArrayList<>(Arrays.asList(line.split("\\s+")));

        //lowercase
        for (int i = 0; i < parts.size(); i++) {
            parts.set(i, parts.get(i).toLowerCase());
        }

        //merge trailing ";"
        if (!parts.isEmpty() && parts.get(parts.size() - 1).equals(";")) {
            int last = parts.size() - 1;
            parts.set(last - 1, parts.get(last - 1) + ";");
            parts.remove(last);
        }

        String result = String.join(" ", parts);

        LineData ld = new LineData(index, result, parts);

        if (isComment) {
            ld.markComment();
        }

        file.addLineObject(ld);

        normalizedLines.add(result);
        contentTokens.add(parts);
        index++;
    }
}

    
     

    public static void distributeContext(FileData file) {
        List<LineData> lines = file.getLineObjects();
        int total = lines.size();

        for (int i = 0; i < total; i++) {
            List<String> contextTokens = new ArrayList<>();

            //gather lines from i-4 through i+4
            for (int d = -4; d <= 4; d++) {
                int idx = i + d;
                if (idx >= 0 && idx < total) {
                    contextTokens.addAll(lines.get(idx).getContentTokens());
                }
            }

            lines.get(i).setContextTokens(contextTokens);
        }
    }

    //input: filePath, ouput: List of all lines in the file, as Strings
    public static List<String> readFile(String p) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(p));) {
            String line;
            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
