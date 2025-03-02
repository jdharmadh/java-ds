package io.github.jdharmadh.ds.tree;

import java.util.HashMap;
import java.util.Map;

public class UnionFind<T> {
    private Map<T, T> parent;
    private Map<T, Integer> rank;

    public UnionFind() {
        parent = new HashMap<>();
        rank = new HashMap<>();
    }

    public void add(T x) {
        parent.put(x, x);
        rank.put(x, 0);
    }

    public T find(T x) {
        if (!parent.containsKey(x)) {
            throw new IllegalArgumentException("Node not found");
        }
        T root = x;
        while (!root.equals(parent.get(root))) {
            root = parent.get(root);
        }
        while (!x.equals(root)) {
            T next = parent.get(x);
            parent.put(x, root);
            x = next;
        }
        return root;
    }

    public void union(T x, T y) {
        T xRoot = find(x);
        T yRoot = find(y);
        if (xRoot.equals(yRoot)) {
            return;
        }
        if (rank.get(xRoot) < rank.get(yRoot)) {
            parent.put(xRoot, yRoot);
        } else if (rank.get(xRoot) > rank.get(yRoot)) {
            parent.put(yRoot, xRoot);
        } else {
            parent.put(yRoot, xRoot);
            rank.put(xRoot, rank.get(xRoot) + 1);
        }
    }
}
