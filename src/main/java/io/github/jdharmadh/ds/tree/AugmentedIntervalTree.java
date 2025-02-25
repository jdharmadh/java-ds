package io.github.jdharmadh.ds.tree;

import java.util.ArrayList;
import java.util.List;

import io.github.jdharmadh.ds.base.Interval;

public class AugmentedIntervalTree<T extends Comparable<T>> {
    class IntervalTreeNode {
        final Interval<T> interval;
        T maxValue;
        IntervalTreeNode left;
        IntervalTreeNode right;

        public IntervalTreeNode(Interval<T> interval) {
            this.interval = interval;
            maxValue = interval.end;
        }

        public String toString() {
            return "(" + interval.toString() + ", " + maxValue.toString() + ")";
        }
    }

    private IntervalTreeNode root = null;

    public void insert(Interval<T> interval) {
        root = insert(interval, root);
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
            cur.left = insert(interval, cur.left);
        } else if (compareIntervals > 0) {
            cur.right = insert(interval, cur.right);
        }
        if (cur.left != null && cur.left.maxValue.compareTo(cur.maxValue) > 0)
            cur.maxValue = cur.left.maxValue;
        if (cur.right != null && cur.right.maxValue.compareTo(cur.maxValue) > 0)
            cur.maxValue = cur.right.maxValue;
        return cur;
    }

    public List<Interval<T>> query(T point) {
        List<Interval<T>> results = new ArrayList<>();
        query(root, point, results);
        return results;
    }

    public List<Interval<T>> query(Interval<T> interval) {
        List<Interval<T>> results = new ArrayList<>();
        query(root, interval, results);
        return results;
    }

    private void query(IntervalTreeNode cur, T point, List<Interval<T>> results) {
        if (cur == null || point.compareTo(cur.maxValue) > 0)
            return;
        if (cur.interval.intersects(point))
            results.add(cur.interval);
        query(cur.left, point, results);
        if (point.compareTo(cur.interval.start) >= 0)
            query(cur.right, point, results);
    }

    private void query(IntervalTreeNode cur, Interval<T> interval, List<Interval<T>> results) {
        if (cur == null || interval.start.compareTo(cur.maxValue) > 0)
            return;
        if (cur.interval.intersects(interval))
            results.add(cur.interval);
        query(cur.left, interval, results);
        if (interval.end.compareTo(cur.interval.start) >= 0)
            query(cur.right, interval, results);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        read(root, sb);
        return sb.toString();
    }

    private void read(IntervalTreeNode cur, StringBuilder sb) {
        if (cur == null)
            return;
        sb.append(cur);
        read(cur.left, sb);
        read(cur.right, sb);
    }
}
