package io.github.jdharmadh.ds.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BinomialHeap {
    private BinomialNode[] nodes = new BinomialNode[8];
    private int size = 0;

    public void insert(int x) {
        BinomialNode node = new BinomialNode(x, null);
        size++;
        mergeIntoHeap(node);
        while (node.parent != null && node.parent.data > node.data) {
            int temp = node.parent.data;
            node.parent.data = node.data;
            node.data = temp;
            node = node.parent;
        }
    }

    public int findMin() {
        return nodes[indexOfMin()].data;
    }

    public int deleteMin() {
        if (size == 0)
            throw new NoSuchElementException();
        int min = indexOfMin();
        BinomialNode node = nodes[min];
        nodes[min] = null;
        for (BinomialNode b : node.nodes) {
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
            if (nodes[p].data < cur.data) {
                nodes[p].merge(cur);
                cur = nodes[p];
            } else {
                cur.merge(nodes[p]);
            }
            nodes[p] = null;
            p++;
            if (p == nodes.length) {
                BinomialNode[] new_nodes = new BinomialNode[nodes.length * 2];
                for (int i = 0; i < nodes.length; i++) {
                    new_nodes[i] = nodes[i];
                }
                nodes = new_nodes;
            }
        }
        nodes[p] = cur;
    }

    private int indexOfMin() {
        int minIndex = 0;
        for (int i = 1; i < nodes.length; i++) {
            if (nodes[i] != null && (nodes[minIndex] == null || nodes[i].data < nodes[minIndex].data))
                minIndex = i;
        }
        return minIndex;
    }

    public static void main(String[] args) {
        BinomialHeap bn = new BinomialHeap();
        for (int i = 30; i > 0; i--) {
            bn.insert(i % 4);
        }
        while (bn.size() != 0) {
            System.out.println(bn.deleteMin());
        }
    }
}

class BinomialNode {
    int data;
    BinomialNode parent;
    List<BinomialNode> nodes;

    public BinomialNode(int data, BinomialNode parent) {
        this.data = data;
        this.parent = parent;
        this.nodes = new ArrayList<>();
    }

    public int order() {
        return nodes.size();
    }

    public void merge(BinomialNode node) {
        nodes.add(node);
        node.parent = this;
    }
}