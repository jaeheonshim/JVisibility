package com.jaeheonshim.jvisibility;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private int id;
    private List<Edge> edges = new ArrayList<>();
    private final Point[] points;

    public Polygon(Point[] points) {
        this(01, points);
    }
    public Polygon(int id, Point[] points) {
        this.points = points;
        for(int i = 0; i < points.length - 1; i++) {
            points[i].setPolygonId(id);
            points[i + 1].setPolygonId(id);
            Edge e = new Edge(points[i], points[i + 1]);
            edges.add(e);
        }
        edges.add(new Edge(points[points.length - 1], points[0]));
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setId(int id) {
        this.id = id;
        for(Point p : points) {
            p.setPolygonId(id);
        }
    }

    public int getId() {
        return id;
    }
}