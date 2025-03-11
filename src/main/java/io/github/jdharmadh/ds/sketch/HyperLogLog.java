package io.github.jdharmadh.ds.sketch;

import io.github.jdharmadh.ds.util.Utils;

public class HyperLogLog {
    byte[] counters;
    byte b;

    public HyperLogLog(int m) {
        counters = new byte[m];
        b = (byte) (31 - Integer.numberOfLeadingZeros(m));
    }

    public void add(Object data) {
        int h = Utils.hash(data);
        int j = ((h >>> (32 - b)));
        byte rho_w = (byte) (Integer.numberOfLeadingZeros(h << b) + 1);
        if (rho_w > counters[j])
            counters[j] = rho_w;
        if (counters[j] == 127) {
            throw new OutOfMemoryError("Counter has reached max capacity");
        }
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

    public void merge(HyperLogLog other) {
        if (b != other.b) {
            throw new IllegalArgumentException("Mismatched precisions for merging HLLs");
        }
        for (int i = 0; i < counters.length; i++) {
            if (other.counters[i] > counters[i]) {
                counters[i] = other.counters[i];
            }
        }
    }
}
