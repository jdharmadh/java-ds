
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

        assertEquals((Integer) 10, tree.query(10));
        assertEquals((Integer) 20, tree.query(20));
        assertEquals((Integer) 5, tree.query(5));
        assertNull(tree.query(15));
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
