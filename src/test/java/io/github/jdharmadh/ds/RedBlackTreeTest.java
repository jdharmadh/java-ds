
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
}
