package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextSimilarity {

    public static double getSimilarity(LineData lineA, LineData lineB) {
        //System.out.println("Comparing... "+ lineA.getContent() + "  <->  " + lineB.getContent());
        double contentSimilarity = contentSim(lineA.getContent(), lineB.getContent());
        double contextSimilarity = contextSim(lineA.contextTokens, lineB.contextTokens);
        return combined(contentSimilarity, contextSimilarity);
    }

    // ------------------------------------------------------------
    // CONTENT SIMILARITY (Normalized Levenshtein)
    // ------------------------------------------------------------
    public static double contentSim(String a, String b) {
        if (a.isEmpty() && b.isEmpty())
            return 1.0;

        int dist = levenshtein(a, b);
        int maxLen = Math.max(a.length(), b.length());
        return 1.0 - ((double) dist / maxLen);
    }

    // Standard Levenshtein distance
    private static int levenshtein(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++)
            dp[i][0] = i;
        for (int j = 0; j <= m; j++)
            dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            char c1 = a.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char c2 = b.charAt(j - 1);

                int cost = (c1 == c2) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost);
            }
        }
        return dp[n][m];
    }

    // ------------------------------------------------------------
    // CONTEXT SIMILARITY (Cosine over token-frequency vectors)
    // ------------------------------------------------------------
    public static double contextSim(List<String> a, List<String> b) {
        if (a.isEmpty() && b.isEmpty())
            return 1.0;

        Map<String, Integer> freqA = buildFreq(a);
        Map<String, Integer> freqB = buildFreq(b);

        double dot = 0.0;
        for (String token : freqA.keySet()) {
            if (freqB.containsKey(token)) {
                dot += freqA.get(token) * freqB.get(token);
            }
        }

        double magA = magnitude(freqA);
        double magB = magnitude(freqB);

        if (magA == 0 || magB == 0)
            return 0.0;

        return dot / (magA * magB);
    }

    private static Map<String, Integer> buildFreq(List<String> tokens) {
        Map<String, Integer> map = new HashMap<>();
        for (String t : tokens) {
            map.put(t, map.getOrDefault(t, 0) + 1);
        }
        return map;
    }

    private static double magnitude(Map<String, Integer> freq) {
        double sum = 0.0;
        for (int c : freq.values()) {
            sum += c * c;
        }
        return Math.sqrt(sum);
    }

    // ------------------------------------------------------------
    // COMBINED SIMILARITY (as per LHDiff slide)
    // ------------------------------------------------------------
    public static double combined(double contentSim, double contextSim) {
        double contentProportion = 0.6 * contentSim;
        double contextProportion = 0.4 * contextSim;
        if(Flags.logSimProportions) {
            System.out.printf("Combined Similarity: Content %.4f, Context %.4f, Total %.4f%n",
                    contentProportion, contextProportion, contentProportion + contextProportion);
        }
        return contentProportion + contextProportion;
    }
}
