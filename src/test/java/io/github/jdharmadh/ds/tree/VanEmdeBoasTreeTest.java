package io.github.jdharmadh.ds.tree;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class VanEmdeBoasTreeTest {

    @Test
    public void testInsertAndMinMax() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        assertEquals(-1, tree.min());
        assertEquals(Long.MAX_VALUE, tree.max());

        tree.insert(5);
        assertEquals(5, tree.min());
        assertEquals(5, tree.max());

        tree.insert(3);
        assertEquals(3, tree.min());
        assertEquals(5, tree.max());

        tree.insert(10);
        assertEquals(3, tree.min());
        assertEquals(10, tree.max());
    }

    @Test
    public void testSuccessorBasic() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        tree.insert(3);
        tree.insert(7);
        tree.insert(10);

        assertEquals(3, tree.successor(0));
        assertEquals(3, tree.successor(2));
        assertEquals(7, tree.successor(3));
        assertEquals(7, tree.successor(4));
        assertEquals(10, tree.successor(7));
        assertEquals(16, tree.successor(10)); // Returns universe size when no successor
    }

    @Test
    public void testSuccessorWithDenseData() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        // Insert all numbers from 0 to 15
        for (int i = 0; i < 16; i++) {
            tree.insert(i);
        }

        for (int i = 0; i < 15; i++) {
            assertEquals(i + 1, tree.successor(i));
        }
        assertEquals(16, tree.successor(15)); // No successor for the largest element
    }

    @Test
    public void testMediumTreeWithSparseData() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(256);
        int[] values = { 17, 42, 89, 120, 187, 220 };

        for (int val : values) {
            tree.insert(val);
        }

        assertEquals(17, tree.successor(0));
        assertEquals(42, tree.successor(17));
        assertEquals(89, tree.successor(50));
        assertEquals(120, tree.successor(89));
        assertEquals(187, tree.successor(150));
        assertEquals(220, tree.successor(187));
        assertEquals(256, tree.successor(220));
    }

    @Test
    public void testLargeTreeWithRandomData() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(65536);
        int[] values = { 123, 5678, 10000, 25000, 40000, 60000 };

        for (int val : values) {
            tree.insert(val);
        }

        assertEquals(123, tree.successor(0));
        assertEquals(5678, tree.successor(123));
        assertEquals(10000, tree.successor(5678));
        assertEquals(25000, tree.successor(10000));
        assertEquals(40000, tree.successor(25000));
        assertEquals(60000, tree.successor(40000));
        assertEquals(65536, tree.successor(60000));
    }

    @Test
    public void testInsertDuplicates() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        tree.insert(5);
        tree.insert(5); // Insert duplicate

        assertEquals(5, tree.min());
        assertEquals(5, tree.max());
        assertEquals(16, tree.successor(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSuccessorOutOfRangeNegative() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        tree.insert(5);
        tree.successor(-1); // Should throw IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSuccessorOutOfRangePositive() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        tree.insert(5);
        tree.successor(17); // Should throw IllegalArgumentException
    }

    @Test
    public void testSuccessorWithNoElements() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        assertEquals(16, tree.successor(5)); // Should return universe size
    }

    @Test
    public void testComplexSequence() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(256);

        tree.insert(100);
        assertEquals(100, tree.min());
        assertEquals(100, tree.max());

        tree.insert(50);
        assertEquals(50, tree.min());
        assertEquals(100, tree.max());

        tree.insert(150);
        assertEquals(50, tree.min());
        assertEquals(150, tree.max());

        assertEquals(50, tree.successor(0));
        assertEquals(100, tree.successor(50));
        assertEquals(150, tree.successor(100));
        assertEquals(256, tree.successor(150));

        tree.insert(75);
        assertEquals(75, tree.successor(50));
        assertEquals(100, tree.successor(75));
    }

    @Test
    public void testBoundaryValues() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(16);
        tree.insert(0);
        tree.insert(15);

        assertEquals(0, tree.min());
        assertEquals(15, tree.max());
        assertEquals(15, tree.successor(0));
        assertEquals(16, tree.successor(15));
    }

    @Test
    public void testManyConsecutiveInsertions() {
        VanEmdeBoasTree tree = new VanEmdeBoasTree(256);
        // Insert multiples of 10
        for (int i = 0; i <= 250; i += 10) {
            tree.insert(i);
        }

        for (int i = 0; i < 250; i += 10) {
            assertEquals(i + 10, tree.successor(i));
        }
        assertEquals(256, tree.successor(250));
    }
}