package io.github.jdharmadh.ds;

public class RedBlackTree<T extends Comparable<T>> {
    static boolean RED = true;
    static boolean BLACK = false;

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

    private RedBlackNode root;

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

        if (cur.parent == null) {
            cur.color = BLACK;
            return;
        }

        if (cur.parent.parent == null) {
            cur.color = RED;
            return;
        }

        fixAfterInsertion(cur);
    }

    private void fixAfterInsertion(RedBlackNode node) {
        while (node != root && node.parent.color == RED) {
            assert (node.color == RED);
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
                    if (node.parent.parent != null)
                        rotateRight(node.parent.parent);
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
                    if (node.parent.parent != null)
                        rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    public void remove(T data) {
        RedBlackNode cur = root;
        while (cur != null) {
            if (data.compareTo(cur.data) < 0) {
                cur = cur.left;
            } else if (data.compareTo(cur.data) > 0) {
                cur = cur.right;
            } else {
                delete(cur);
                return;
            }
        }
    }

    public void delete(RedBlackNode node) {
        boolean originalColor = node.color;
        if (node.left != null && node.right != null) {
            RedBlackNode successor = successor(node);
            node.data = successor.data;
            delete(successor);
            return;
        } else if (node.left != null || node.right != null) {
            // one child
            RedBlackNode replacement = node.left != null ? node.left : node.right;
            shiftNodes(node, replacement);
            if (originalColor == BLACK) {
                if (replacement.color == RED) {
                    replacement.color = BLACK; // Maintain black height
                } else {
                    fixAfterDeletion(replacement); // Handle double-black case
                }
            }
        } else {
            // no children
            if (node.parent == null) {
                // we are the only node in the tree, so we're done?
                // assert (node == root && node.color == BLACK);
                assert (node == root);
                root = null;
                return;
            }
            // we are a leaf, fix after deletion and then unlink ourselves
            if (originalColor == BLACK) {
                fixAfterDeletion(node);
            }
            if (node.parent != null) {
                if (node == node.parent.left)
                    node.parent.left = null;
                else
                    node.parent.right = null;
                node.parent = null;
            }
        }
    }

    private void fixAfterDeletion(RedBlackNode node) {
        RedBlackNode cur = node;
        while (cur != root && cur.color == BLACK) {
            if (cur == cur.parent.left) {
                RedBlackNode sibling = cur.parent.right;
                assert (sibling != null);
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    cur.parent.color = RED;
                    rotateLeft(cur.parent);
                    sibling = node.parent.right;
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
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    cur.parent.color = RED;
                    rotateRight(cur.parent);
                    sibling = node.parent.left;
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

    public RedBlackNode successor(RedBlackNode node) {
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

    private void shiftNodes(RedBlackNode o, RedBlackNode n) {
        if (o.parent == null)
            root = n;
        else if (o == o.parent.left)
            o.parent.left = n;
        else
            o.parent.right = n;
        if (n != null)
            n.parent = o.parent;
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

    private RedBlackNode uncle(RedBlackNode node) {
        if (node.parent.parent.left == node.parent)
            return node.parent.parent.right;
        return node.parent.parent.left;
    }

    private boolean getColor(RedBlackNode node) {
        return node != null ? node.color : BLACK;
    }

    public void checkInvariant() {
        assert (root.color == BLACK);
        checkInvariantHelper(root);
        assert (blackHeight(root) != -1);
    }

    private void checkInvariantHelper(RedBlackNode cur) {
        if (cur == null)
            return;
        if (getColor(cur) == RED) {
            assert (getColor(cur.left) != RED && getColor(cur.right) != RED);
        }
        checkInvariantHelper(cur.left);
        checkInvariantHelper(cur.right);
    }

    private int blackHeight(RedBlackNode node) {
        if (node == null)
            return 1;
        int leftHeight = blackHeight(node.left);
        int rightHeight = blackHeight(node.right);
        assert (leftHeight == rightHeight);
        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight)
            return -1;
        return leftHeight + (node.color == BLACK ? 1 : 0);
    }

    public T get(T data) {
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

    public String toString() {
        return toString(root);
    }

    private String toString(RedBlackNode cur) {
        if (cur == null)
            return "";
        return cur.toString() + " " + toString(cur.left) + " " + toString(cur.right);
    }
}
