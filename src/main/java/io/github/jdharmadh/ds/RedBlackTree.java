package io.github.jdharmadh.ds;

import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

public class RedBlackTree<T extends Comparable<T>> {
    private class RedBlackNode {
        T data;
        RedBlackNode left;
        RedBlackNode right;
        RedBlackNode parent;
        boolean color;

        public RedBlackNode(T data) {
            this.data = data;
            this.color = RED;
        }

        public String toString() {
            return data.toString();
        }
    }

    static boolean RED = true;
    static boolean BLACK = false;
    private RedBlackNode root;
    int size;

    public RedBlackTree() {
        this.root = null;
        size = 0;
    }

    // ------------------------PUBLIC METHODS------------------------
    public T put(T data) {
        RedBlackNode prev = null;
        RedBlackNode cur = root;
        while (cur != null) {
            prev = cur;
            if (data.compareTo(cur.data) < 0) {
                cur = cur.left;
            } else if (data.compareTo(cur.data) > 0) {
                cur = cur.right;
            } else {
                T oldData = cur.data;
                cur.data = data;
                return oldData;
            }
        }
        size++;
        cur = new RedBlackNode(data);
        cur.parent = prev;
        if (prev == null) {
            root = cur;
        } else if (prev.data.compareTo(data) < 0) {
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

    public T remove(T data) {
        RedBlackNode cur = root;
        while (cur != null) {
            if (data.compareTo(cur.data) < 0) {
                cur = cur.left;
            } else if (data.compareTo(cur.data) > 0) {
                cur = cur.right;
            } else {
                T oldData = cur.data;
                delete(cur);
                size--;
                return oldData;
            }
        }
        return null;
    }

    public T get(T data) {
        RedBlackNode cur = root;
        while (cur != null) {
            if (data.compareTo(cur.data) < 0) {
                cur = cur.left;
            } else if (data.compareTo(cur.data) > 0) {
                cur = cur.right;
            } else {
                return cur.data;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    // ------------------------PUBLIC UTILS------------------------
    public void checkInvariant() {
        assert (getColor(root) == BLACK);
        checkInvariantHelper(root);
        assert (blackHeight(root) != -1);
    }

    public T min() {
        return leftmost(root).data;
    }

    public T max() {
        return rightmost(root).data;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public SortedSet<? extends T> sortedData() {
        SortedSet<T> set = new TreeSet<T>();
        RedBlackNode cur = root;
        Stack<RedBlackNode> stack = new Stack<>();
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            set.add(cur.data);
            cur = cur.right;
        }
        return set;
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

    private void checkInvariantHelper(RedBlackNode cur) {
        if (cur == null) {
            return;
        }
        if (getColor(cur) == RED) {
            assert (getColor(cur.left) != RED && getColor(cur.right) != RED);
        }
        checkInvariantHelper(cur.left);
        checkInvariantHelper(cur.right);
    }

    private int blackHeight(RedBlackNode node) {
        if (node == null) {
            return 1;
        }
        int leftHeight = blackHeight(node.left);
        int rightHeight = blackHeight(node.right);
        assert (leftHeight == rightHeight);
        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) {
            return -1;
        }
        return leftHeight + (node.color == BLACK ? 1 : 0);
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
            node.data = successor.data;
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
