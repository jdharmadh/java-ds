package io.github.jdharmadh.ds;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import java.util.TreeMap;

public class RBTreeMapTest {

    private Map<Integer, String> map;

    @Before
    public void setUp() {
        map = new TreeMap<>();
    }

    @Test
    public void testPutAndGet() {
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        assertEquals("one", map.get(1));
        assertEquals("two", map.get(2));
        assertEquals("three", map.get(3));
    }

    @Test
    public void testKeyOrder() {
        map.put(3, "three");
        map.put(1, "one");
        map.put(2, "two");

        Integer[] expectedKeys = { 1, 2, 3 };
        assertArrayEquals(expectedKeys, map.keySet().toArray());
    }

    @Test
    public void testOverwriteValue() {
        map.put(1, "one");
        map.put(1, "uno");

        assertEquals("uno", map.get(1));
    }

    @Test
    public void testRemove() {
        map.put(1, "one");
        map.put(2, "two");
        map.remove(1);

        assertNull(map.get(1));
        assertEquals("two", map.get(2));
    }

    @Test
    public void testSize() {
        map.put(1, "one");
        map.put(2, "two");

        assertEquals(2, map.size());
    }

    @Test
    public void testClear() {
        map.put(1, "one");
        map.put(2, "two");
        map.clear();

        assertTrue(map.isEmpty());
    }
}
