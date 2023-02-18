package com.jaeheonshim.jvisibility;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.stream.util.EventReaderDelegate;

public class BSTOpenEdges implements OpenEdges {
    public static class Node {
        public Edge data;
        public Node left;
        public Node right;

        public Node(Edge edge) {
            this.data = edge;
        }
    }

    private Node root;
    private int size = 0;

    public void insert(Point p1, Point p2, Edge edge) {
        double dist = GeometryMath.pointEdgeDistance(p1, p2, edge);
        root = insert(p1, p2, edge, dist, root);
        size += 1;
    }

    private Node insert(Point p1, Point p2, Edge edge, double dist, Node n) {
        if(n == null) {
            return new Node(edge);
        } else {
            double cmpDist = GeometryMath.pointEdgeDistance(p1, p2, n.data);
            if(cmpDist >= dist) {
                n.left = insert(p1, p2, edge, dist, n.left);
            } else {
                n.right = insert(p1, p2, edge, dist, n.right);
            }
        }

        return n;
    }

    @Override
    public void delete(Point p1, Point p2, Edge edge) {
        this.root = delete(p1, p2, edge, root);
    }

    @Override
    public Edge smallest() {
        return smallest(root).data;
    }

    private Node smallest(Node n) {
        while(n.left != null) {
            n = n.left;
        }
        return n;
    }

    private Node delete(Point p1, Point p2, Edge edge, Node node) {
        double dist = GeometryMath.pointEdgeDistance(p1, p2, edge);
        if(node == null) return null;

        double cmpDist = GeometryMath.pointEdgeDistance(p1, p2, node.data);
        if(node.data.equals(edge)) {
            size -= 1;
            if(node.left == null) {
                return node.right;
            } else if(node.right == null) {
                return node.left;
            } else {
                Node successor = smallest(node.right);
                Edge data = successor.data;
                delete(p1, p2, data, node);
                size += 1;
                node.data = data;
                return node;
            }
        } else if(dist < cmpDist) {
            node.left = delete(p1, p2, edge, node.left);
        } else {
            node.right = delete(p1, p2, edge, node.right);
        }

        return node;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Edge> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<Edge> {
        private Deque<Node> nodeStack = new LinkedList<>();

        public BSTIterator() {
            Node n = BSTOpenEdges.this.root;
            while(n != null) {
                nodeStack.push(n);
                n = n.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        @Override
        public Edge next() {
            Node n = nodeStack.pop();
            Edge item = n.data;
            if(n.right != null) {
                Node o = n.right;
                while(o != null) {
                    nodeStack.push(o);
                    o = o.left;
                }
            }

            return item;
        }
    }

    public Node getRoot() {
        return root;
    }
}