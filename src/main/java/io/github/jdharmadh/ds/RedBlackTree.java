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
