package io.github.jdharmadh.ds.table;

public class TinySet {
    byte[] bits;

    public TinySet() {
        bits = new byte[1];
    }

    public TinySet(int size) {
        bits = new byte[(size + 7) / 8];
    }

    public void add(int index) {
        if (index < 0 || index >= bits.length * 8) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        bits[index / 8] |= (1 << (index % 8));
    }

    public void remove(int index) {
        if (index < 0 || index >= bits.length * 8) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        bits[index / 8] &= ~(1 << (index % 8));
    }

    public boolean contains(int index) {
        if (index < 0 || index >= bits.length * 8) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return (bits[index / 8] & (1 << (index % 8))) != 0;
    }

    public int size() {
        return bits.length * 8;
    }

    public void clear() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = 0;
        }
    }

    public boolean isEmpty() {
        for (byte b : bits) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }
}
