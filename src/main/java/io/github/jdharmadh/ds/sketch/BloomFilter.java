package io.github.jdharmadh.ds.sketch;

import java.util.BitSet;

public class BloomFilter<T> {
    int k;
    int m;
    BitSet[] store;
    
    private static final int[] PRIMES = {17, 37, 79, 149, 311, 619, 1259, 2503, 5003, 10007, 20011, 40031, 80063};

    public BloomFilter(int k, int m) {
        this.k = k;
        this.m = m;
        store = new BitSet[k];
        for (int i = 0; i < k; i++) {
            store[i] = new BitSet(m);
        }
    }

    public void add(T data) {
        for (int i = 0; i < k; i++) {
            int bit = hash(data, i);
            store[i].set(bit);
        }
    }

    public boolean contains(T data) {
        for (int i = 0; i < k; i++) {
            int bit = hash(data, i);
            if (!store[i].get(bit))
                return false;
        }
        return true;
    }

    public int hash(T data, int i) {
        int h = data != null ? data.hashCode() : 0;
        int prime1 = PRIMES[i % PRIMES.length];
        int prime2 = PRIMES[(i * 3 + 1) % PRIMES.length];
        
        h = ((h ^ prime1) * prime2) ^ (h >>> 16);
        
        return Math.floorMod(h, m);
    }
}
