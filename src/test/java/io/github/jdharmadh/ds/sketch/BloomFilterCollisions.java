package io.github.jdharmadh.ds.sketch;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BloomFilterCollisions {
    public static void main(String[] args) {
        BloomFilter<String> bf = new BloomFilter<>(30, 2_500_000);
        int numElements = 5_000_000;
        Set<String> set = new HashSet<>();
        Random random = new Random();

        System.out.println("Inserting " + numElements + " random strings...");
        for (int i = 0; i < numElements; i++) {
            String randomString;
            do {
                int length = random.nextInt(33) + 32; // between 32 and 64
                randomString = generateRandomString(length, random);
            } while (set.contains(randomString));

            set.add(randomString);
            bf.add(randomString);

            if ((i + 1) % 500_000 == 0) {
                System.out.println((i + 1) + " strings inserted");
            }
        }

        System.out.println("Testing for false positives with " + numElements + " random strings...");
        int falsePositives = 0;
        for (int i = 0; i < numElements; i++) {
            int length = random.nextInt(33) + 32; // between 32 and 64
            String randomString = generateRandomString(length, random);

            if (!set.contains(randomString) && bf.contains(randomString)) {
                falsePositives++;
            }

            if ((i + 1) % 500_000 == 0) {
                System.out.println((i + 1) + " strings tested");
            }
        }

        double falsePositiveRate = (double) falsePositives / numElements;
        System.out.println("False positive rate: " + falsePositiveRate);
        System.out.println("Number of false positives: " + falsePositives + " out of " + numElements);
        System.out.println("Testing for false negatives with original elements...");
        int falseNegatives = 0;
        int testedElements = 0;

        for (String element : set) {
            if (!bf.contains(element)) {
                falseNegatives++;
            }

            testedElements++;
            if (testedElements % 500_000 == 0) {
                System.out.println(testedElements + " original elements tested");
            }
        }

        System.out.println("False negative rate: " + (double) falseNegatives / set.size());
        System.out.println("Number of false negatives: " + falseNegatives + " out of " + set.size());
    }

    private static String generateRandomString(int length, Random random) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
