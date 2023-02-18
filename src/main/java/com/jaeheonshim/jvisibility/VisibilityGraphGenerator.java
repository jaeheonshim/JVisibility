package com.jaeheonshim.jvisibility;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VisibilityGraphGenerator {
    private static Comparator<Point> getAngleComparator(Point p) {
        return new Comparator<Point>() {
            @Override
            public int compare(Point v1, Point v2) {
                double theta1 = GeometryMath.negativePiTo2Pi(Math.atan2(v1.y - p.y, v1.x - p.x));
                double theta2 = GeometryMath.negativePiTo2Pi(Math.atan2(v2.y - p.y, v2.x - p.x));

                if (theta1 < theta2)
                    return -1;
                else if (theta1 > theta2)
                    return 1;
                else {
                    if (Math.pow(p.x - v1.x, 2) + Math.pow(p.y - v1.y, 2) < Math.pow(p.x - v2.x, 2)
                            + Math.pow(p.y - v2.y, 2))
                        return -1;
                    else
                        return 1;
                }
            }
        };
    }

    public static List<Point> visibleVertices(Point point, Environment environment) {
        List<Edge> edges = environment.getEdges();
        List<Point> points = environment.getPoints();

        points.sort(getAngleComparator(point));

        OpenEdges openEdges = new BSTOpenEdges();

        Point pointInf = new Point(Double.MAX_VALUE, point.y);
        for (Edge edge : edges) {
            if (edge.containsEndpoint(point))
                continue;
            if (GeometryMath.edgeIntersect(point, pointInf, edge)) {
                if (GeometryMath.onSegment(point, edge.p1, pointInf))
                    continue;
                if (GeometryMath.onSegment(point, edge.p2, pointInf))
                    continue;
                openEdges.insert(point, pointInf, edge);
            }
        }

        List<Point> visible = new ArrayList<>();

        // begin the clockwise scan
        Point prev = null;
        boolean prevVisible = false;

        for (Point p : points) {
            if (p.equals(point))
                continue;

            for (Edge edge : environment.getIncidentEdges(p)) {
                if (GeometryMath.ccw(point, p, edge.getOtherVertex(p)) == -1) {
                    openEdges.delete(point, p, edge);
                }
            }

            boolean isVisible = false;
            if (prev == null || GeometryMath.ccw(point, prev, p) != 0 || !GeometryMath.onSegment(point, prev, p)) {
                if (openEdges.size() == 0) {
                    isVisible = true;
                } else if (!GeometryMath.edgeIntersect(point, p, openEdges.smallest())) {
                    isVisible = true;
                }
            } else if (prevVisible) {
                isVisible = false;
            } else {
                isVisible = true;
                for (Edge edge : openEdges) {
                    if (!edge.containsEndpoint(prev) && GeometryMath.edgeIntersect(prev, p, edge)) {
                        isVisible = false;
                        break;
                    }
                }
            }

            if (isVisible && !environment.getAdjacentPoints(point).contains(p)) {
                isVisible = !GeometryMath.edgeInPolygon(point, p, environment);
            }


            if (isVisible && GeometryMath.edgeInPolygon(prev, p, environment)) {
                isVisible = false;
            }

            if (isVisible) {
                visible.add(p);
            }

            for (Edge edge : environment.getIncidentEdges(p)) {
                if (edge.containsEndpoint(point) && GeometryMath.ccw(point, p, edge.getOtherVertex(p)) == 1) {
                    openEdges.insert(point, p, edge);
                }
            }

            prev = p;
            prevVisible = isVisible;
        }

        return visible;
    }
}
