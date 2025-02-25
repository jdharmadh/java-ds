package io.github.jdharmadh.ds.graph;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class UndirectedGraph<T> {
    private final Map<T, Set<T>> adjacencyList;

    public UndirectedGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void add(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (!this.adjacencyList.containsKey(node)) {
            this.adjacencyList.put(node, new HashSet<>());
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

    public void addEdge(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        this.adjacencyList.get(node1).add(node2);
        this.adjacencyList.get(node2).add(node1);
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
        return this.adjacencyList.get(node);
    }

    public int size() {
        return this.adjacencyList.size();
    }

    public boolean isEmpty() {
        return this.adjacencyList.isEmpty();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<T, Set<T>> entry : this.adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public List<T> pathBetween(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!this.adjacencyList.containsKey(node1) || !this.adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }

        Map<T, T> visited = new HashMap<>(); // node to its parent
        Queue<T> queue = new LinkedList<>();
        queue.add(node1);
        while (!queue.isEmpty()) {
            T node = queue.remove();
            if (node.equals(node2)) {
                List<T> path = new ArrayList<>();
                T cur = node2;
                while (!cur.equals(node1)) {
                    path.add(cur);
                    cur = visited.get(cur);
                }
                path.add(node1);
                return path.reversed();
            }
            for (T neighbor : adjacencyList.get(node)) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, node);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
}
