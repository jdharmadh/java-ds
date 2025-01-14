package io.github.jdharmadh.ds;

import java.util.List;

public class AugmentedIntervalTree<T extends Comparable<T>> {
    class IntervalTreeNode {
        Interval<T> interval;
        T maxValue;

        IntervalTreeNode left;
        IntervalTreeNode right;

        public IntervalTreeNode(Interval<T> interval) {
            this.interval = interval;
            this.maxValue = interval.end;
        }

        public String toString() {
            return "(" + this.interval.toString() + ", " + this.maxValue.toString() + ")";
        }
    }

    IntervalTreeNode root = null;

    public void insert(Interval<T> interval) {
        this.root = insert(interval, this.root);
    }

    private IntervalTreeNode insert(Interval<T> interval, IntervalTreeNode cur) {
        if (cur == null) {
            IntervalTreeNode node = new IntervalTreeNode(interval);
            return node;
        }

        int compareIntervals = interval.start.compareTo(cur.interval.start);
        if (compareIntervals == 0)
            compareIntervals = interval.end.compareTo(cur.interval.end);
        if (compareIntervals < 0) {
            // insert left
            cur.left = insert(interval, cur.left);
        } else if (compareIntervals > 0) {
            cur.right = insert(interval, cur.right);
        } else {
            // already exists??
        }
        if (cur.left != null && cur.left.maxValue.compareTo(cur.maxValue) > 0)
            cur.maxValue = cur.left.maxValue;
        if (cur.right != null && cur.right.maxValue.compareTo(cur.maxValue) > 0)
            cur.maxValue = cur.right.maxValue;
        return cur;
    }

    public void print() {
        read(this.root);
    }

    private void read(IntervalTreeNode cur) {
        if (cur == null)
            return;
        System.out.println(cur);
        read(cur.left);
        read(cur.right);
    }
}
