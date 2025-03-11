package io.github.jdharmadh.ds.sketch;

import io.github.jdharmadh.ds.util.Utils;

import java.util.BitSet;

public class BloomFilter<T> {
    int k;
    int m;
    BitSet store;

    public BloomFilter(int k, int m) {
        this.k = k;
        this.m = m;
        store = new BitSet(m * k);
    }

    public void add(T data) {
        for (int i = 0; i < k; i++) {
            int bit = Math.floorMod(Utils.seededHash(data, i), m);
            store.set(i * m + bit);
        }
    }

    public boolean contains(T data) {
        for (int i = 0; i < k; i++) {
            int bit = Math.floorMod(Utils.seededHash(data, i), m);
            if (!store.get(i * m + bit))
                return false;
        }
        return true;
    }
}
