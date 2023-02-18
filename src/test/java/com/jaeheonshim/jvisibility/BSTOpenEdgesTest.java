package com.jaeheonshim.jvisibility;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BSTOpenEdgesTest {
    @Test
    public void testInsertion() {
        BSTOpenEdges edgesTree = new BSTOpenEdges();
        Point tP1 = new Point(-6, 0);
        Point tP2 = new Point(Double.MAX_VALUE, 0);

        Edge testEdge1 = new Edge(new Point(1, -1), new Point(1, 1));
        Edge testEdge2 = new Edge(new Point(0, -1), new Point(0, 1));
        Edge testEdge3 = new Edge(new Point(3, -1), new Point(3, 1));

        edgesTree.insert(tP1, tP2, testEdge1);
        edgesTree.insert(tP1, tP2, testEdge2);
        edgesTree.insert(tP1, tP2, testEdge3);

        List<Edge> order = new ArrayList<>();
        for(Edge e : edgesTree) {
            order.add(e);
        }

        // edges should be in order in the tree
        assertEquals(testEdge2, order.get(0));
        assertEquals(testEdge1, order.get(1));
        assertEquals(testEdge3, order.get(2));
        assertEquals(3, edgesTree.size());
    }

    @Test
    public void testDeletion() {
        BSTOpenEdges edgesTree = new BSTOpenEdges();
        Point tP1 = new Point(-6, 0);
        Point tP2 = new Point(Double.MAX_VALUE, 0);

        Edge testEdge1 = new Edge(new Point(1, -1), new Point(1, 1));
        Edge testEdge2 = new Edge(new Point(0, -1), new Point(0, 1));
        Edge testEdge3 = new Edge(new Point(3, -1), new Point(3, 1));
        Edge testEdge4 = new Edge(new Point(2, -1), new Point(2, 1));

        edgesTree.insert(tP1, tP2, testEdge1);
        edgesTree.insert(tP1, tP2, testEdge4);
        edgesTree.insert(tP1, tP2, testEdge3);
        edgesTree.insert(tP1, tP2, testEdge2);

        assertEquals(4, edgesTree.size());

        edgesTree.delete(tP1, tP2, testEdge4);

        List<Edge> order = new ArrayList<>();
        for(Edge e : edgesTree) {
            order.add(e);
        }

        // edges should be in order in the tree
        assertEquals(testEdge2, order.get(0));
        assertEquals(testEdge1, order.get(1));
        assertEquals(testEdge3, order.get(2));
        assertEquals(3, edgesTree.size());
    }

    @Test
    public void bigTest() {
        Point tP1 = new Point(0, 0);
        Point tP2 = new Point(Double.MAX_VALUE, 0);

        BSTOpenEdges edgesTree = new BSTOpenEdges();

        Edge testEdge1 = new Edge(new Point(5, -1), new Point(5, 1));
        Edge testEdge2 = new Edge(new Point(3, -1), new Point(3, 1));
        Edge testEdge3 = new Edge(new Point(1, -1), new Point(1, 1));
        Edge testEdge4 = new Edge(new Point(2, -1), new Point(2, 1));
        Edge testEdge5 = new Edge(new Point(8, -1), new Point(8, 1));
        Edge testEdge6 = new Edge(new Point(7, -1), new Point(7, 1));
        Edge testEdge7 = new Edge(new Point(42, -1), new Point(42, 1));
        Edge testEdge8 = new Edge(new Point(4, -1), new Point(4, 1));

        edgesTree.insert(tP1, tP2, testEdge1);
        edgesTree.insert(tP1, tP2, testEdge2);
        edgesTree.insert(tP1, tP2, testEdge3);
        edgesTree.insert(tP1, tP2, testEdge4);
        edgesTree.insert(tP1, tP2, testEdge5);
        edgesTree.insert(tP1, tP2, testEdge6);
        edgesTree.insert(tP1, tP2, testEdge7);
        edgesTree.insert(tP1, tP2, testEdge8);

        // test the entire structure of the tree
        assertEquals(testEdge1, edgesTree.getRoot().data);
        assertEquals(testEdge2, edgesTree.getRoot().left.data);
        assertEquals(testEdge3, edgesTree.getRoot().left.left.data);
        assertEquals(testEdge8, edgesTree.getRoot().left.right.data);
        assertEquals(testEdge4, edgesTree.getRoot().left.left.right.data);
        assertEquals(testEdge5, edgesTree.getRoot().right.data);
        assertEquals(testEdge6, edgesTree.getRoot().right.left.data);
        assertEquals(testEdge7, edgesTree.getRoot().right.right.data);

        // test the size of the tree
        assertEquals(8, edgesTree.size());

        // test random deletions and size after
        // leaf deletion
        edgesTree.delete(tP1, tP2, testEdge7);
        assertNull(edgesTree.getRoot().right.right);
        assertEquals(7, edgesTree.size());

        // single child deletion
        edgesTree.delete(tP1, tP2, testEdge3);
        assertNotEquals(edgesTree.getRoot().left.left.data, testEdge3);
        assertEquals(testEdge4, edgesTree.getRoot().left.left.data);
        assertEquals(6, edgesTree.size());

        // intermediate node deletion
        edgesTree.delete(tP1, tP2, testEdge2);
        assertEquals(testEdge8, edgesTree.getRoot().left.data);
        assertEquals(testEdge4, edgesTree.getRoot().left.left.data);
        assertEquals(5, edgesTree.size());
    }

    @Test
    public void smallestTest() {
        Point tP1 = new Point(0, 0);
        Point tP2 = new Point(Double.MAX_VALUE, 0);

        BSTOpenEdges edgesTree = new BSTOpenEdges();

        Edge testEdge1 = new Edge(new Point(5, -1), new Point(5, 1));
        Edge testEdge2 = new Edge(new Point(3, -1), new Point(3, 1));
        Edge testEdge3 = new Edge(new Point(1, -1), new Point(1, 1));
        Edge testEdge4 = new Edge(new Point(2, -1), new Point(2, 1));
        Edge testEdge5 = new Edge(new Point(8, -1), new Point(8, 1));
        Edge testEdge6 = new Edge(new Point(7, -1), new Point(7, 1));
        Edge testEdge7 = new Edge(new Point(42, -1), new Point(42, 1));
        Edge testEdge8 = new Edge(new Point(4, -1), new Point(4, 1));

        edgesTree.insert(tP1, tP2, testEdge1);
        edgesTree.insert(tP1, tP2, testEdge2);
        edgesTree.insert(tP1, tP2, testEdge3);
        edgesTree.insert(tP1, tP2, testEdge4);
        edgesTree.insert(tP1, tP2, testEdge5);
        edgesTree.insert(tP1, tP2, testEdge6);
        edgesTree.insert(tP1, tP2, testEdge7);
        edgesTree.insert(tP1, tP2, testEdge8);

        assertEquals(edgesTree.smallest(), testEdge3);
    }
}