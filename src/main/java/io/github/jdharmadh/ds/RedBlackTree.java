package io.github.jdharmadh.ds;

enum RedBlackColors {
    RED, BLACK
}

public class RedBlackTree<T extends Comparable<T>> {
    class RedBlackNode {
        T data;
        RedBlackNode left;
        RedBlackNode right;
        RedBlackNode parent;
        RedBlackColors color;

        public RedBlackNode(T data) {
            this.data = data;
            this.color = RedBlackColors.RED;
        }
    }

    private RedBlackNode root;
    static RedBlackColors RED = RedBlackColors.RED;
    static RedBlackColors BLACK = RedBlackColors.BLACK;

    public RedBlackTree() {
        this.root = null;
    }

    public void insert(T data) {
        RedBlackNode prev = null;
        RedBlackNode cur = root;
        while (cur != null) {
            prev = cur;
            if (data.compareTo(cur.data) < 0) {
                cur = cur.left;
            } else if (data.compareTo(cur.data) > 0) {
                cur = cur.right;
            } else {
                cur.data = data;
                return;
            }
        }
        cur = new RedBlackNode(data);
        cur.parent = prev;
        if (prev == null) {
            root = cur;
        } else if (prev.data.compareTo(data) < 0) {
            prev.right = cur;

        } else {
            prev.left = cur;
        }

        if (parent(cur) == null) {
            cur.color = RedBlackColors.BLACK;
            return;
        }
        if (grandparent(cur) == null) { // todo: remove this by incorporating into the below logic??
            cur.color = RedBlackColors.RED;
            return;
        }

        fixAfterInsertion(cur);
    }

    private void fixAfterInsertion(RedBlackNode node) {
        // Invariant: node is red
        while (node != root && node.parent.color == RED) {
            assert (node.color == RED);
            if (node.parent == grandparent(node).left) {
                if (getColor(uncle(node)) == RED) {
                    // Case 2
                    node.color = RED;
                    node.parent.color = BLACK;
                    uncle(node).color = BLACK;
                    grandparent(node).color = RED;
                    node = grandparent(node);
                } else {
                    // Case 3
                    if (node == parent(node).right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    parent(node).color = BLACK;
                    grandparent(node).color = RED;
                    if (grandparent(node) != null)
                        rotateRight(grandparent(node));
                }
            } else {
                if (getColor(uncle(node)) == RED) {
                    // Case 2
                    node.color = RED;
                    node.parent.color = BLACK;
                    uncle(node).color = BLACK;
                    grandparent(node).color = RED;
                    node = grandparent(node);
                } else {
                    if (node == parent(node).left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    parent(node).color = BLACK;
                    grandparent(node).color = RED;
                    if (grandparent(node) != null)
                        rotateLeft(grandparent(node));
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(RedBlackNode node) {
        RedBlackNode r = node.right;
        node.right = r.left;
        if (r.left != null)
            r.left.parent = node;
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
        if (l.right != null)
            l.right.parent = node;
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

    private RedBlackNode parent(RedBlackNode node) {
        return node.parent;
    }

    private RedBlackNode uncle(RedBlackNode node) {
        assert (node.parent != null && node.parent.parent != null);
        if (node.parent.parent.left == node.parent)
            return node.parent.parent.right;
        return node.parent.parent.left;
    }

    private RedBlackNode grandparent(RedBlackNode node) {
        assert (node.parent != null);
        return node.parent.parent;
    }

    private RedBlackColors getColor(RedBlackNode node) {
        return node != null ? node.color : RedBlackColors.BLACK;
    }

    public void checkInvariant() {
        assert (root.color == RedBlackColors.BLACK);
        checkInvariantHelper(root);
        assert (blackHeight(root) != -1);
    }

    private void checkInvariantHelper(RedBlackNode cur) {
        if (cur == null) return;
        if (getColor(cur) == RED) {
            assert (getColor(cur.left) != RED && getColor(cur.right) != RED);
        }
        checkInvariantHelper(cur.left);
        checkInvariantHelper(cur.right);
    }

    private int blackHeight(RedBlackNode node) {
        if (node == null) return 1;
        int leftHeight = blackHeight(node.left);
        int rightHeight = blackHeight(node.right);
        assert (leftHeight == rightHeight);
        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) return -1;
        return leftHeight + (node.color == BLACK ? 1 : 0);
    }

    public T query(T data) {
        RedBlackNode cur = root;
        while (cur != null) {
            if (data.compareTo(cur.data) < 0) {
                cur = cur.left;
            } else if (data.compareTo(cur.data) > 0) {
                cur = cur.right;
            } else {
                return data;
            }
        }
        return null;
    }

    public void delete(T data) {
        // TODO
    }
}
