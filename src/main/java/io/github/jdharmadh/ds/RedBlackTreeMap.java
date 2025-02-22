package io.github.jdharmadh.ds;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

public class RedBlackTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private class RedBlackNode implements Map.Entry<K, V>, Comparable<RedBlackNode> {
        K key;
        V value;
        RedBlackNode left;
        RedBlackNode right;
        RedBlackNode parent;
        boolean color;

        public RedBlackNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }

        public String toString() {
            return key.toString();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public int compareTo(RedBlackNode o) {
            return key.compareTo(o.key);
        }
    }

    static boolean RED = true;
    static boolean BLACK = false;
    private RedBlackNode root;
    int size;

    public RedBlackTreeMap() {
        this.root = null;
        size = 0;
    }

    // ------------------------PUBLIC METHODS------------------------
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        RedBlackNode prev = null;
        RedBlackNode cur = root;
        while (cur != null) {
            prev = cur;
            if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else {
                return cur.setValue(value);
            }
        }
        size++;
        cur = new RedBlackNode(key, value);
        cur.parent = prev;
        if (prev == null) {
            root = cur;
        } else if (prev.key.compareTo(key) < 0) {
            prev.right = cur;
        } else {
            prev.left = cur;
        }

        if (cur.parent == null) {
            cur.color = BLACK;
            return null;
        }

        if (cur.parent.parent == null) {
            cur.color = RED;
            return null;
        }

        fixAfterInsertion(cur);
        return null;
    }

    @SuppressWarnings("unchecked")
    public V remove(Object keyObject) {
        if (keyObject == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        K key = (K) keyObject;
        RedBlackNode cur = root;
        while (cur != null) {
            if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else {
                V oldValue = cur.value;
                delete(cur);
                size--;
                return oldValue;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public V get(Object keyObject) {
        if (keyObject == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        K key = (K) keyObject;
        RedBlackNode cur = root;
        while (cur != null) {
            if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else {
                return cur.value;
            }
        }
        return null;
    }

    // ------------------------PUBLIC UTILS------------------------

    public V min() {
        return leftmost(root).value;
    }

    public V max() {
        return rightmost(root).value;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Set<Map.Entry<K,V>> entrySet() {
        Set<Map.Entry<K,V>> set = new TreeSet<>();
        RedBlackNode cur = root;
        Stack<RedBlackNode> stack = new Stack<>();
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            set.add(cur);
            cur = cur.right;
        }
        return set;
    }

    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        for (Map.Entry<K,V> entry : entrySet()) {
            set.add(entry.getKey());
        }
        return set;
    }

    public Set<V> values() {
        Set<V> set = new HashSet<>();
        for (Map.Entry<K,V> entry : entrySet()) {
            set.add(entry.getValue());
        }
        return set;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    // TODO: Make this more efficient
    public boolean containsValue(Object value) {
        for (Map.Entry<K,V> entry : entrySet()) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    // ------------------------PRIVATE UTILS------------------------
    private RedBlackNode uncle(RedBlackNode node) {
        if (node.parent.parent.left == node.parent) {
            return node.parent.parent.right;
        }
        return node.parent.parent.left;
    }

    private boolean getColor(RedBlackNode node) {
        return node != null ? node.color : BLACK;
    }

    private void rotateLeft(RedBlackNode node) {
        RedBlackNode r = node.right;
        node.right = r.left;
        if (r.left != null) {
            r.left.parent = node;
        }
        r.parent = node.parent;
        if (node == root) {
            root = r;
        } else if (node.parent.left == node) {
            node.parent.left = r;
        } else {
            node.parent.right = r;
        }
        r.left = node;
        node.parent = r;
    }

    private void rotateRight(RedBlackNode node) {
        RedBlackNode l = node.left;
        node.left = l.right;
        if (l.right != null) {
            l.right.parent = node;
        }
        l.parent = node.parent;
        if (node == root) {
            root = l;
        } else if (node.parent.left == node) {
            node.parent.left = l;
        } else {
            node.parent.right = l;
        }
        l.right = node;
        node.parent = l;
    }

    private void shiftNodes(RedBlackNode o, RedBlackNode n) {
        if (o.parent == null) {
            root = n;
        } else if (o == o.parent.left) {
            o.parent.left = n;
        } else {
            o.parent.right = n;
        }
        if (n != null) {
            n.parent = o.parent;
        }
    }

    private RedBlackNode successor(RedBlackNode node) {
        if (node.right != null) {
            return leftmost(node.right);
        }
        RedBlackNode cur = node;
        RedBlackNode parent = cur.parent;
        while (parent != null && cur == parent.right) {
            cur = parent;
            parent = parent.parent;
        }
        return parent;
    }

    private RedBlackNode leftmost(RedBlackNode node) {
        RedBlackNode cur = node;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    private RedBlackNode rightmost(RedBlackNode node) {
        RedBlackNode cur = node;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur;
    }

    private void fixAfterInsertion(RedBlackNode node) {
        while (node != root && node.parent.color == RED) {
            if (node.parent == node.parent.parent.left) {
                if (getColor(uncle(node)) == RED) {
                    node.color = RED;
                    node.parent.color = BLACK;
                    uncle(node).color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    if (node.parent.parent != null) {
                        rotateRight(node.parent.parent);
                    }
                }
            } else {
                if (getColor(uncle(node)) == RED) {
                    node.color = RED;
                    node.parent.color = BLACK;
                    uncle(node).color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    if (node.parent.parent != null) {
                        rotateLeft(node.parent.parent);
                    }
                }
            }
        }
        root.color = BLACK;
    }

    private void delete(RedBlackNode node) {
        boolean originalColor = node.color;
        if (node.left != null && node.right != null) {
            RedBlackNode successor = successor(node);
            node.key = successor.key;
            node.value = successor.value;
            delete(successor);
            return;
        } else if (node.left != null || node.right != null) {
            RedBlackNode replacement = node.left != null ? node.left : node.right;
            shiftNodes(node, replacement);
            if (originalColor == BLACK) {
                if (replacement.color == RED) {
                    replacement.color = BLACK;
                } else {
                    fixAfterDeletion(replacement);
                }
            }
        } else {
            if (node.parent == null) {
                root = null;
                return;
            }
            if (originalColor == BLACK) {
                fixAfterDeletion(node);
            }
            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                node.parent = null;
            }
        }
    }

    private void fixAfterDeletion(RedBlackNode node) {
        RedBlackNode cur = node;
        while (cur != root && getColor(cur) == BLACK) {
            if (cur == cur.parent.left) {
                RedBlackNode sibling = cur.parent.right;
                if (getColor(sibling) == RED) {
                    sibling.color = BLACK;
                    cur.parent.color = RED;
                    rotateLeft(cur.parent);
                    sibling = cur.parent.right;
                }
                if (getColor(sibling.left) == BLACK && getColor(sibling.right) == BLACK) {
                    sibling.color = RED;
                    cur = cur.parent;
                } else {
                    if (getColor(sibling.right) == BLACK) {
                        sibling.left.color = BLACK;
                        sibling.color = RED;
                        rotateRight(sibling);
                        sibling = cur.parent.right;
                    }
                    sibling.color = cur.parent.color;
                    cur.parent.color = BLACK;
                    sibling.right.color = BLACK;
                    rotateLeft(cur.parent);
                    cur = root;
                }
            } else {
                RedBlackNode sibling = cur.parent.left;
                if (getColor(sibling) == RED) {
                    sibling.color = BLACK;
                    cur.parent.color = RED;
                    rotateRight(cur.parent);
                    sibling = cur.parent.left;
                }
                if (getColor(sibling.left) == BLACK && getColor(sibling.right) == BLACK) {
                    sibling.color = RED;
                    cur = cur.parent;
                } else {
                    if (getColor(sibling.left) == BLACK) {
                        sibling.right.color = BLACK;
                        sibling.color = RED;
                        rotateLeft(sibling);
                        sibling = cur.parent.left;
                    }
                    sibling.color = cur.parent.color;
                    cur.parent.color = BLACK;
                    sibling.left.color = BLACK;
                    rotateRight(cur.parent);
                    cur = root;
                }
            }
        }
        cur.color = BLACK;
    }
}
