package io.github.jdharmadh.ds.graph;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.github.jdharmadh.ds.tree.UnionFind;

public class UndirectedWeightedGraph<T> {
    private Map<T, Map<T, Integer>> adjacencyList;

    public UndirectedWeightedGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void add(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (!this.adjacencyList.containsKey(node)) {
            this.adjacencyList.put(node, new HashMap<>());
        }
    }

    public void remove(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (this.adjacencyList.containsKey(node)) {
            this.adjacencyList.remove(node);
            this.adjacencyList.values().forEach(e -> e.remove(node));
        }
    }

    public void addEdge(T node1, T node2, int weight) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        this.adjacencyList.get(node1).put(node2, weight);
        this.adjacencyList.get(node2).put(node1, weight);
    }

    public void removeEdge(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        this.adjacencyList.get(node1).remove(node2);
        this.adjacencyList.get(node2).remove(node1);
    }

    public Set<T> getNodes() {
        return this.adjacencyList.keySet();
    }

    public Set<T> getNeighbors(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!this.adjacencyList.containsKey(node)) {
            throw new IllegalArgumentException("Node does not exist in the graph");
        }
        return this.adjacencyList.get(node).keySet();
    }

    public boolean containsEdge(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        return this.adjacencyList.get(node1).containsKey(node2);
    }

    public int size() {
        return this.adjacencyList.size();
    }

    public boolean isEmpty() {
        return this.adjacencyList.isEmpty();
    }

    public int getWeight(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        return this.adjacencyList.get(node1).get(node2);
    }

    public boolean contains(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        return this.adjacencyList.containsKey(node);
    }

    public List<T> shortestPath(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        PriorityQueue<T> queue = new PriorityQueue<>();
        Map<T, Integer> distance = new HashMap<>();
        Map<T, T> parent = new HashMap<>();
        for (T node : this.adjacencyList.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
            parent.put(node, null);
        }
        distance.put(node1, 0);
        queue.add(node1);
        while (!queue.isEmpty()) {
            T node = queue.poll();
            for (T neighbor : this.adjacencyList.get(node).keySet()) {
                int newDistance = distance.get(node) + this.adjacencyList.get(node).get(neighbor);
                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    parent.put(neighbor, node);
                    queue.add(neighbor);
                }
            }
        }
        List<T> path = new ArrayList<>();
        T cur = node2;
        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }
        if (path.get(path.size() - 1) != node1) {
            return null;
        }
        return path.reversed();
    }

    public List<List<T>> minimumSpanningTree() {
        List<List<T>> edges = new ArrayList<>();
        List<List<T>> tree = new ArrayList<>();
        for (T node1 : adjacencyList.keySet()) {
            for (T node2 : adjacencyList.get(node1).keySet()){
                edges.add(List.of(node1, node2));
            }
        }
        Collections.sort(edges, (a,b) -> adjacencyList.get(a.get(0)).get(a.get(1)).compareTo(adjacencyList.get(b.get(0)).get(b.get(1))));
        UnionFind<T> uf = new UnionFind<>();
        for (T node : adjacencyList.keySet()) {
            uf.add(node);
        }
        for(List<T> edge : edges) {
            if (tree.size() >= adjacencyList.size() - 1) break;
            if (uf.find(edge.get(0)).equals(uf.find(edge.get(1)))) continue;
            uf.union(edge.get(0), edge.get(1));
            tree.add(edge);
        }
        return tree;
    }

}
