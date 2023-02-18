package com.jaeheonshim.jvisibility;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VisibilityGraphGeneratorTest {
    @Test
    public void testVisibleVertices() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, -1),
                new Point(0, -1)
        };

        Polygon p = new Polygon(points);

        Environment environment = new Environment();
        environment.addPolygon(p);

        List<Point> visible = VisibilityGraphGenerator.visibleVertices(new Point(-1, -0.5), environment);
        assertTrue(visible.contains(points[0]));
        assertTrue(visible.contains(points[3]));
        assertEquals(2, visible.size());
    }

    public void comprehensive() {
        Point[] polygon1Points = {
                new Point(1, 1),
                new Point(2, 1),
                new Point(1.5, 3)
        };

        Point[] polygon2Points = {
                new Point(2.2, 2),
                new Point(3, 4),
                new Point(2, 3)
        };

        Point[] polygon3Points = {
                new Point(2, 1.75),
                new Point(4, 2),
                new Point(3, 1)
        };

        Polygon p1 = new Polygon(polygon1Points);
        Polygon p2 = new Polygon(polygon2Points);
        Polygon p3 = new Polygon(polygon3Points);

        Environment env = new Environment();
        env.addPolygon(p1);
        env.addPolygon(p2);
        env.addPolygon(p3);

        assertEquals(0, p1.getId());
        assertEquals(1, p2.getId());
        assertEquals(2, p3.getId());

        List<Point> visible1 = VisibilityGraphGenerator.visibleVertices(new Point(1.9, 2.3), env);
        assertTrue(visible1.contains(polygon1Points[2]));
        assertTrue(visible1.contains(polygon1Points[1]));
        assertTrue(visible1.contains(polygon2Points[2]));
        assertTrue(visible1.contains(polygon2Points[0]));
        assertTrue(visible1.contains(polygon3Points[0]));
        assertEquals(5, visible1.size());
    }
}