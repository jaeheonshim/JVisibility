package com.jaeheonshim.jvisibility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PolygonTest {
    @Test
    public void testEdgeGeneration() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, -1),
                new Point(0, -1)
        };

        Polygon p = new Polygon(points);

        Edge[] edges = {
            new Edge(points[0], points[1]),
            new Edge(points[1], points[2]),
            new Edge(points[2], points[3]),
            new Edge(points[3], points[0])
        };

        assertArrayEquals(edges, p.getEdges().toArray());
    }
}
