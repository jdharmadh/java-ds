package io.github.jdharmadh.ds.tree;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import io.github.jdharmadh.ds.util.SimpleEntry;

public class WrappedRedBlackTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private RedBlackTree<SimpleEntry<K, V>> tree;

    public WrappedRedBlackTreeMap() {
        tree = new RedBlackTree<>();
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        SimpleEntry<K, V> entry = new SimpleEntry<>(key, value);
        SimpleEntry<K, V> returned = tree.put(entry);
        return returned == null ? null : returned.getValue();
    }

    public V get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        @SuppressWarnings("unchecked")
        SimpleEntry<K, V> entry = new SimpleEntry<>((K) key, null);
        SimpleEntry<K, V> returned = tree.get(entry);
        return returned == null ? null : returned.getValue();
    }

    public V remove(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        @SuppressWarnings("unchecked")
        SimpleEntry<K, V> entry = new SimpleEntry<>((K) key, null);
        SimpleEntry<K, V> returned = tree.remove(entry);
        return returned == null ? null : returned.getValue();
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public void clear() {
        tree.clear();
    }

    public int size() {
        return tree.size();
    }

    public boolean isEmpty() {
        return tree.size() == 0;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new TreeSet<>();
        for (SimpleEntry<K, V> entry : tree.sortedData()) {
            entries.add(entry);
        }
        return entries;
    }

    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>();
        for (Map.Entry<K, V> entry : entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    public Set<V> values() {
        Set<V> values = new TreeSet<>();
        for (Map.Entry<K, V> entry : entrySet()) {
            values.add(entry.getValue());
        }
        return values;
    }

    public boolean containsValue(Object value) {
        for (Map.Entry<K, V> entry : entrySet()) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
