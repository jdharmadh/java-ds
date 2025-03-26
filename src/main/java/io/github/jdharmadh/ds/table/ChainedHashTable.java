package io.github.jdharmadh.ds.table;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import io.github.jdharmadh.ds.util.SimpleEntry;
import io.github.jdharmadh.ds.util.Utils;

@SuppressWarnings("unchecked")
public class ChainedHashTable<K extends Comparable<K>, V> implements Map<K, V> {
    private class ChainEntry extends SimpleEntry<K, V> {
        ChainEntry next;

        public ChainEntry(K key, V value) {
            super(key, value);
            next = null;
        }
    }

    public static final int INITIAL_BUCKETS = 8;
    private Object[] buckets;
    private int size;

    public ChainedHashTable() {
        buckets = new Object[INITIAL_BUCKETS];
        size = 0;
    }

    public V put(K key, V value) {
        // TODO: resize based on load factor
        int bucket = Math.floorMod(Utils.hash(key), buckets.length);
        if (buckets[bucket] == null) {
            buckets[bucket] = new ChainEntry(key, value);
            size++;
            return null;
        }
        ChainEntry curr = ((ChainEntry) buckets[bucket]);
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                return curr.setValue(value);
            }
            if (curr.next == null) {
                curr.next = new ChainEntry(key, value);
                size++;
                return null;
            }
            curr = curr.next;
        }

        throw new IllegalStateException();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public V get(Object key) {
        int bucket = Math.floorMod(Utils.hash(key), buckets.length);
        ChainEntry curr = ((ChainEntry) buckets[bucket]);
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                return curr.getValue();
            }
            curr = curr.next;
        }
        return null;
    }

    public V remove(Object key) {
        int bucket = Math.floorMod(Utils.hash(key), buckets.length);
        ChainEntry curr = ((ChainEntry) buckets[bucket]);
        if (curr.getKey().equals(key)) {
            buckets[bucket] = curr.next;
            size--;
            return curr.getValue();
        }
        while (curr.next != null) {
            if (curr.next.getKey().equals(key)) {
                V value = curr.next.getValue();
                curr.next = curr.next.next;
                size--;
                return value;
            }
            curr = curr.next;
        }
        return null;
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Object c : buckets) {
            ChainEntry curr = (ChainEntry) c;
            while (curr != null) {
                keys.add(curr.getKey());
                curr = curr.next;
            }
        }
        return keys;
    }

    public Set<V> values() {
        Set<V> values = new HashSet<>();
        for (Object c : buckets) {
            ChainEntry curr = (ChainEntry) c;
            while (curr != null) {
                values.add(curr.getValue());
                curr = curr.next;
            }
        }
        return values;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new HashSet<>();
        for (Object c : buckets) {
            entries.add((ChainEntry) c);
        }
        return entries;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        return values().contains(value);

    }

    public void clear() {
        buckets = new Object[INITIAL_BUCKETS];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
