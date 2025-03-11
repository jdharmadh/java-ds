package io.github.jdharmadh.ds.sketch;

import java.util.HashMap;

public class CountMinEval {
    public static void main(String[] args) {
        testCountMinSketchErrorGuarantee();
    }

    public static void testCountMinSketchErrorGuarantee() {
        double eps = 0.01;
        double delta = 0.05;
        CountMinSketch<String> sketch = new CountMinSketch<>(eps, delta);
        HashMap<String, Integer> truth = new HashMap<>();
        int totalEvents = 1_000_000;

        for (int i = 0; i < 800_000; i++) {
            String key = "unique_" + i;
            sketch.add(key);
            truth.put(key, 1);
        }

        for (int i = 0; i < 10; i++) {
            String key = "hundred_" + i;
            for (int j = 0; j < 100; j++) {
                sketch.add(key);
            }
            truth.put(key, 100);
        }

        for (int i = 0; i < 10; i++) {
            String key = "thousand_" + i;
            for (int j = 0; j < 1000; j++) {
                sketch.add(key);
            }
            truth.put(key, 1000);
        }

        int eventsSoFar = 800_000 + (10 * 100) + (10 * 1000);

        int remaining = totalEvents - eventsSoFar;
        for (int i = 0; i < remaining; i++) {
            String key = "random_" + (i % 100);
            sketch.add(key);
            truth.put(key, truth.getOrDefault(key, 0) + 1);
        }

        int sum = truth.values().stream().mapToInt(Integer::intValue).sum();

        int errorThreshold = (int) (eps * sum);

        int countWithin = 0;
        for (String key : truth.keySet()) {
            int trueCount = truth.get(key);
            int estimate = sketch.query(key);
            if (estimate <= trueCount + errorThreshold) {
                countWithin++;
            }
        }
        double fractionWithin = (double) countWithin / truth.size();
        System.out.println("Fraction of queries within error threshold: " + fractionWithin);

        System.out.println("Fraction within error guarantee is less than expected: " + fractionWithin);
    }
}
