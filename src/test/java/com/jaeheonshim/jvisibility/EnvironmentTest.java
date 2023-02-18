package com.jaeheonshim.jvisibility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentTest {
    @Test
    public void testIncidentEdges() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, -1),
                new Point(0, -1)
        };

        Edge[] edges = {
                new Edge(points[0], points[1]),
                new Edge(points[1], points[2]),
                new Edge(points[2], points[3]),
                new Edge(points[3], points[0])
        };

        Polygon p = new Polygon(points);

        Environment environment = new Environment();
        environment.addPolygon(p);

        assertTrue(environment.getIncidentEdges(points[0]).contains(edges[0]) || environment.getIncidentEdges(points[0]).contains(edges[3]));
        assertTrue(environment.getIncidentEdges(points[1]).contains(edges[0]) || environment.getIncidentEdges(points[0]).contains(edges[1]));
        assertTrue(environment.getIncidentEdges(points[2]).contains(edges[1]) || environment.getIncidentEdges(points[0]).contains(edges[2]));
        assertTrue(environment.getIncidentEdges(points[3]).contains(edges[3]) || environment.getIncidentEdges(points[0]).contains(edges[0]));
    }
}
