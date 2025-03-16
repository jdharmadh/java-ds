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
        adjacencyList = new HashMap<>();
    }

    public UndirectedGraph(List<Integer> pruferCode, Map<Integer, T> labels) {
        adjacencyList = new HashMap<>();
        int n = pruferCode.size() + 2;
        for (T node : labels.values()) {
            adjacencyList.put(node, new HashSet<>());
        }
        int[] degree = new int[n];
        for (int i = 0; i < n; i++) {
            degree[i] = 1;
        }
        for (int a : pruferCode) {
            degree[a] += 1;
        }
        for (int i : pruferCode) {
            for (int j = 0; j < n; j++) {
                if (degree[j] == 1) {
                    addEdge(labels.get(i), labels.get(j));
                    degree[i]--;
                    degree[j]--;
                    break;
                }
            }
        }
        int u = -1;
        int v = -1;
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                if (u == -1) {
                    u = i;
                } else {
                    v = i;
                    break;
                }
            }
        }
        addEdge(labels.get(u), labels.get(v));
    }

    public void add(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (!adjacencyList.containsKey(node)) {
            adjacencyList.put(node, new HashSet<>());
        }
    }

    public void remove(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (adjacencyList.containsKey(node)) {
            adjacencyList.remove(node);
            adjacencyList.values().forEach(e -> e.remove(node));
        }
    }

    public void addEdge(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        adjacencyList.get(node1).add(node2);
        adjacencyList.get(node2).add(node1);
    }

    public void removeEdge(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }
        adjacencyList.get(node1).remove(node2);
        adjacencyList.get(node2).remove(node1);
    }

    public Set<T> getNodes() {
        return adjacencyList.keySet();
    }

    public Set<T> getNeighbors(T node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!adjacencyList.containsKey(node)) {
            throw new IllegalArgumentException("Node does not exist in the graph");
        }
        return adjacencyList.get(node);
    }

    public int size() {
        return adjacencyList.size();
    }

    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    public boolean isTree() {
        if (adjacencyList.isEmpty()) {
            return true;
        }
        Map<T, T> parent = new HashMap<>();
        Queue<T> queue = new LinkedList<>();
        T start = adjacencyList.keySet().iterator().next();
        parent.put(start, null);
        queue.add(start);
        while (!queue.isEmpty()) {
            T node = queue.remove();
            for (T neighbor : adjacencyList.get(node)) {
                if (parent.get(node) != null && neighbor.equals(parent.get(node))) {
                    continue;
                }
                if (parent.containsKey(neighbor)) {
                    return false;
                }
                parent.put(neighbor, node);
                queue.add(neighbor);
            }
        }
        return parent.size() == adjacencyList.size();
    }

    public List<T> shortestPath(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new IllegalArgumentException("Nodes do not exist in the graph");
        }

        Map<T, T> visited = new HashMap<>();
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

    public List<Integer> pruferSequence(Map<T, Integer> labels) {
        if (!isTree()) {
            throw new UnsupportedOperationException("Graph is not a tree");
        }
        int n = adjacencyList.size();
        Map<Integer, T> indexNode = new HashMap<>();
        for (T node : labels.keySet()) {
            indexNode.put(labels.get(node), node);
        }
        int[] degree = new int[n];
        for (T node : adjacencyList.keySet()) {
            degree[labels.get(node)] = adjacencyList.get(node).size();
        }
        List<Integer> prufer = new ArrayList<>();
        for (int i = 0; i < n - 2; i++) {
            int leaf = -1;
            for (int j = 0; j < n; j++) {
                if (degree[j] == 1) {
                    leaf = j;
                    break;
                }
            }
            if (leaf == -1) {
                throw new IllegalStateException("No leaf found");
            }
            degree[leaf]--;
            for (T neighbor : adjacencyList.get(indexNode.get(leaf))) {
                if (degree[labels.get(neighbor)] == 0) {
                    continue;
                }
                degree[labels.get(neighbor)]--;
                prufer.add(labels.get(neighbor));
            }
        }
        return prufer;
    }
}
