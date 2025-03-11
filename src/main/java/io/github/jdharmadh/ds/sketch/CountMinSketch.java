package io.github.jdharmadh.ds.sketch;

import io.github.jdharmadh.ds.util.Utils;

public class CountMinSketch<T> {
    private int r;
    private int c;
    private int[][] store;

    public CountMinSketch(double eps, double delta) {
        this.r = (int) Math.ceil(Math.log(1/delta));
        this.c = (int) Math.ceil(2.718/eps);
        this.store = new int[r][c];
    }

    public void add(T event) {
        for (int i = 0; i < r; i++) {
            int k = Math.floorMod(Utils.seededHash(event, i), c);
            store[i][k] += 1;
        }
    }

    public int query(T event) {
        int best = -1;
        for (int i = 0; i < r; i++) {
            int k = Math.floorMod(Utils.seededHash(event, i), c);
            if (best == -1 || best > store[i][k]) best = store[i][k];
        }
        return best;
    }
}
