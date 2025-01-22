package io.github.jdharmadh.ds;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class RedBlackTreeTest {
    private RedBlackTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new RedBlackTree<>();
    }

    @Test
    public void testputAndget() {
        tree.put(10);
        tree.put(20);
        tree.put(5);
        tree.put(15);
        tree.put(25);
        tree.put(30);
        tree.put(1);
        tree.put(7);
        tree.put(12);
        tree.put(17);
        tree.put(22);
        tree.put(27);
        tree.put(3);
        tree.put(8);
        tree.put(13);
        tree.put(18);
        tree.put(23);
        tree.put(28);
        tree.put(2);
        tree.put(9);
        tree.put(14);
        tree.put(19);
        tree.put(24);
        tree.put(29);
        tree.checkInvariant();
        assertEquals(24, tree.size());

        assertEquals((Integer) 10, tree.get(10));
        assertEquals((Integer) 20, tree.get(20));
        assertEquals((Integer) 5, tree.get(5));
        assertNull(tree.get(-1));
    }

    @Test
    public void testputDuplicate() {
        tree.put(10);
        assertEquals(1, tree.size());
        tree.put(10);
        assertEquals(1, tree.size());

        assertEquals((Integer) 10, tree.get(10));
    }

    @Test
    public void testgetEmptyTree() {
        assertEquals(0, tree.size());
        assertNull(tree.get(10));
    }

    @Test
    public void testDelete() {
        tree.put(10);
        tree.put(20);
        tree.put(5);
        tree.put(15);
        tree.put(25);
        tree.put(30);
        tree.put(1);
        tree.put(7);
        tree.put(12);
        tree.put(17);
        tree.put(22);
        tree.put(27);
        tree.put(3);
        tree.put(8);
        tree.put(13);
        tree.put(18);
        tree.put(23);
        tree.put(28);
        tree.put(2);
        tree.put(9);
        tree.put(14);
        tree.put(19);
        tree.put(24);
        tree.put(29);
        assertEquals(24, tree.size());

        tree.remove(10);
        assertNull(tree.get(10));
        assertEquals(23, tree.size());
        assertEquals((Integer) 20, tree.get(20));
        assertEquals((Integer) 5, tree.get(5));
        assertEquals((Integer) 15, tree.get(15));
        assertEquals((Integer) 25, tree.get(25));
        tree.checkInvariant();

        tree.remove(20);
        assertNull(tree.get(20));
        assertEquals(22, tree.size());
        assertEquals((Integer) 5, tree.get(5));
        assertEquals((Integer) 15, tree.get(15));
        assertEquals((Integer) 25, tree.get(25));
        assertEquals((Integer) 30, tree.get(30));
        tree.checkInvariant();

        tree.remove(5);
        assertNull(tree.get(5));
        assertEquals(21, tree.size());
        assertEquals((Integer) 15, tree.get(15));
        assertEquals((Integer) 25, tree.get(25));
        assertEquals((Integer) 30, tree.get(30));
        assertEquals((Integer) 1, tree.get(1));
        tree.checkInvariant();

        tree.remove(15);
        assertNull(tree.get(15));
        assertEquals(20, tree.size());
        assertEquals((Integer) 25, tree.get(25));
        assertEquals((Integer) 30, tree.get(30));
        assertEquals((Integer) 1, tree.get(1));
        assertEquals((Integer) 7, tree.get(7));
        tree.checkInvariant();

        tree.remove(25);
        assertNull(tree.get(25));
        assertEquals(19, tree.size());
        assertEquals((Integer) 30, tree.get(30));
        assertEquals((Integer) 1, tree.get(1));
        assertEquals((Integer) 7, tree.get(7));
        assertEquals((Integer) 12, tree.get(12));
        tree.checkInvariant();

        tree.remove(30);
        assertNull(tree.get(30));
        assertEquals(18, tree.size());
        assertEquals((Integer) 1, tree.get(1));
        assertEquals((Integer) 7, tree.get(7));
        assertEquals((Integer) 12, tree.get(12));
        assertEquals((Integer) 17, tree.get(17));
        tree.checkInvariant();

        tree.remove(1);
        assertNull(tree.get(1));
        assertEquals(17, tree.size());
        assertEquals((Integer) 7, tree.get(7));
        assertEquals((Integer) 12, tree.get(12));
        assertEquals((Integer) 17, tree.get(17));
        assertEquals((Integer) 22, tree.get(22));
        tree.checkInvariant();

        tree.remove(7);
        assertNull(tree.get(7));
        assertEquals(16, tree.size());
        assertEquals((Integer) 12, tree.get(12));
        assertEquals((Integer) 17, tree.get(17));
        assertEquals((Integer) 22, tree.get(22));
        assertEquals((Integer) 27, tree.get(27));
        tree.checkInvariant();

        tree.remove(12);
        assertNull(tree.get(12));
        assertEquals(15, tree.size());
        assertEquals((Integer) 17, tree.get(17));
        assertEquals((Integer) 22, tree.get(22));
        assertEquals((Integer) 27, tree.get(27));
        assertEquals((Integer) 3, tree.get(3));
        tree.checkInvariant();

        tree.remove(17);
        assertNull(tree.get(17));
        assertEquals(14, tree.size());
        assertEquals((Integer) 22, tree.get(22));
        assertEquals((Integer) 27, tree.get(27));
        assertEquals((Integer) 3, tree.get(3));
        assertEquals((Integer) 8, tree.get(8));
        tree.checkInvariant();

        tree.remove(22);
        assertNull(tree.get(22));
        assertEquals(13, tree.size());
        assertEquals((Integer) 27, tree.get(27));
        assertEquals((Integer) 3, tree.get(3));
        assertEquals((Integer) 8, tree.get(8));
        assertEquals((Integer) 13, tree.get(13));
        tree.checkInvariant();

        tree.remove(27);
        assertNull(tree.get(27));
        assertEquals(12, tree.size());
        assertEquals((Integer) 3, tree.get(3));
        assertEquals((Integer) 8, tree.get(8));
        assertEquals((Integer) 13, tree.get(13));
        assertEquals((Integer) 18, tree.get(18));
        tree.checkInvariant();
    }

    @Test
    public void testBig() {
        for (int i = 1; i < 2; i++) {
            tree.put(i);
            tree.checkInvariant();
            assertEquals(i, tree.size());
        }
    }

    @Test
    public void testHeavy() {
        Random Random = new Random();
        for (int i = 1; i < 30000; i++) {
            tree.put(Random.nextInt(1000));
            tree.checkInvariant();
            assertTrue(tree.size() > 0);
            tree.remove(Random.nextInt(1000));
            tree.checkInvariant();
        }
    }

    @Test
    public void testLotsOfClashes() {
        Random Random = new Random();
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                int r = Random.nextInt(10);
                tree.put(r);
                assertEquals((Integer) r, tree.get(r));
            } else {
                int r = Random.nextInt(10);
                tree.remove(r);
                assertNull(tree.get(r));
            }
            tree.checkInvariant();
            assertTrue(tree.size() >= 0);
        }
    }
}
