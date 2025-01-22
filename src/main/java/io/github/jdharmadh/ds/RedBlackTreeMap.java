package io.github.jdharmadh.ds;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class RedBlackTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    class Entry implements Map.Entry<K, V>, Comparable<Entry> {
        K key;
        V value;

        public Entry(K k, V v) {
            key = k;
            value = v;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

        public String toString() {
            return key + "=" + value;
        }

        public int hashCode() {
            return key.hashCode() ^ value.hashCode();
        }

        public int compareTo(Entry other) {
            return ((Comparable<K>) key).compareTo(other.getKey());
        }
    }

    private RedBlackTree<Entry> tree;

    public RedBlackTreeMap() {
        tree = new RedBlackTree<>();
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        Entry entry = new Entry(key, value);
        Entry returned = tree.put(entry);
        return returned == null ? null : returned.getValue();
    }

    public V get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        @SuppressWarnings("unchecked")
        Entry entry = new Entry((K) key, null);
        Entry returned = tree.get(entry);
        return returned == null ? null : returned.getValue();
    }

    public V remove(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        @SuppressWarnings("unchecked")
        Entry entry = new Entry((K) key, null);
        Entry returned = tree.remove(entry);
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
        for (Entry entry : tree.sortedData()) {
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
