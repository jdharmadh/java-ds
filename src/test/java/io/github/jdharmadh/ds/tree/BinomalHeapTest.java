package io.github.jdharmadh.ds.tree;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


public class BinomalHeapTest {
    
    private BinomialHeap heap;
    
    @Before
    public void setUp() {
        heap = new BinomialHeap();
    }
    
    @Test
    public void testInsertAndFindMin() {
        heap.insert(5);
        assertEquals(5, heap.findMin());
        
        heap.insert(3);
        assertEquals(3, heap.findMin());
        
        heap.insert(7);
        assertEquals(3, heap.findMin());
        
        heap.insert(1);
        assertEquals(1, heap.findMin());
    }
    
    @Test
    public void testDeleteMin() {
        heap.insert(5);
        heap.insert(3);
        heap.insert(7);
        
        assertEquals(3, heap.findMin());
        assertEquals(3, heap.deleteMin());
        assertEquals(5, heap.findMin());
        assertEquals(5, heap.deleteMin());
        assertEquals(7, heap.findMin());
        assertEquals(7, heap.deleteMin());
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
        
        assertEquals(2, heap.deleteMin());
        assertEquals(5, heap.deleteMin());
        assertEquals(8, heap.deleteMin());
        assertEquals(10, heap.deleteMin());
        assertEquals(12, heap.deleteMin());
        assertEquals(15, heap.deleteMin());
    }
    
    @Test
    public void testInsertDuplicates() {
        heap.insert(5);
        heap.insert(5);
        heap.insert(5);
        
        assertEquals(5, heap.deleteMin());
        assertEquals(5, heap.deleteMin());
        assertEquals(5, heap.deleteMin());
        
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
        
        assertEquals(-10, heap.findMin());
        assertEquals(-10, heap.deleteMin());
        assertEquals(-5, heap.deleteMin());
        assertEquals(-3, heap.deleteMin());
    }
    
    @Test
    public void testInsertAfterDelete() {
        heap.insert(5);
        heap.insert(3);
        assertEquals(3, heap.deleteMin());
        
        heap.insert(1);
        assertEquals(1, heap.findMin());
        assertEquals(1, heap.deleteMin());
        assertEquals(5, heap.deleteMin());
    }
}
