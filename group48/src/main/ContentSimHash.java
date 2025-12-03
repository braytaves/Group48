package main;

import java.util.List;

public class ContentSimHash {

    private static final int HASH_BITS = 64;

    /**
     * Computes content SimHash for every line in the file and prints the values.
     */
    public static void compute(FileData fileData) {

        List<LineData> lines = fileData.getLineObjects();

        System.out.println("\n=== Content SimHash Values for File: " + fileData.getPath() + " ===\n");

        for (LineData line : lines) {

            List<String> tokens = line.getContentTokens();

            // Combine into a single string (SimHash works on many tokens)
            String combined = String.join(" ", tokens);

            long hash = simHash(combined);

            // Store hash in LineData
            line.contentHash = hash;

            // Print the result
            System.out.println("Line " + line.getIndex() +
                               " ContentHash: " + hash);
        }

        System.out.println("\n=== End of Content Hash Output for " + fileData.getPath() + " ===\n");
    }


    /**
     * 64-bit SimHash implementation for a string.
     */
    public static long simHash(String text) {
        int[] bitVector = new int[HASH_BITS];

        if (text == null || text.isEmpty())
            return 0L;

        String[] tokens = text.split("\\s+");

        for (String token : tokens) {

            long h = token.hashCode();  // hash of individual token

            for (int i = 0; i < HASH_BITS; i++) {
                long mask = 1L << i;

                if ((h & mask) != 0)
                    bitVector[i] += 1;
                else
                    bitVector[i] -= 1;
            }
        }

        long simhash = 0L;

        for (int i = 0; i < HASH_BITS; i++) {
            if (bitVector[i] > 0)
                simhash |= (1L << i);
        }

        return simhash;
    }


    /**
     * Compute Hamming distance between two content hashes.
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

