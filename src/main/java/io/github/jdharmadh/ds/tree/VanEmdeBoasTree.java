package io.github.jdharmadh.ds.tree;

import java.util.HashMap;
import java.util.Map;

public class VanEmdeBoasTree {
    private long sqrt_u;
    private long min = -1;
    private long max = Long.MAX_VALUE;
    private Map<Long, VanEmdeBoasTree> cluster;
    private VanEmdeBoasTree summary;
    private int[] base;

    public VanEmdeBoasTree(long u) {
        this.sqrt_u = (long) Math.sqrt(u);
        if (u == 4) {
            base = new int[4];
            return;
        }
        cluster = new HashMap<>();
        summary = new VanEmdeBoasTree(sqrt_u);
    }

    public void insert(long x) {
        if (base != null) {
            base[(int) x] = 1;
            return;
        }
        if (min == -1) {
            min = x;
            max = x;
            return;
        }
        if (x < min) {
            long temp = min;
            min = x;
            x = temp;
        }
        if (x > max) {
            max = x;
        }
        if (!cluster.containsKey(high(x))) {
            summary.insert(high(x));
            cluster.put(high(x), new VanEmdeBoasTree(sqrt_u));
        }
        cluster.get(high(x)).insert(low(x));
    }

    public long successor(long x) {
        if (x > sqrt_u * sqrt_u || x < 0) {
            throw new IllegalArgumentException("Successor out of range: " + x);
        }
        if (base != null) {
            if (isEmpty()) {
                return sqrt_u * sqrt_u;
            }
            if (x < min()) {
                return min();
            }
            if (x > max()) {
                return sqrt_u * sqrt_u;
            }
            for (int l = 0; l < base.length; l++) {
                if (base[l] == 1 && l > x) {
                    return l;
                }
            }
            return sqrt_u * sqrt_u;
        }
        if (x < min) {
            return min;
        }
        if (x > max) {
            return sqrt_u * sqrt_u;
        }
        long i = high(x);
        long j = -1;
        if (cluster.containsKey(i) && low(x) < cluster.get(i).max()) {
            j = cluster.get(i).successor(low(x));
        } else {
            i = summary.successor(high(x));
            if (!cluster.containsKey(i)) {
                return sqrt_u * sqrt_u;
            }
            j = cluster.get(i).min();
        }
        return index(i, j);
    }

    private long low(long x) {
        return x % sqrt_u;
    }

    private long high(long x) {
        return x / sqrt_u;
    }

    private long index(long i, long j) {
        return i * sqrt_u + j;
    }

    public long min() {
        if (base == null)
            return min;
        int m = base.length - 1;
        for (int i = base.length - 1; i >= 0; i--)
            if (base[i] == 1)
                m = i;
        return m;
    }

    public long max() {
        if (base == null)
            return max;
        int m = 0;
        for (int i = 0; i < base.length; i++)
            if (base[i] == 1)
                m = i;
        return m;
    }

    private boolean isEmpty() {
        for (int i : base) {
            if (i == 1)
                return false;
        }
        return true;
    }
}
