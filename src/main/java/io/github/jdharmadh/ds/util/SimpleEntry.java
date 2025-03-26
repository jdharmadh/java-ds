package io.github.jdharmadh.ds.util;

import java.util.Map;

public class SimpleEntry<K extends Comparable<K>, V> implements Map.Entry<K, V>, Comparable<SimpleEntry<K, V>> {
    K key;
    V value;

    public SimpleEntry(K k, V v) {
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

    public int compareTo(SimpleEntry<K, V> other) {
        return (key).compareTo(other.getKey());
    }
}