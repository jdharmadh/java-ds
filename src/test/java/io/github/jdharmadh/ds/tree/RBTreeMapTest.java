package io.github.jdharmadh.ds.tree;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import java.util.TreeMap;

public class RBTreeMapTest {

    private Map<Integer, String> map;

    @Before
    public void setUp() {
        map = new RedBlackTreeMap<>();
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

    @Test
    public void testPutAndGetMultiple() {
        for (int i = 0; i < 1000; i++) {
            map.put(i, "value" + i);
        }

        for (int i = 0; i < 1000; i++) {
            assertEquals("value" + i, map.get(i));
        }
    }

    @Test
    public void testRemoveMultiple() {
        for (int i = 0; i < 1000; i++) {
            map.put(i, "value" + i);
        }

        for (int i = 0; i < 1000; i++) {
            map.remove(i);
            assertNull(map.get(i));
        }

        assertTrue(map.isEmpty());
    }

    @Test
    public void testStressPutAndRemove() {
        for (int i = 0; i < 10000; i++) {
            map.put(i, "value" + i);
        }

        for (int i = 0; i < 5000; i++) {
            map.remove(i);
        }

        for (int i = 0; i < 5000; i++) {
            assertNull(map.get(i));
        }

        for (int i = 5000; i < 10000; i++) {
            assertEquals("value" + i, map.get(i));
        }

        assertEquals(5000, map.size());
    }

    @Test
    public void testContainsKey() {
        map.put(1, "one");
        map.put(2, "two");

        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(2));
        assertFalse(map.containsKey(3));
    }

    @Test
    public void testContainsValue() {
        map.put(1, "one");
        map.put(2, "two");

        assertTrue(map.containsValue("one"));
        assertTrue(map.containsValue("two"));
        assertFalse(map.containsValue("three"));
    }

    @Test
    public void testNullValue() {
        map.put(1, null);

        assertNull(map.get(1));
    }

    @Test
    public void testPutAll() {
        Map<Integer, String> anotherMap = new TreeMap<>();
        anotherMap.put(1, "one");
        anotherMap.put(2, "two");

        map.putAll(anotherMap);

        assertEquals("one", map.get(1));
        assertEquals("two", map.get(2));
    }
}
