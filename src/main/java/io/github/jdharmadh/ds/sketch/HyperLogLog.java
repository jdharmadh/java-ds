package io.github.jdharmadh.ds.sketch;

import io.github.jdharmadh.ds.util.Hashes;

public class HyperLogLog {
    private byte[] counters;
    private int b;

    public HyperLogLog(int m) {
        counters = new byte[m];
        b = 31 - Integer.numberOfLeadingZeros(m);
        System.out.println(b);
    }

    public void add(Object data) {
        int h = Hashes.hash(data);
        int j = ((h >>> (32 - b)));
        byte rho_w = leftmost1(h << b);
        if (rho_w > counters[j])
            counters[j] = rho_w;
    }

    public int count() {
        double Z = 0;
        for (int i = 0; i < counters.length; i++) {
            Z += Math.pow(2, -counters[i]);
        }
        return (int) Math.floor(biasCorrection() * counters.length * counters.length / Z);
    }

    private double biasCorrection() {
        switch (counters.length) {
            case 16:
                return 0.673;
            case 32:
                return 0.697;
            case 64:
                return 0.709;
            default:
                return 0.7213 / (1 + 1.079 / counters.length);
        }
    }

    private byte leftmost1(int x) {
        return (byte) (Integer.numberOfLeadingZeros(x) + 1);
    }
}
