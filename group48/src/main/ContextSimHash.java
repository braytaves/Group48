package main;

import java.util.ArrayList;
import java.util.List;

public class ContextSimHash {

    private static final int CONTEXT_WINDOW = 4;
    private static final int HASH_BITS = 64;

    /**
     * Computes context tokens and context SimHash for every line in FileData.
     * ALSO prints the value for each line.
     */
    public static void compute(FileData fileData) {

        List<LineData> lines = fileData.getLineObjects();
        int n = lines.size();

        System.out.println("\n=== Context SimHash Values for File: " + fileData.getPath() + " ===\n");

        for (int i = 0; i < n; i++) {

            // Build context tokens (top 4 + line + bottom 4)
            List<String> contextTokens = new ArrayList<>();

            // Top context
            for (int t = Math.max(0, i - CONTEXT_WINDOW); t < i; t++) {
                contextTokens.add(lines.get(t).getContent());
            }

            // The line itself
            contextTokens.add(lines.get(i).getContent());

            // Bottom context
            for (int t = i + 1; t <= Math.min(n - 1, i + CONTEXT_WINDOW); t++) {
                contextTokens.add(lines.get(t).getContent());
            }

            // Store tokens inside LineData
            lines.get(i).setContextTokens(contextTokens);

            // Convert tokens to one combined string
            String combined = String.join(" ", contextTokens);

            // Compute SimHash for this context
            long contextHash = simHash(combined);

            // Store inside LineData
            lines.get(i).contextHash = contextHash;

            // === PRINT VALUE HERE ===
            System.out.println("Line " + i + " ContextHash: " + contextHash);
        }

        System.out.println("\n=== End of Context Hash Output ===\n");
    }

    /**
     * 64-bit SimHash implementation.
     */
    public static long simHash(String text) {
        int[] bitVector = new int[HASH_BITS];

        if (text == null || text.isEmpty())
            return 0;

        String[] tokens = text.split("\\s+");

        for (String token : tokens) {
            long h = token.hashCode(); // base hashing

            for (int i = 0; i < HASH_BITS; i++) {
                long mask = 1L << i;
                if ((h & mask) != 0)
                    bitVector[i] += 1;
                else
                    bitVector[i] -= 1;
            }
        }

        long simhash = 0;
        for (int i = 0; i < HASH_BITS; i++) {
            if (bitVector[i] > 0)
                simhash |= (1L << i);
        }

        return simhash;
    }

    /**
     * Hamming distance used for picking best similarity.
     */
    public static int hammingDistance(long a, long b) {
        long x = a ^ b;
        int dist = 0;

        while (x != 0) {
            dist++;
            x &= (x - 1);
        }

        return dist;
    }
}
