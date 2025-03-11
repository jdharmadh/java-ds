package io.github.jdharmadh.ds.sketch;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import io.github.jdharmadh.ds.util.Utils;

public class BloomFilterEval {
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
                randomString = Utils.generateRandomString(random);
            } while (set.contains(randomString));

            set.add(randomString);
            bf.add(randomString);
        }

        int falsePositives = 0;
        for (int i = 0; i < n; i++) {
            String randomString = Utils.generateRandomString(random);

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
}
