package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {


public static String[] readFile(String filename) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
    } catch (IOException e) {
        System.out.println("Error reading file: " + filename);
        e.printStackTrace();
    }
    return lines.toArray(new String[0]);
}

public static void writeFile(String filename, String[] content) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
        for (String line : content) {
            bw.write(line);
            bw.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error writing file: " + filename);
        e.printStackTrace();
    }
}

// public static void main(String[] args) {             //commented out to avoid conflicts from having 2 main methods
//     String[] content = {"Line1", "Line2"};
//     String filename = "your_file.txt";

//     writeFile(filename, content);
//     String[] lines = readFile(filename);

//     for (String line : lines) {
//         System.out.println(line);
//     }
// }

}
