package io.github.jdharmadh.ds.table;

import java.util.Map;
import java.util.Set;

public class CuckooHashTable<K, V> implements Map<K, V> {
    public static final int INITIAL_BUCKETS = 8;
    private Object[] primary;
    private Object[] secondary;
    private int f;
    private int g;
    
    public CuckooHashTable() {
        primary = new Object[INITIAL_BUCKETS];
        secondary = new Object[INITIAL_BUCKETS];
        f = (int) Math.random() * 256;
        g = (int) Math.random() * 256;
    }

    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    public V get(Object key) {
        throw new UnsupportedOperationException();
    }

    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public Set<V> values() {
        throw new UnsupportedOperationException();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();

    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }
}
