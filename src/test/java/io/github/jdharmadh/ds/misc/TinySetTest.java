package io.github.jdharmadh.ds.misc;

import io.github.jdharmadh.ds.table.TinySet;
import org.junit.Test;
import static org.junit.Assert.*;

public class TinySetTest {

    @Test
    public void testDefaultConstructor() {
        TinySet set = new TinySet();
        assertEquals(8, set.size());
        assertTrue(set.isEmpty());
    }

    @Test
    public void testConstructorWithSize() {
        TinySet set = new TinySet(20);
        assertEquals(24, set.size());
        assertTrue(set.isEmpty());
    }

    @Test
    public void testAddAndContains() {
        TinySet set = new TinySet(10);
        set.add(3);
        assertTrue(set.contains(3));
        assertFalse(set.contains(2));
    }

    @Test
    public void testRemove() {
        TinySet set = new TinySet(10);
        set.add(5);
        assertTrue(set.contains(5));
        set.remove(5);
        assertFalse(set.contains(5));
    }

    @Test
    public void testClear() {
        TinySet set = new TinySet(10);
        set.add(1);
        set.add(2);
        set.clear();
        assertTrue(set.isEmpty());
        for (int i = 0; i < set.size(); i++) {
            assertFalse(set.contains(i));
        }
    }

    @Test
    public void testIsEmpty() {
        TinySet set = new TinySet(8);
        assertTrue(set.isEmpty());
        set.add(0);
        assertFalse(set.isEmpty());
        set.remove(0);
        assertTrue(set.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutOfBoundsNegative() {
        TinySet set = new TinySet(8);
        set.add(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutOfBoundsPositive() {
        TinySet set = new TinySet(8);
        set.add(8);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds() {
        TinySet set = new TinySet(8);
        set.remove(100);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testContainsOutOfBounds() {
        TinySet set = new TinySet(8);
        set.contains(-5);
    }

    @Test
    public void testMultipleAddRemove() {
        TinySet set = new TinySet(16);
        set.add(0);
        set.add(7);
        set.add(8);
        set.add(15);
        assertTrue(set.contains(0));
        assertTrue(set.contains(7));
        assertTrue(set.contains(8));
        assertTrue(set.contains(15));
        set.remove(7);
        set.remove(8);
        assertFalse(set.contains(7));
        assertFalse(set.contains(8));
        assertTrue(set.contains(0));
        assertTrue(set.contains(15));
    }
}
