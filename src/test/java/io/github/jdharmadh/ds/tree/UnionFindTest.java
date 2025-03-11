package io.github.jdharmadh.ds.tree;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class UnionFindTest {

    private UnionFind<Integer> intUnionFind;
    private UnionFind<String> stringUnionFind;

    @Before
    public void setUp() {
        intUnionFind = new UnionFind<>();
        stringUnionFind = new UnionFind<>();
    }

    @Test
    public void testAddAndFindInteger() {
        intUnionFind.add(1);
        intUnionFind.add(2);
        intUnionFind.add(3);

        assertEquals(Integer.valueOf(1), intUnionFind.find(1));
        assertEquals(Integer.valueOf(2), intUnionFind.find(2));
        assertEquals(Integer.valueOf(3), intUnionFind.find(3));
    }

    @Test
    public void testAddAndFindString() {
        stringUnionFind.add("a");
        stringUnionFind.add("b");
        stringUnionFind.add("c");

        assertEquals("a", stringUnionFind.find("a"));
        assertEquals("b", stringUnionFind.find("b"));
        assertEquals("c", stringUnionFind.find("c"));
    }

    @Test
    public void testUnionInteger() {
        intUnionFind.add(1);
        intUnionFind.add(2);
        intUnionFind.add(3);
        intUnionFind.add(4);

        intUnionFind.union(1, 2);
        intUnionFind.union(3, 4);

        assertEquals(intUnionFind.find(1), intUnionFind.find(2));
        assertEquals(intUnionFind.find(3), intUnionFind.find(4));
        assertNotEquals(intUnionFind.find(1), intUnionFind.find(3));
    }

    @Test
    public void testUnionString() {
        stringUnionFind.add("a");
        stringUnionFind.add("b");
        stringUnionFind.add("c");
        stringUnionFind.add("d");

        stringUnionFind.union("a", "b");
        stringUnionFind.union("c", "d");

        assertEquals(stringUnionFind.find("a"), stringUnionFind.find("b"));
        assertEquals(stringUnionFind.find("c"), stringUnionFind.find("d"));
        assertNotEquals(stringUnionFind.find("a"), stringUnionFind.find("c"));
    }

    @Test
    public void testTransitiveUnion() {
        intUnionFind.add(1);
        intUnionFind.add(2);
        intUnionFind.add(3);

        intUnionFind.union(1, 2);
        intUnionFind.union(2, 3);

        assertEquals(intUnionFind.find(1), intUnionFind.find(3));
    }

    @Test
    public void testPathCompression() {
        intUnionFind.add(1);
        intUnionFind.add(2);
        intUnionFind.add(3);
        intUnionFind.add(4);
        intUnionFind.add(5);

        intUnionFind.union(1, 2);
        intUnionFind.union(2, 3);
        intUnionFind.union(3, 4);
        intUnionFind.union(4, 5);

        Integer root = intUnionFind.find(5);

        assertEquals(root, intUnionFind.find(1));
        assertEquals(root, intUnionFind.find(2));
        assertEquals(root, intUnionFind.find(3));
        assertEquals(root, intUnionFind.find(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindNonExistentElement() {
        intUnionFind.find(99);
    }

    @Test
    public void testUnionOfSameSet() {
        intUnionFind.add(1);
        intUnionFind.add(2);

        intUnionFind.union(1, 2);
        Integer root = intUnionFind.find(1);

        intUnionFind.union(1, 2);
        assertEquals(root, intUnionFind.find(1));
        assertEquals(root, intUnionFind.find(2));
    }

    @Test
    public void testLargeUnion() {
        for (int i = 0; i < 100; i++) {
            intUnionFind.add(i);
        }

        for (int i = 0; i < 90; i += 10) {
            for (int j = 1; j < 10; j++) {
                intUnionFind.union(i, i + j);
            }
        }

        for (int i = 0; i < 90; i += 10) {
            for (int j = 0; j < 10; j++) {
                assertEquals(intUnionFind.find(i), intUnionFind.find(i + j));
            }
        }

        for (int i = 0; i < 90; i += 10) {
            for (int j = 10; j < 90; j += 10) {
                if (i != j) {
                    assertNotEquals(intUnionFind.find(i), intUnionFind.find(j));
                }
            }
        }
    }

    @Test
    public void testRankHeuristic() {
        intUnionFind.add(1);
        intUnionFind.add(2);
        intUnionFind.add(3);
        intUnionFind.add(4);

        intUnionFind.union(1, 2);
        intUnionFind.union(3, 4);

        intUnionFind.union(1, 3);

        assertEquals(intUnionFind.find(1), intUnionFind.find(3));
        assertEquals(intUnionFind.find(2), intUnionFind.find(4));
    }
}
