package io.github.jdharmadh.ds.graph;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Set;

public class DirectedGraphTest {

    private DirectedGraph<Integer> graph;

    @Before
    public void setUp() {
        graph = new DirectedGraph<>();
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
        assertTrue(neighbors1.contains(2));
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
        assertFalse(neighbors1.contains(2));
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
}