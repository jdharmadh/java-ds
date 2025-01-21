
package io.github.jdharmadh.ds;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RedBlackTreeTest {
    private RedBlackTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new RedBlackTree<>();
    }

    @Test
    public void testInsertAndQuery() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(15);
        tree.insert(25);
        tree.insert(30);
        tree.insert(1);
        tree.insert(7);
        tree.insert(12);
        tree.insert(17);
        tree.insert(22);
        tree.insert(27);
        tree.insert(3);
        tree.insert(8);
        tree.insert(13);
        tree.insert(18);
        tree.insert(23);
        tree.insert(28);
        tree.insert(2);
        tree.insert(9);
        tree.insert(14);
        tree.insert(19);
        tree.insert(24);
        tree.insert(29);
        tree.checkInvariant();

        assertEquals((Integer) 10, tree.query(10));
        assertEquals((Integer) 20, tree.query(20));
        assertEquals((Integer) 5, tree.query(5));
        assertNull(tree.query(-1));
    }

    @Test
    public void testInsertDuplicate() {
        tree.insert(10);
        tree.insert(10);

        assertEquals((Integer) 10, tree.query(10));
    }

    @Test
    public void testQueryEmptyTree() {
        assertNull(tree.query(10));
    }

    @Test
    public void testDelete() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(15);
        tree.insert(25);
        tree.insert(30);
        tree.insert(1);
        tree.insert(7);
        tree.insert(12);
        tree.insert(17);
        tree.insert(22);
        tree.insert(27);
        tree.insert(3);
        tree.insert(8);
        tree.insert(13);
        tree.insert(18);
        tree.insert(23);
        tree.insert(28);
        tree.insert(2);
        tree.insert(9);
        tree.insert(14);
        tree.insert(19);
        tree.insert(24);
        tree.insert(29);

        tree.remove(10);
        assertNull(tree.query(10));
        assertEquals((Integer) 20, tree.query(20));
        assertEquals((Integer) 5, tree.query(5));
        assertEquals((Integer) 15, tree.query(15));
        assertEquals((Integer) 25, tree.query(25));
        tree.checkInvariant();

        tree.remove(20);
        assertNull(tree.query(20));
        assertEquals((Integer) 5, tree.query(5));
        assertEquals((Integer) 15, tree.query(15));
        assertEquals((Integer) 25, tree.query(25));
        assertEquals((Integer) 30, tree.query(30));
        tree.checkInvariant();

        tree.remove(5);
        assertNull(tree.query(5));
        assertEquals((Integer) 15, tree.query(15));
        assertEquals((Integer) 25, tree.query(25));
        assertEquals((Integer) 30, tree.query(30));
        assertEquals((Integer) 1, tree.query(1));
        tree.checkInvariant();

        tree.remove(15);
        assertNull(tree.query(15));
        assertEquals((Integer) 25, tree.query(25));
        assertEquals((Integer) 30, tree.query(30));
        assertEquals((Integer) 1, tree.query(1));
        assertEquals((Integer) 7, tree.query(7));
        tree.checkInvariant();

        tree.remove(25);
        assertNull(tree.query(25));
        assertEquals((Integer) 30, tree.query(30));
        assertEquals((Integer) 1, tree.query(1));
        assertEquals((Integer) 7, tree.query(7));
        assertEquals((Integer) 12, tree.query(12));
        tree.checkInvariant();

        tree.remove(30);
        assertNull(tree.query(30));
        assertEquals((Integer) 1, tree.query(1));
        assertEquals((Integer) 7, tree.query(7));
        assertEquals((Integer) 12, tree.query(12));
        assertEquals((Integer) 17, tree.query(17));
    }
}
