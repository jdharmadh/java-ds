package io.github.jdharmadh.ds;

enum RedBlackColors {
    RED, BLACK
}

public class RedBlackTree<T extends Comparable<T>> {
    class RedBlackNode {
        T data;
        RedBlackNode left;
        RedBlackNode right;
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
        root = insert(data, root);
    }

    private RedBlackNode insert(T data, RedBlackNode cur) {
        if (cur == null) {
            RedBlackNode newNode = new RedBlackNode(data);
            return newNode;
        }
        if (data.compareTo(cur.data) < 0) {
            cur.left = insert(data, cur.left);
        } else if (data.compareTo(cur.data) > 0) {
            cur.right = insert(data, cur.right);
        } else {
            cur.data = data;
        }
        return cur;
    }

    public T query(T data) {
        return query(data, root);
    }

    private T query(T data, RedBlackNode cur){
        if (cur == null) {
            return null;
        }
        if (data.compareTo(cur.data) < 0) {
            return query(data, cur.left);
        } else if (data.compareTo(cur.data) > 0) {
            return query(data, cur.right);
        }
        return cur.data;
    }

    public void delete(T data) {
        // TODO
    }
}
