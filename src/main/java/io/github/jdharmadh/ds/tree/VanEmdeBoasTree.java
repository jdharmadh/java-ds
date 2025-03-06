package io.github.jdharmadh.ds.tree;

import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;

public class VanEmdeBoasTree {
    private int sqrt_u;
    private int min = -1;
    private int max = Integer.MAX_VALUE;
    private Map<Integer, VanEmdeBoasTree> cluster;
    private VanEmdeBoasTree summary;
    private Set<Integer> base;

    public VanEmdeBoasTree(int u) {
        this.sqrt_u = (int) Math.sqrt(u);
        if (u == 4) {
            base = new TreeSet<>();
            return;
        }
        cluster = new HashMap<>(sqrt_u);
        summary = new VanEmdeBoasTree(sqrt_u);
    }

    public void insert(int x) {
        if (base != null) {
            base.add(x);
            return;
        }
        if (min == -1) {
            min = x;
            max = x;
            return;
        }
        if (x < min) {
            int temp = min;
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

    public int successor(int x) {
        if (x > sqrt_u * sqrt_u || x < 0) {
            throw new IllegalArgumentException("Successor out of range: " + x);
        }
        if (base != null) {
            if (x < min()) {
                return min();
            }
            if (x > max()) {
                return sqrt_u * sqrt_u;
            }
            for (int l : base) {
                if (l > x) {
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
        int i = high(x);
        int j = -1;
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

    private int low(int x) {
        return x % sqrt_u;
    }

    private int high(int x) {
        return x / sqrt_u;
    }

    private int index(int i, int j) {
        return i * sqrt_u + j;
    }

    public int min() {
        return base == null ? min : Collections.min(base);
    }

    public int max() {
        return base == null ? max : Collections.max(base);
    }
}
