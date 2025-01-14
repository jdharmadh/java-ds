package io.github.jdharmadh.ds;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class IntervalTest {
    @Test
    public void testIntervals() {
        // Test intersecting intervals
        Interval<Integer> i1 = new Interval<Integer>(1, 4);
        Interval<Integer> i2 = new Interval<Integer>(2, 5);
        assertTrue(i1.intersects(i2));

        // Test non-intersecting intervals
        i1 = new Interval<Integer>(1, 2);
        i2 = new Interval<Integer>(3, 4);
        assertFalse(i1.intersects(i2));

        // Test touching intervals
        i1 = new Interval<Integer>(1, 2);
        i2 = new Interval<Integer>(2, 3);
        assertTrue(i1.intersects(i2));

        // Test same intervals
        i1 = new Interval<Integer>(1, 4);
        i2 = new Interval<Integer>(1, 4);
        assertTrue(i1.intersects(i2));

        // Test contained interval
        i1 = new Interval<Integer>(1, 5);
        i2 = new Interval<Integer>(2, 3);
        assertTrue(i1.intersects(i2));

        // Test invalid interval
        try {
            new Interval<Integer>(5, 1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
