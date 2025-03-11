package io.github.jdharmadh.ds.eval;

import io.github.jdharmadh.ds.sketch.HyperLogLog;
import io.github.jdharmadh.ds.util.Utils;
import java.util.Random;

public class HLLEval {
    static int[] mValues = {64, 256, 1024};
    static long[] magnitudes = {10_000L, 100_000L, 1_000_000L, 10_000_000L, 100_000_000L, 1_000_000_000L};
    static Random random = new Random(19);

    public static void main(String[] args) {
        System.out.println("===== HYPERLOGLOG EVALUATION =====");
        numbersCloseTogether();
        numbersFarApart();
        stringsCloseTogether();
        stringsFarApart();
        System.out.println("===== FIN =====");
    }

    private static void numbersCloseTogether() {
        for (int m : mValues) {
            System.out.println("\n===== NUMBERS CLOSE TOGETHER: m = " + m + " =====");
            for (long magnitude : magnitudes) {
                HyperLogLog hll = new HyperLogLog(m);
                long base = random.nextLong(Long.MAX_VALUE - magnitude);
                
                for (long i = 0; i < magnitude; i++) {
                    hll.add(base + i);
                }
                
                long estimate = hll.count();
                long[] errorRange = calculateErrorRange(magnitude, m);
                double errorPercent = 100.0 * (estimate - magnitude) / magnitude;
                boolean withinRange = estimate >= errorRange[0] && estimate <= errorRange[1];
                
                System.out.printf("n=%d: estimate=%d, error=%.2f%%, expected range=[%d, %d] %s%n", 
                    magnitude, estimate, errorPercent, errorRange[0], errorRange[1], 
                    withinRange ? "PASS" : "FAIL");
            }
        }
    }

    private static void numbersFarApart() {
        for (int m : mValues) {
            System.out.println("\n===== NUMBERS FAR APART: m = " + m + " =====");
            for (long magnitude : magnitudes) {
                HyperLogLog hll = new HyperLogLog(m);
                
                for (long i = 0; i < magnitude; i++) {
                    hll.add(random.nextLong());
                }
                
                long estimate = hll.count();
                long[] errorRange = calculateErrorRange(magnitude, m);
                double errorPercent = 100.0 * (estimate - magnitude) / magnitude;
                boolean withinRange = estimate >= errorRange[0] && estimate <= errorRange[1];
                
                System.out.printf("n=%d: estimate=%d, error=%.2f%%, expected range=[%d, %d] %s%n", 
                    magnitude, estimate, errorPercent, errorRange[0], errorRange[1],
                    withinRange ? "PASS" : "FAIL");
            }
        }
    }

    private static void stringsCloseTogether() {
        for (int m : mValues) {
            System.out.println("\n===== STRINGS CLOSE TOGETHER: m = " + m + " =====");
            for (long magnitude : magnitudes) {
                HyperLogLog hll = new HyperLogLog(m);
                String prefix = "prefix-" + random.nextInt(1000) + "-";
                
                for (long i = 0; i < magnitude; i++) {
                    hll.add(prefix + Utils.generateRandomString(random));
                }
                
                long estimate = hll.count();
                long[] errorRange = calculateErrorRange(magnitude, m);
                double errorPercent = 100.0 * (estimate - magnitude) / magnitude;
                boolean withinRange = estimate >= errorRange[0] && estimate <= errorRange[1];
                
                System.out.printf("n=%d: estimate=%d, error=%.2f%%, expected range=[%d, %d] %s%n", 
                    magnitude, estimate, errorPercent, errorRange[0], errorRange[1],
                    withinRange ? "PASS" : "FAIL");
            }
        }
    }

    private static void stringsFarApart() {
        for (int m : mValues) {
            System.out.println("\n===== STRINGS FAR APART: m = " + m + " =====");
            for (long magnitude : magnitudes) {
                HyperLogLog hll = new HyperLogLog(m);
                
                for (long i = 0; i < magnitude; i++) {
                    hll.add(Utils.generateRandomString(random));
                }
                
                long estimate = hll.count();
                long[] errorRange = calculateErrorRange(magnitude, m);
                double errorPercent = 100.0 * (estimate - magnitude) / magnitude;
                boolean withinRange = estimate >= errorRange[0] && estimate <= errorRange[1];
                
                System.out.printf("n=%d: estimate=%d, error=%.2f%%, expected range=[%d, %d] %s%n", 
                    magnitude, estimate, errorPercent, errorRange[0], errorRange[1],
                    withinRange ? "PASS" : "FAIL");
            }
        }
    }

    private static long[] calculateErrorRange(long n, int m) {
        double beta;
        if (m == 16) {
            beta = 1.106;
        } else if (m == 32) {
            beta = 1.070;
        } else if (m == 64) {
            beta = 1.054;
        } else if (m == 128) {
            beta = 1.046;
        } else if (m > 128) {
            beta = Math.sqrt(3 * Math.log(2)) - 1;
        } else {
            beta = 1.106;
        }

        double standardError = beta / Math.sqrt(m);

        long lowerBound = (long) (n * (1 - standardError));
        long upperBound = (long) (n * (1 + standardError));

        return new long[] { lowerBound, upperBound };
    }
}
