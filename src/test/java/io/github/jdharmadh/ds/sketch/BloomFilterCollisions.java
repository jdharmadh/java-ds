package io.github.jdharmadh.ds.sketch;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BloomFilterCollisions {
    public static void main(String[] args) {
        falsePositiveRate(5_000_000, 30, 2_500_000);
    }

    private static double expectedFPR(int n, int k, int m) {
        return Math.pow(1 - Math.pow(1 - 1.0 / m, n), k);
    }

    private static void falsePositiveRate(int n, int k, int m) {
        BloomFilter<String> bf = new BloomFilter<>(k, m);
        Set<String> set = new HashSet<>();
        Random random = new Random();

        System.out.println("Expected false positive rate: " + String.format("%.3f", expectedFPR(n, k, m)));
        for (int i = 0; i < n; i++) {
            String randomString;
            do {
                int length = random.nextInt(33) + 32; // between 32 and 64
                randomString = generateRandomString(length, random);
            } while (set.contains(randomString));

            set.add(randomString);
            bf.add(randomString);
        }

        int falsePositives = 0;
        for (int i = 0; i < n; i++) {
            int length = random.nextInt(33) + 32; // between 32 and 64
            String randomString = generateRandomString(length, random);

            if (!set.contains(randomString) && bf.contains(randomString)) {
                falsePositives++;
            }
        }

        double falsePositiveRate = (double) falsePositives / n;
        System.out.println("True false positive rate: " + String.format("%.3f", falsePositiveRate));

        for (String element : set) {
            if (!bf.contains(element)) {
                System.out.println("Error: false negative: ");
            }
        }
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
