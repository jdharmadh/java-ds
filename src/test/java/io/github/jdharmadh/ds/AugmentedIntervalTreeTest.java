package io.github.jdharmadh.ds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class AugmentedIntervalTreeTest {
    private AugmentedIntervalTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new AugmentedIntervalTree<>();
    }

    @Test
    public void testInsertAndQuery() {
        Interval<Integer> i1 = new Interval<>(1, 5);
        Interval<Integer> i2 = new Interval<>(6, 10);
        Interval<Integer> i3 = new Interval<>(11, 15);

        tree.insert(i1);
        tree.insert(i2);
        tree.insert(i3);

        List<Interval<Integer>> result = tree.query(7);
        assertEquals(1, result.size());
        assertTrue(result.contains(i2));

        result = tree.query(12);
        assertEquals(1, result.size());
        assertTrue(result.contains(i3));

        result = tree.query(5);
        assertEquals(1, result.size());
        assertTrue(result.contains(i1));
    }

    @Test
    public void testQueryMultipleIntervals() {
        Interval<Integer> i1 = new Interval<>(1, 5);
        Interval<Integer> i2 = new Interval<>(3, 7);
        Interval<Integer> i3 = new Interval<>(6, 10);

        tree.insert(i1);
        tree.insert(i2);
        tree.insert(i3);

        List<Interval<Integer>> result = tree.query(4);
        assertEquals(2, result.size());
        assertTrue(result.contains(i1));
        assertTrue(result.contains(i2));

        result = tree.query(6);
        assertEquals(2, result.size());
        assertTrue(result.contains(i2));
        assertTrue(result.contains(i3));
    }

    @Test
    public void testQueryNoIntervals() {
        Interval<Integer> i1 = new Interval<>(1, 5);
        Interval<Integer> i2 = new Interval<>(6, 10);

        tree.insert(i1);
        tree.insert(i2);

        List<Interval<Integer>> result = tree.query(11);
        assertTrue(result.isEmpty());

        result = tree.query(0);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertDuplicateIntervals() {
        Interval<Integer> i1 = new Interval<>(1, 5);
        Interval<Integer> i2 = new Interval<>(1, 5);

        tree.insert(i1);
        tree.insert(i2);

        List<Interval<Integer>> result = tree.query(3);
        assertEquals(1, result.size());
        assertTrue(result.contains(i1));
    }

    @Test
    public void testQueryInterval() {
        Interval<Integer> i1 = new Interval<>(1, 5);
        Interval<Integer> i2 = new Interval<>(6, 10);
        Interval<Integer> i3 = new Interval<>(11, 15);

        tree.insert(i1);
        tree.insert(i2);
        tree.insert(i3);

        Interval<Integer> queryInterval = new Interval<>(4, 12);
        List<Interval<Integer>> result = tree.query(queryInterval);
        assertEquals(3, result.size());
        assertTrue(result.contains(i1));
        assertTrue(result.contains(i2));
        assertTrue(result.contains(i3));

        queryInterval = new Interval<>(5, 6);
        result = tree.query(queryInterval);
        assertEquals(2, result.size());
        assertTrue(result.contains(i1));
        assertTrue(result.contains(i2));
    }

    @Test
    public void testQueryIntervalNoMatch() {
        Interval<Integer> i1 = new Interval<>(1, 5);
        Interval<Integer> i2 = new Interval<>(6, 10);

        tree.insert(i1);
        tree.insert(i2);

        Interval<Integer> queryInterval = new Interval<>(11, 15);
        List<Interval<Integer>> result = tree.query(queryInterval);
        assertTrue(result.isEmpty());

        queryInterval = new Interval<>(0, 0);
        result = tree.query(queryInterval);
        assertTrue(result.isEmpty());
    }
}