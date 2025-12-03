//re-did this class because the old class gave
//(    public void g();) a value of 4157841 and (public void g3333333333333333333();) a value of -1070231348
//these hash values are very different from each other
//these lines are supposed to be matched lines according to the asdf.xml file, so their hash values should be more similar
//So I made this class to deal with that problem


package main;

import java.util.List;

public class ContentSimHash2 {

    private static final int HASH_BITS = 64;

    //----------------------------------------------------------------------------------------------------------
    //Updates the contentHash attribute for each line in the given file input
    public static void compute(FileData fileData) {
        List<LineData> lines = fileData.getLineObjects();

        for (LineData line : lines) {
            List<String> tokens = line.getContentTokens();

            //used for changing a token like g333333333333(); to g3();, uses the normalizeToken method defined below
            for (int i = 0; i < tokens.size(); i++) {
                tokens.set(i, normalizeToken(tokens.get(i)));
            }

            String combined = String.join(" ", tokens); //turns normalized tokens back into a single string
            long hash = getSimHash(combined); //Uses stableSim
            line.contentHash = hash; //update the attribute that stores the content hash number for the line
        }
    }
    //----------------------------------------------------------------------------------------------------------
    //method for reducing repeated characters (used once in the compute method above)
    private static String normalizeToken(String token) {
        StringBuilder sb = new StringBuilder();
        char last = 0; //tracks the last character
        int repeatCount = 0; //tracks how many times a character repeats

        for (char c : token.toCharArray()) {
            if (c == last) {
                repeatCount++;
                if (repeatCount < 2) { //keeps only the first repetition
                    sb.append(c);
                }
            } else {
                sb.append(c);
                last = c;
                repeatCount = 0;
            }
        }
        return sb.toString().toLowerCase();
    }
    //----------------------------------------------------------------------------------------------------------
    //method for computing the hash value (used in the compute method above)
    private static long getSimHash(String text) {
        int[] bitVector = new int[HASH_BITS];
        if (text == null || text.isEmpty()) return 0; //ignore blank lines

        String[] tokens = text.split("\\s+"); //splits the line into tokens
        for (String token : tokens) {
            long h = fnv(token); //each token gets a hash number
            for (int i = 0; i < HASH_BITS; i++) {
                long mask = 1L << i;
                if ((h & mask) != 0) bitVector[i] += 1;
                else bitVector[i] -= 1;
            }
        }
        long hash = 0;
        for (int i = 0; i < HASH_BITS; i++) {
            if (bitVector[i] > 0) hash |= (1L << i);
        }
        return hash;
    }
    //----------------------------------------------------------------------------------------------------------
    //fnv, apparently better than the build in hash code (this is used in the getSimHash method
    private static long fnv(String s) {
        final long fnv_number = 0x100000001b3L; //special prime number
        long hash = 0xcbf29ce484222325L; //specific offset basis
        //compute the hash number
        for (char c : s.toCharArray()) {
            hash ^= c;
            hash *= fnv_number;
        }
        return hash;
    }
    //same method as in the other context sim hash class
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
