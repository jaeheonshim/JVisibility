package com.jaeheonshim.jvisibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    private Map<Integer, Polygon> polygonMap = new HashMap<>();
    private Map<Point, List<Edge>> incidentEdges = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();

    private int polygonIdCounter = 0;

    public void addPolygon(Polygon p) {
        p.setId(polygonIdCounter);
        polygonMap.put(polygonIdCounter++, p);
        for(Edge edge : p.getEdges()) {
            incidentEdges.computeIfAbsent(edge.p1, k -> new ArrayList<>());
            incidentEdges.computeIfAbsent(edge.p2, k -> new ArrayList<>());

            incidentEdges.get(edge.p1).add(edge);
            incidentEdges.get(edge.p2).add(edge);
            edges.add(edge);
        }
    }

    public List<Edge> getIncidentEdges(Point p) {
        return incidentEdges.get(p);
    }

    public List<Point> getAdjacentPoints(Point p) {
        List<Edge> incidentEdges = getIncidentEdges(p);
        if(incidentEdges == null) return new ArrayList<>();

        List<Point> adjacent = new ArrayList<>();

        Edge e1 = incidentEdges.get(0);
        Edge e2 = incidentEdges.get(1);

        if(e1.p1.equals(p)) {
            adjacent.add(e1.p2);
        } else {
            adjacent.add(e1.p1);
        }

        if(e2.p1.equals(p)) {
            adjacent.add(e2.p2);
        } else {
            adjacent.add(e2.p1);
        }

        return adjacent;
    }

    public Polygon getPolygonById(int polygonId) {
        return polygonMap.get(polygonId);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Point> getPoints() {
        return new ArrayList<>(incidentEdges.keySet());
    }
}
