package io.github.jdharmadh.ds.graph;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Set;

public class UndirectedWeightedGraphTest {

    private UndirectedWeightedGraph<Integer> graph;

    @Before
    public void setUp() {
        graph = new UndirectedWeightedGraph<>();
    }

    @Test
    public void testAddNode() {
        graph.add(1);
        Set<Integer> nodes = graph.getNodes();
        assertTrue(nodes.contains(1));
        assertEquals(1, graph.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullNode() {
        graph.add(null);
    }

    @Test
    public void testRemoveNode() {
        graph.add(1);
        graph.add(2);
        graph.addEdge(1, 2, 1);
        graph.remove(1);
        Set<Integer> nodes = graph.getNodes();
        assertFalse(nodes.contains(1));
        Set<Integer> neighbors = graph.getNeighbors(2);
        assertFalse(neighbors.contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullNode() {
        graph.remove(null);
    }

    @Test
    public void testAddEdge() {
        graph.add(1);
        graph.add(2);
        graph.addEdge(1, 2, 1);
        Set<Integer> neighbors1 = graph.getNeighbors(1);
        Set<Integer> neighbors2 = graph.getNeighbors(2);
        assertTrue(neighbors1.contains(2));
        assertTrue(neighbors2.contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNonExistentNodes() {
        graph.add(1);
        graph.addEdge(1, 2, 1);
    }

    @Test
    public void testRemoveEdge() {
        graph.add(1);
        graph.add(2);
        graph.addEdge(1, 2, 1);
        graph.removeEdge(1, 2);
        Set<Integer> neighbors1 = graph.getNeighbors(1);
        Set<Integer> neighbors2 = graph.getNeighbors(2);
        assertFalse(neighbors1.contains(2));
        assertFalse(neighbors2.contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNonExistentNodes() {
        graph.add(1);
        graph.removeEdge(1, 2);
    }

    @Test
    public void testGetNeighbors() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 1);
        Set<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNeighborsForNonExistentNode() {
        graph.getNeighbors(99);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(graph.isEmpty());
        graph.add(1);
        assertFalse(graph.isEmpty());
    }

    @Test
    public void testPathBetween() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(2, 4, 1);
        List<Integer> path = graph.shortestPath(1, 3);
        assertNotNull(path);
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(3), path.get(path.size() - 1));
    }

    @Test
    public void testPathBetweenNoPath() {
        graph.add(1);
        graph.add(2);
        List<Integer> path = graph.shortestPath(1, 2);
        assertNull(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPathBetweenNonExistentNodes() {
        graph.add(1);
        graph.shortestPath(1, 2);
    }
    @Test
    public void testContainsEdge() {
        graph.add(1);
        graph.add(2);
        assertFalse(graph.containsEdge(1, 2));
        graph.addEdge(1, 2, 5);
        assertTrue(graph.containsEdge(1, 2));
        assertTrue(graph.containsEdge(2, 1));
    }

    @Test
    public void testGetWeight() {
        graph.add(1);
        graph.add(2);
        graph.addEdge(1, 2, 5);
        assertEquals(5, graph.getWeight(1, 2));
        assertEquals(5, graph.getWeight(2, 1));
    }

    @Test
    public void testContains() {
        assertFalse(graph.contains(1));
        graph.add(1);
        assertTrue(graph.contains(1));
    }

    @Test
    public void testMinimumSpanningTreeEmpty() {
        List<List<Integer>> mst = graph.minimumSpanningTree();
        assertTrue(mst.isEmpty());
    }

    @Test
    public void testMinimumSpanningTreeSingleNode() {
        graph.add(1);
        List<List<Integer>> mst = graph.minimumSpanningTree();
        assertTrue(mst.isEmpty());
    }

    @Test
    public void testMinimumSpanningTreeSimple() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 3);
        graph.addEdge(1, 3, 7);
        
        List<List<Integer>> mst = graph.minimumSpanningTree();
        assertEquals(2, mst.size());
        
        boolean has23Edge = false;
        boolean has12Edge = false;
        
        for (List<Integer> edge : mst) {
            if ((edge.get(0) == 2 && edge.get(1) == 3) || (edge.get(0) == 3 && edge.get(1) == 2)) {
                has23Edge = true;
            }
            if ((edge.get(0) == 1 && edge.get(1) == 2) || (edge.get(0) == 2 && edge.get(1) == 1)) {
                has12Edge = true;
            }
        }
        
        assertTrue(has23Edge);
        assertTrue(has12Edge);
    }

    @Test
    public void testMinimumSpanningTreeComplex() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        graph.add(5);
        graph.add(6);
        
        graph.addEdge(1, 2, 6);
        graph.addEdge(1, 3, 1);
        graph.addEdge(1, 4, 5);
        graph.addEdge(2, 3, 5);
        graph.addEdge(2, 5, 3);
        graph.addEdge(3, 4, 5);
        graph.addEdge(3, 5, 6);
        graph.addEdge(3, 6, 4);
        graph.addEdge(4, 6, 2);
        graph.addEdge(5, 6, 6);
        
        List<List<Integer>> mst = graph.minimumSpanningTree();
        
        assertEquals(5, mst.size());
        
        int totalWeight = 0;
        for (List<Integer> edge : mst) {
            totalWeight += graph.getWeight(edge.get(0), edge.get(1));
        }
        
        assertEquals(15, totalWeight);
    }

    @Test
    public void testMinimumSpanningTreeDisconnected() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        
        graph.addEdge(1, 2, 1);
        graph.addEdge(3, 4, 2);
        
        List<List<Integer>> mst = graph.minimumSpanningTree();
        
        assertEquals(2, mst.size());
        
        int totalWeight = 0;
        for (List<Integer> edge : mst) {
            totalWeight += graph.getWeight(edge.get(0), edge.get(1));
        }
        
        assertEquals(3, totalWeight);
    }

    @Test
    public void testMinimumSpanningTreeWithCycles() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        
        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 1, 6);
        graph.addEdge(1, 3, 4);
        
        List<List<Integer>> mst = graph.minimumSpanningTree();
        
        assertEquals(3, mst.size());
        
        int totalWeight = 0;
        for (List<Integer> edge : mst) {
            totalWeight += graph.getWeight(edge.get(0), edge.get(1));
        }
        
        assertEquals(9, totalWeight);
    }
}
