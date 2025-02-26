package io.github.jdharmadh.ds.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DirectedGraph<T> extends WeightedDirectedGraph<T> {

    public void addEdge(T node1, T node2) {
        super.addEdge(node1, node2, 1);
    }

    @Override
    public List<T> shortestPath(T node1, T node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!contains(node1) || !contains(node2)) {
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
            for (T neighbor : getNeighbors(node)) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, node);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
}
