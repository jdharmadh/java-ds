package io.github.jdharmadh.ds.sketch;

import java.util.BitSet;

public class BloomFilter<T> {
    int k;
    int m;
    BitSet store;

    private static final int[] PRIMES = { 17, 37, 79, 149, 311, 619, 1259, 2503, 5003, 10007, 20011, 40031, 80063,
            160001, 320003, 640009, 1280023, 2560037, 5120063, 10240127, 20480257, 40960501, 81921017, 16384103,
            32768243, 65536487, 131072607, 262145231, 524290253, 1048581013 };

    public BloomFilter(int k, int m) {
        this.k = k;
        this.m = m;
        store = new BitSet(m * k);
    }

    public void add(T data) {
        for (int i = 0; i < k; i++) {
            int bit = hash(data, i);
            store.set(i * m + bit);
        }
    }

    public boolean contains(T data) {
        for (int i = 0; i < k; i++) {
            int bit = hash(data, i);
            if (!store.get(i * m + bit))
                return false;
        }
        return true;
    }

    public int hash(T data, int i) {
        int h = data != null ? data.hashCode() : 0;
        int prime1 = PRIMES[i % PRIMES.length];
        int prime2 = PRIMES[(i * 5 + 1) % PRIMES.length];

        h = ((h ^ prime1) * prime2) ^ (h >>> 16);

        return Math.floorMod(h, m);
    }
}
