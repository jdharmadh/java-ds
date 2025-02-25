package io.github.jdharmadh.ds.tree;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import io.github.jdharmadh.ds.base.Interval;
import io.github.jdharmadh.ds.tree.AugmentedIntervalTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BenchmarkAugmentedIntervalTreeTest {
    private AugmentedIntervalTree<Integer> tree;
    private static final int NUM_INTERVALS = 1000000;
    private static final int NUM_QUERIES = 1000000;
    private Random random;

    @Before
    public void setUp() {
        tree = new AugmentedIntervalTree<>();
        random = new Random();
    }

    @Test
    public void testInsertAndQueryPerformance() {
        List<Interval<Integer>> whatever = new ArrayList<>();
        // Insert a million random intervals
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_INTERVALS; i++) {
            int start = random.nextInt(NUM_INTERVALS);
            int end = start + random.nextInt(100);
            Interval<Integer> interval = new Interval<>(start, end);
            tree.insert(interval);
            // whatever.add(interval);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to insert " + NUM_INTERVALS + " intervals: " + (endTime - startTime) + " ms");

        // Query a million random points
        startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_QUERIES; i++) {
            int point = random.nextInt(NUM_INTERVALS);
            tree.query(point);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time taken to query " + NUM_QUERIES + " points: " + (endTime - startTime) + " ms");

        
        for (int i = 0; i < NUM_QUERIES; i++) {
            startTime = System.currentTimeMillis();
            int count = 0;
            for (Interval<Integer> interval : whatever) {
                if( interval.intersects(i) ) {
                    count++;
                }
            }
            endTime = System.currentTimeMillis();
            System.out.println("Time taken to query " + i + "th point in a list: " + (endTime - startTime) + " ms");
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time taken to query " + NUM_QUERIES + " points in a list: " + (endTime - startTime) + " ms");
        // Ensure the test passes
        assertTrue(true);
    }
}