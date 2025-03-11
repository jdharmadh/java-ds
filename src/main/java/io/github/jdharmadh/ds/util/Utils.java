package io.github.jdharmadh.ds.util;

import java.util.Random;

public class Utils {
    private static final int[] PRIMES = { 17, 37, 79, 149, 311, 619, 1259, 2503, 5003, 10007, 20011, 40031, 80063,
            160001, 320003, 640009, 1280023, 2560037, 5120063, 10240127, 20480257, 40960501, 81921017, 16384103,
            32768243, 65536487, 131072607, 262145231, 524290253, 1048581013 };

    public static int hash(Object data) {
        int h = data != null ? data.hashCode() : 0;
        h ^= h >>> 16;
        h *= 0x85ebca6b;
        h ^= h >>> 13;
        h *= 0xc2b2ae35;
        h ^= h >>> 16;

        return h;
    }

    public static int seededHash(Object data, int k) {
        int h = data != null ? data.hashCode() : 0;
        int prime1 = PRIMES[Math.abs(k % PRIMES.length)];
        int prime2 = PRIMES[Math.abs((k * 5 + 1) % PRIMES.length)];

        h = ((h ^ prime1) * prime2) ^ (h >>> 16);

        return h;
    }

    public static String generateRandomString(Random random) {
        int length = 32 + (int) (Math.random() * 33);
        StringBuilder sb = new StringBuilder(length);
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

    public static String generateRandomString(int length, Random random) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

}
