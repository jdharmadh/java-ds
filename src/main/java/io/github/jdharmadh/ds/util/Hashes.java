package io.github.jdharmadh.ds.util;

public class Hashes {
    private static final int[] PRIMES = { 17, 37, 79, 149, 311, 619, 1259, 2503, 5003, 10007, 20011, 40031, 80063,
            160001, 320003, 640009, 1280023, 2560037, 5120063, 10240127, 20480257, 40960501, 81921017, 16384103,
            32768243, 65536487, 131072607, 262145231, 524290253, 1048581013 };

    public static int hash(Object data) {
        int h = data != null ? data.hashCode() : 0;
        int prime1 = PRIMES[Math.abs(h % PRIMES.length)];
        int prime2 = PRIMES[Math.abs((h * 5 + 1) % PRIMES.length)];
        h = ((h ^ prime1) * prime2) ^ (h >>> 16);

        return h;
    }

    public static int seededHash(Object data, int k) {
        int h = data != null ? data.hashCode() : 0;
        int prime1 = PRIMES[Math.abs(k % PRIMES.length)];
        int prime2 = PRIMES[Math.abs((k * 5 + 1) % PRIMES.length)];

        h = ((h ^ prime1) * prime2) ^ (h >>> 16);

        return h;
    }
}
