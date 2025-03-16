package io.github.jdharmadh.ds.graph;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UndirectedGraphTest {

    private UndirectedGraph<Integer> graph;

    @Before
    public void setUp() {
        graph = new UndirectedGraph<>();
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
        graph.addEdge(1, 2);
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
        graph.addEdge(1, 2);
        Set<Integer> neighbors1 = graph.getNeighbors(1);
        Set<Integer> neighbors2 = graph.getNeighbors(2);
        assertTrue(neighbors1.contains(2));
        assertTrue(neighbors2.contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNonExistentNodes() {
        graph.add(1);
        graph.addEdge(1, 2);
    }

    @Test
    public void testRemoveEdge() {
        graph.add(1);
        graph.add(2);
        graph.addEdge(1, 2);
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
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
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
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
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
    public void testIsTreeValid() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        assertTrue(graph.isTree());
    }

    @Test
    public void testIsTreeWithCycle() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        assertFalse(graph.isTree());
    }

    @Test
    public void testIsTreeDisconnected() {
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        graph.addEdge(1, 2);

        assertFalse(graph.isTree());
    }

    @Test
    public void testIsTreeEmptyGraph() {
        assertTrue(graph.isTree());
    }

    @Test
    public void testPruferSequenceLinearTree() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        graph.add(5);
        
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        
        Map<Integer, Integer> labels = new HashMap<>();
        Map<Integer, Integer> reverseLabels = new HashMap<>();
        int index = 0;
        for (Integer node : graph.getNodes()) {
            labels.put(node, index);
            reverseLabels.put(index, node);
            index++;
        }
        
        List<Integer> prufer = graph.pruferSequence(labels);
        assertEquals(3, prufer.size());
        
        UndirectedGraph<Integer> newGraph = new UndirectedGraph<>(prufer, reverseLabels);
        
        assertEquals(graph.size(), newGraph.size());
        
        for (Integer node : graph.getNodes()) {
            assertTrue(newGraph.getNodes().contains(node));
            assertEquals(graph.getNeighbors(node), newGraph.getNeighbors(node));
        }
    }

    @Test
    public void testPruferSequenceStarTree() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        graph.add("center");
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.add("d");
        
        graph.addEdge("center", "a");
        graph.addEdge("center", "b");
        graph.addEdge("center", "c");
        graph.addEdge("center", "d");
        
        Map<String, Integer> labels = new HashMap<>();
        Map<Integer, String> reverseLabels = new HashMap<>();
        int index = 0;
        for (String node : graph.getNodes()) {
            labels.put(node, index);
            reverseLabels.put(index, node);
            index++;
        }
        
        List<Integer> prufer = graph.pruferSequence(labels);
        assertEquals(3, prufer.size());
        
        int centerIdx = labels.get("center");
        for (int code : prufer) {
            assertEquals(centerIdx, code);
        }
        
        UndirectedGraph<String> newGraph = new UndirectedGraph<>(prufer, reverseLabels);
        assertEquals(graph.size(), newGraph.size());
        
        for (String node : graph.getNodes()) {
            assertTrue(newGraph.getNodes().contains(node));
            assertEquals(graph.getNeighbors(node).size(), newGraph.getNeighbors(node).size());
        }
    }

    @Test
    public void testPruferSequenceBinaryTree() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        for (int i = 1; i <= 7; i++) {
            graph.add(i);
        }
        
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        graph.addEdge(3, 7);
        
        Map<Integer, Integer> labels = new HashMap<>();
        Map<Integer, Integer> reverseLabels = new HashMap<>();
        int index = 0;
        for (Integer node : graph.getNodes()) {
            labels.put(node, index);
            reverseLabels.put(index, node);
            index++;
        }
        
        List<Integer> prufer = graph.pruferSequence(labels);
        assertEquals(5, prufer.size());
        
        UndirectedGraph<Integer> newGraph = new UndirectedGraph<>(prufer, reverseLabels);
        
        assertEquals(graph.size(), newGraph.size());
        for (Integer node : graph.getNodes()) {
            assertTrue(newGraph.getNodes().contains(node));
            assertEquals(graph.getNeighbors(node).size(), newGraph.getNeighbors(node).size());
            
            for (Integer neighbor : graph.getNeighbors(node)) {
                assertTrue(newGraph.getNeighbors(node).contains(neighbor));
            }
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPruferSequenceNonTree() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        graph.add(1);
        graph.add(2);
        graph.add(3);
        graph.add(4);
        
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1); // Creates a cycle
        
        Map<Integer, Integer> labels = new HashMap<>();
        for (Integer node : graph.getNodes()) {
            labels.put(node, node - 1);
        }
        
        graph.pruferSequence(labels); // Should throw exception
    }
}
