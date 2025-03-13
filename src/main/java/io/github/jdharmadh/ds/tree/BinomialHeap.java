package io.github.jdharmadh.ds.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class BinomialHeap<T extends Comparable<? super T>> {
    private class BinomialNode {
        T data;
        BinomialNode parent;
        List<BinomialNode> children;

        public BinomialNode(T data, BinomialNode parent) {
            this.data = data;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        public int order() {
            return children.size();
        }

        public void merge(BinomialNode node) {
            children.add(node);
            node.parent = this;
        }
    }

    private Object[] nodes = new Object[8];
    private int size = 0;

    public void insert(T data) {
        BinomialNode node = new BinomialNode(data, null);
        size++;
        mergeIntoHeap(node);
        while (node.parent != null && node.parent.data.compareTo(node.data) > 0) {
            T temp = node.parent.data;
            node.parent.data = node.data;
            node.data = temp;
            node = node.parent;
        }
    }

    public T findMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return ((BinomialNode) nodes[indexOfMin()]).data;
    }

    public T deleteMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int min = indexOfMin();
        BinomialNode node = (BinomialNode) nodes[min];
        nodes[min] = null;
        for (BinomialNode b : node.children) {
            b.parent = null;
            mergeIntoHeap(b);
        }

        size--;
        return node.data;
    }

    public int size() {
        return size;
    }

    private void mergeIntoHeap(BinomialNode node) {
        int p = node.order();
        BinomialNode cur = node;
        while (nodes[p] != null) {
            BinomialNode nodeAtP = (BinomialNode) nodes[p];
            if (nodeAtP.data.compareTo(cur.data) < 0) {
                nodeAtP.merge(cur);
                cur = nodeAtP;
            } else {
                cur.merge(nodeAtP);
            }
            nodes[p] = null;
            p++;
            Object[] new_nodes = new Object[nodes.length * 2];
            System.arraycopy(nodes, 0, new_nodes, 0, nodes.length);
            nodes = new_nodes;
            nodes = new_nodes;
        }
        nodes[p] = cur;
    }

    private int indexOfMin() {
        int minIndex = 0;
        for (int i = 1; i < nodes.length; i++) {
            if (nodes[i] != null && (nodes[minIndex] == null ||
                    ((BinomialNode) nodes[i]).data.compareTo(((BinomialNode) nodes[minIndex]).data) < 0))
                minIndex = i;
        }
        return minIndex;
    }
}