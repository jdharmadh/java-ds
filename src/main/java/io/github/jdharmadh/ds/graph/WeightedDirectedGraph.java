package io.github.jdharmadh.ds.graph;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WeightedDirectedGraph<T> {
    private Map<T, Map<T, Integer>> adjacencyList;

    public WeightedDirectedGraph() {
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
    }

    public void removeEdge(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        this.adjacencyList.get(node1).remove(node2);
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
        Map<T, Integer> distance = new HashMap<>();
        Map<T, T> parent = new HashMap<>();
        for (T node : this.adjacencyList.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
            parent.put(node, null);
        }
        distance.put(node1, 0);

        for (int i = 0; i < this.size() - 1; i++) {
            for (T node : this.adjacencyList.keySet()) {
                for (T neighbor : this.adjacencyList.get(node).keySet()) {
                    if (distance.get(node) + this.adjacencyList.get(node).get(neighbor) < distance.get(neighbor)) {
                        distance.put(neighbor, distance.get(node) + this.adjacencyList.get(node).get(neighbor));
                        parent.put(neighbor, node);
                    }
                }
            }
        }

        for (T node : this.adjacencyList.keySet()) {
            for (T neighbor : this.adjacencyList.get(node).keySet()) {
                if (distance.get(node) + this.adjacencyList.get(node).get(neighbor) < distance.get(neighbor)) {
                    throw new IllegalArgumentException("Graph contains negative cycle");
                }
            }
        }
        if (distance.get(node2) == Integer.MAX_VALUE) {
            return null;
        }
        List<T> path = new ArrayList<>();
        T cur = node2;
        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }
        return path.reversed();
    }

}
