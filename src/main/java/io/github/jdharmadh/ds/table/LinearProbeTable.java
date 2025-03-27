package io.github.jdharmadh.ds.table;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import io.github.jdharmadh.ds.util.SimpleEntry;
import io.github.jdharmadh.ds.util.Utils;

@SuppressWarnings("unchecked")
public class LinearProbeTable<K extends Comparable<K>, V> implements Map<K, V> {

    public static final int INITIAL_BUCKETS = 16;
    private Object[] buckets;
    private int size;

    public LinearProbeTable() {
        buckets = new Object[INITIAL_BUCKETS];
        size = 0;
    }

    public V put(K key, V value) {
        if (size >= buckets.length / 2) {
            Set<Map.Entry<K, V>> entries = entrySet();
            size = 0;
            buckets = new Object[buckets.length * 2];
            for (Map.Entry<K, V> entry : entries) {
                put(entry.getKey(), entry.getValue());
            }
        }
        int bucket = Math.floorMod(Utils.hash(key), buckets.length);
        while (buckets[bucket] != null) {
            SimpleEntry<K, V> curr = (SimpleEntry<K, V>) buckets[bucket];
            if (curr.getKey().equals(key)) {
                return curr.setValue(value);
            }
            bucket = (bucket + 1) % buckets.length;
        }

        buckets[bucket] = new SimpleEntry<>(key, value);
        size++;
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public V get(Object key) {
        int bucket = Math.floorMod(Utils.hash(key), buckets.length);
        while (buckets[bucket] != null) {
            if (((SimpleEntry<K, V>) buckets[bucket]).getKey().equals(key)) {
                return ((SimpleEntry<K, V>) buckets[bucket]).getValue();
            }
            bucket = (bucket + 1) % buckets.length;
        }
        return null;
    }

    public V remove(Object key) {
        // TODO: add tombstones
        int bucket = Math.floorMod(Utils.hash(key), buckets.length);
        while (buckets[bucket] != null) {
            if (((SimpleEntry<K, V>) buckets[bucket]).getKey().equals(key)) {
                V value = ((SimpleEntry<K, V>) buckets[bucket]).getValue();
                buckets[bucket] = null;
                size--;
                return value;
            }
            bucket = (bucket + 1) % buckets.length;
        }
        return null;
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Object e : buckets) {
            if (e != null) {
                keys.add(((SimpleEntry<K, V>) e).getKey());
            }
        }
        return keys;
    }

    public Set<V> values() {
        Set<V> values = new HashSet<>();
        for (Object e : buckets) {
            if (e != null) {
                values.add(((SimpleEntry<K, V>) e).getValue());
            }
        }
        return values;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new HashSet<>();
        for (Object e : buckets) {
            if (e != null) {
                entries.add((SimpleEntry<K, V>) e);
            }
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
