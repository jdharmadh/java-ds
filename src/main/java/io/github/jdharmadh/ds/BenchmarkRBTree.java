package io.github.jdharmadh.ds;

import java.util.*;

public class BenchmarkRBTree {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        Random random = new Random();
        int numElements = 1_000_000;
        List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            randomNumbers.add(random.nextInt());
        }
        
        Map<Integer, Integer> wrappedRedBlackTreeMap = new WrappedRedBlackTreeMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            int key = randomNumbers.get(i);
            wrappedRedBlackTreeMap.put(key, key + 10);
        }
        System.out.println("Time taken to insert 1 million random elements in WrappedRedBlackTreeMap: " + (System.currentTimeMillis() - start) + "ms");

        Map<Integer, Integer> redBlackTreeMap = new RedBlackTreeMap<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            int key = randomNumbers.get(i);
            redBlackTreeMap.put(key, key + 10);
        }
        System.out.println("Time taken to insert 1 million random elements in RedBlackTreeMap: " + (System.currentTimeMillis() - start) + "ms");

        Map<Integer, Integer> treeMap = new TreeMap<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            int key = randomNumbers.get(i);
            treeMap.put(key, key + 10);
        }
        System.out.println("Time taken to insert 1 million random elements in Java TreeMap: " + (System.currentTimeMillis() - start) + "ms");
    }
}
