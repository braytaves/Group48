package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Preprocessor {

    // Wrapper method for full processing. Input: Path of file to process,
    //  Output: Normalized file as List of Strings (all lines) normalized
    public static List<String> process(String path) {
        List<String> rawLines = new ArrayList<>();
        List<String> processedLines = new ArrayList<>();

        rawLines = readFile(path);      // get Lines from file
        if (rawLines.size() == 0) { // handle empty file
            System.out.println("File at path: " + path + " is empty.");
            return rawLines;
        }
        processedLines = normalize(rawLines);   // normalize
        return processedLines;
    }

    // Normalization of a line normalizes all contiguous sequences of whitespace, like space or tab character
    // to be 1 space long. All characters are converted to lowercase
    public static List<String> normalize(List<String> rawLines) {
    List<String> normalizedLines = new ArrayList<>();

    for (String line : rawLines) {

        // Remove invisible unicode control characters (important)
        line = line.replaceAll("\\p{C}", "");

        // Split into parts as a mutable list
        List<String> parts = new ArrayList<>(Arrays.asList(line.split("\\s+")));

        // Lowercase all parts
        for (int i = 0; i < parts.size(); i++) {
            parts.set(i, parts.get(i).toLowerCase());
        }

        // If line ends with ";" as its own token -> merge it with previous token
        if (!parts.isEmpty() && parts.get(parts.size() - 1).equals(";")) {
            int last = parts.size() - 1;
            parts.set(last - 1, parts.get(last - 1) + ";"); // merge
            parts.remove(last); // remove ";" token
        }

        // Join all remaining parts with single spaces
        String result = String.join(" ", parts);

        normalizedLines.add(result);
    }

    return normalizedLines;
}


    // input: filePath, ouput: List of all lines in the file, as Strings
    public static List<String> readFile(String p) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(p));) {
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
