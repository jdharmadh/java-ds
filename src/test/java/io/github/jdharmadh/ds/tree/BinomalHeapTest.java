package io.github.jdharmadh.ds.tree;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BinomalHeapTest {

    private BinomialHeap<Integer> heap;

    @Before
    public void setUp() {
        heap = new BinomialHeap<Integer>();
    }

    @Test
    public void testInsertAndFindMin() {
        heap.insert(5);
        assertEquals(Integer.valueOf(5), heap.findMin());

        heap.insert(3);
        assertEquals(Integer.valueOf(3), heap.findMin());

        heap.insert(7);
        assertEquals(Integer.valueOf(3), heap.findMin());

        heap.insert(1);
        assertEquals(Integer.valueOf(1), heap.findMin());
    }

    @Test
    public void testDeleteMin() {
        heap.insert(5);
        heap.insert(3);
        heap.insert(7);

        assertEquals(Integer.valueOf(3), heap.findMin());
        assertEquals(Integer.valueOf(3), heap.deleteMin());
        assertEquals(Integer.valueOf(5), heap.findMin());
        assertEquals(Integer.valueOf(5), heap.deleteMin());
        assertEquals(Integer.valueOf(7), heap.findMin());
        assertEquals(Integer.valueOf(7), heap.deleteMin());
    }

    @Test
    public void testEmptyHeap() {
        try {
            heap.findMin();
            fail("Should throw exception on empty heap");
        } catch (Exception e) {
            // Expected
        }

        try {
            heap.deleteMin();
            fail("Should throw exception on empty heap");
        } catch (Exception e) {
            // Expected
        }
    }

    @Test
    public void testInsertDeleteMany() {
        heap.insert(10);
        heap.insert(5);
        heap.insert(15);
        heap.insert(2);
        heap.insert(8);
        heap.insert(12);

        assertEquals(Integer.valueOf(2), heap.deleteMin());
        assertEquals(Integer.valueOf(5), heap.deleteMin());
        assertEquals(Integer.valueOf(8), heap.deleteMin());
        assertEquals(Integer.valueOf(10), heap.deleteMin());
        assertEquals(Integer.valueOf(12), heap.deleteMin());
        assertEquals(Integer.valueOf(15), heap.deleteMin());
    }

    @Test
    public void testInsertDuplicates() {
        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(Integer.valueOf(5), heap.deleteMin());
        assertEquals(Integer.valueOf(5), heap.deleteMin());
        assertEquals(Integer.valueOf(5), heap.deleteMin());

        try {
            heap.deleteMin();
            fail("Should throw exception after all elements are removed");
        } catch (Exception e) {
            // Expected
        }
    }

    @Test
    public void testInsertNegativeNumbers() {
        heap.insert(-5);
        heap.insert(-10);
        heap.insert(-3);

        assertEquals(Integer.valueOf(-10), heap.findMin());
        assertEquals(Integer.valueOf(-10), heap.deleteMin());
        assertEquals(Integer.valueOf(-5), heap.deleteMin());
        assertEquals(Integer.valueOf(-3), heap.deleteMin());
    }

    @Test
    public void testInsertAfterDelete() {
        heap.insert(5);
        heap.insert(3);
        assertEquals(Integer.valueOf(3), heap.deleteMin());

        heap.insert(1);
        assertEquals(Integer.valueOf(1), heap.findMin());
        assertEquals(Integer.valueOf(1), heap.deleteMin());
        assertEquals(Integer.valueOf(5), heap.deleteMin());
    }

    @Test
    public void testWithStrings() {
        BinomialHeap<String> stringHeap = new BinomialHeap<>();

        stringHeap.insert("banana");
        stringHeap.insert("apple");
        stringHeap.insert("cherry");
        stringHeap.insert("date");

        // Strings should be ordered lexicographically
        assertEquals("apple", stringHeap.findMin());
        assertEquals("apple", stringHeap.deleteMin());
        assertEquals("banana", stringHeap.deleteMin());
        assertEquals("cherry", stringHeap.deleteMin());
        assertEquals("date", stringHeap.deleteMin());

        // Test empty again
        try {
            stringHeap.findMin();
            fail("Should throw exception on empty heap");
        } catch (Exception e) {
            // Expected
        }
    }

    @Test
    public void testEmptyStrings() {
        BinomialHeap<String> stringHeap = new BinomialHeap<>();

        stringHeap.insert("");
        stringHeap.insert("hello");
        stringHeap.insert("world");

        // Empty string comes first lexicographically
        assertEquals("", stringHeap.findMin());
        assertEquals("", stringHeap.deleteMin());
        assertEquals("hello", stringHeap.deleteMin());
        assertEquals("world", stringHeap.deleteMin());
    }
}
