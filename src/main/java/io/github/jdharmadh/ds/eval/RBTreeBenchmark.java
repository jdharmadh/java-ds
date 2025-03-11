package io.github.jdharmadh.ds.eval;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import io.github.jdharmadh.ds.tree.RedBlackTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Benchmarking class to compare the performance of RedBlackTreeMap with
 * TreeMap.
 * Taken from:
 * https://gist.github.com/elimelt/f037fb289b27d53f592e17ae47a30edc#file-redblacktree-java
 */
public class RBTreeBenchmark {
    static class BenchmarkResult {
        final int size;
        final double rbInsertions;
        final double rbLookups;
        final double rbDeletions;
        final double tmInsertions;
        final double tmLookups;
        final double tmDeletions;

        BenchmarkResult(int size, double rbInsertions, double rbLookups, double rbDeletions,
                double tmInsertions, double tmLookups, double tmDeletions) {
            this.size = size;
            this.rbInsertions = rbInsertions;
            this.rbLookups = rbLookups;
            this.rbDeletions = rbDeletions;
            this.tmInsertions = tmInsertions;
            this.tmLookups = tmLookups;
            this.tmDeletions = tmDeletions;
        }
    }

    private static double benchmarkOperation(Runnable operation, int iterations) {
        System.gc(); // request garbage collection before timing
        long startTime = System.nanoTime();
        operation.run();
        long endTime = System.nanoTime();
        return iterations / ((endTime - startTime) / 1_000_000_000.0);
    }

    private static BenchmarkResult runBenchmark(int size, int iterations) {
        Map<Integer, String> rbTree = new RedBlackTreeMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Generate test data
        Integer[] keys = new Integer[size];
        for (int i = 0; i < size; i++) {
            keys[i] = random.nextInt(size * 10);
        }

        // Warmup
        for (int i = 0; i < Math.min(1000, size); i++) {
            int key = keys[i % size];
            rbTree.put(key, "value-" + key);
            treeMap.put(key, "value-" + key);
            rbTree.get(key);
            treeMap.get(key);
            rbTree.remove(key);
            treeMap.remove(key);
        }

        // Benchmark insertions
        double rbInsertions = benchmarkOperation(() -> {
            for (int i = 0; i < iterations; i++) {
                int key = random.nextInt(size * 10);
                rbTree.put(key, "value-" + key);
            }
        }, iterations);

        double tmInsertions = benchmarkOperation(() -> {
            for (int i = 0; i < iterations; i++) {
                int key = random.nextInt(size * 10);
                treeMap.put(key, "value-" + key);
            }
        }, iterations);

        // Benchmark lookups
        double rbLookups = benchmarkOperation(() -> {
            for (int i = 0; i < iterations; i++) {
                rbTree.get(keys[i % size]);
            }
        }, iterations);

        double tmLookups = benchmarkOperation(() -> {
            for (int i = 0; i < iterations; i++) {
                treeMap.get(keys[i % size]);
            }
        }, iterations);

        // Benchmark deletions
        double rbDeletions = benchmarkOperation(() -> {
            for (int i = 0; i < iterations; i++) {
                int key = keys[i % size];
                rbTree.remove(key);
            }
        }, iterations);

        double tmDeletions = benchmarkOperation(() -> {
            for (int i = 0; i < iterations; i++) {
                int key = keys[i % size];
                treeMap.remove(key);
            }
        }, iterations);

        return new BenchmarkResult(size, rbInsertions, rbLookups, rbDeletions,
                tmInsertions, tmLookups, tmDeletions);
    }

    public static void benchmark() {
        List<BenchmarkResult> results = new ArrayList<>();

        // Test different sizes exponentially
        int[] sizes = { 100, 1000, 10_000, 100_000, 1_000_000 };

        System.out.println("Starting benchmarks across different tree sizes...");
        System.out.println("Size\tOperation\tRedBlackTree\tTreeMap\tRB/TM Ratio");
        System.out.println("------------------------------------------------------------");

        for (int size : sizes) {
            int iterations = Math.min(1_000_000, size * 10);
            BenchmarkResult result = runBenchmark(size, iterations);
            results.add(result);

            // Print results in a formatted table
            System.out.printf("%d\tInsertions\t%.2f\t%.2f\t%.2f%%%n",
                    size, result.rbInsertions, result.tmInsertions,
                    (result.rbInsertions / result.tmInsertions) * 100);
            System.out.printf("%d\tLookups\t\t%.2f\t%.2f\t%.2f%%%n",
                    size, result.rbLookups, result.tmLookups,
                    (result.rbLookups / result.tmLookups) * 100);
            System.out.printf("%d\tDeletions\t%.2f\t%.2f\t%.2f%%%n",
                    size, result.rbDeletions, result.tmDeletions,
                    (result.rbDeletions / result.tmDeletions) * 100);
            System.out.println("------------------------------------------------------------");
        }

        // Print summary statistics
        System.out.println("\nPerformance Summary:");
        double avgInsertRatio = results.stream()
                .mapToDouble(r -> r.rbInsertions / r.tmInsertions).average().orElse(0);
        double avgLookupRatio = results.stream()
                .mapToDouble(r -> r.rbLookups / r.tmLookups).average().orElse(0);
        double avgDeleteRatio = results.stream()
                .mapToDouble(r -> r.rbDeletions / r.tmDeletions).average().orElse(0);

        System.out.printf("Average RedBlackTree/TreeMap performance ratios:%n");
        System.out.printf("Insertions: %.2f%%%n", avgInsertRatio * 100);
        System.out.printf("Lookups: %.2f%%%n", avgLookupRatio * 100);
        System.out.printf("Deletions: %.2f%%%n", avgDeleteRatio * 100);

        // Log scaling behavior
        System.out.println("\nScaling Analysis (relative to previous size):");
        for (int i = 1; i < results.size(); i++) {
            BenchmarkResult curr = results.get(i);
            BenchmarkResult prev = results.get(i - 1);
            double sizeFactor = (double) curr.size / prev.size;
            double expectedSlowdown = Math.log(sizeFactor) / Math.log(2); // log2 of size increase

            System.out.printf("Size increase %d -> %d (factor: %.1fx)%n",
                    prev.size, curr.size, sizeFactor);
            System.out.printf("Expected slowdown factor (log2): %.2fx%n", expectedSlowdown);
            System.out.printf("Actual slowdown factors:%n");
            System.out.printf("- RB Insert: %.2fx, TM Insert: %.2fx%n",
                    prev.rbInsertions / curr.rbInsertions,
                    prev.tmInsertions / curr.tmInsertions);
            System.out.printf("- RB Lookup: %.2fx, TM Lookup: %.2fx%n",
                    prev.rbLookups / curr.rbLookups,
                    prev.tmLookups / curr.tmLookups);
            System.out.printf("- RB Delete: %.2fx, TM Delete: %.2fx%n",
                    prev.rbDeletions / curr.rbDeletions,
                    prev.tmDeletions / curr.tmDeletions);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        RBTreeBenchmark.benchmark();
    }
}
