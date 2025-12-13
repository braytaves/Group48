package main;

import java.util.ArrayList;
import java.util.List;

public class SimHash {
    private static final int HASH_BITS = 64;

    public static void compute(FileData fileData) {

        List<LineData> lines = fileData.getLineObjects();

        for (LineData line : lines) {
            
            // Convert tokens to one combined string
            // String contentTokenString = String.join(" ", line.getContentTokens());
            // String contextTokenString = String.join(" ", line.getContextTokens());
            
            long contentHash = getHash(line.getContentTokens());
            long contextHash = getHash(line.getContextTokens());
            
            // long contentHash = getContentSimHash(contentTokenString);
            // long contextHash = getContextSimHash(contextTokenString);

            line.setContentHash(contentHash);
            line.setContextHash(contextHash);

        }
    }

    public static long getHash(List<String> tokens) {
        int[] bitVector = new int[HASH_BITS];

        for (String token : tokens) {
            //long h = fnv(token); // base hashing

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

    private static long getContentSimHash(String text) {
        int[] bitVector = new int[HASH_BITS];
        if (text == null || text.isEmpty())
            return 0; // ignore blank lines

        String[] tokens = text.split("\\s+"); // splits the line into tokens
        for (String token : tokens) {
            //long h = fnv(token); // each token gets a hash number
            long h = token.hashCode(); // base hashing

            for (int i = 0; i < HASH_BITS; i++) {
                long mask = 1L << i;
                if ((h & mask) != 0)
                    bitVector[i] += 1;
                else
                    bitVector[i] -= 1;
            }
        }
        long hash = 0;
        for (int i = 0; i < HASH_BITS; i++) {
            if (bitVector[i] > 0)
                hash |= (1L << i);
        }
        return hash;
    }

    // fnv, apparently better than the build in hash code (this is used in the
    // getSimHash method
    private static long fnv(String s) {
        final long fnv_number = 0x100000001b3L; // special prime number
        long hash = 0xcbf29ce484222325L; // specific offset basis
        // compute the hash number
        for (char c : s.toCharArray()) {
            hash ^= c;
            hash *= fnv_number;
        }
        return hash;
    }

    public static long getContextSimHash(String text) {
        int[] bitVector = new int[HASH_BITS];

        if (text == null || text.isEmpty())
            return 0;

        String[] tokens = text.split("\\s+");

        for (String token : tokens) {
            // long h = fnv(token); // base hashing
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
