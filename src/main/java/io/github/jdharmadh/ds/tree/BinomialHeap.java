package io.github.jdharmadh.ds.tree;

import java.util.ArrayList;
import java.util.List;

public class BinomialHeap {
    BinomialNode[] nodes = new BinomialNode[8];

    public void insert(int x) {
        BinomialNode node = new BinomialNode(x, null);
        BinomialNode cur = node;

        int p = 0;
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

        while (node.parent != null && node.parent.data > node.data) {
            int temp = node.parent.data;
            node.parent.data = node.data;
            node.data = temp;
            node = node.parent;
        }

        nodes[p] = cur;
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