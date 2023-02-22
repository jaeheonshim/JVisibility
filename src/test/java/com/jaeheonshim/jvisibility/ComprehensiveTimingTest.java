package com.jaeheonshim.jvisibility;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ComprehensiveTimingTest {
    public static int n = 10000000;

    @Test
    public void test() throws IOException {
        List<Polygon> polygonList = new ArrayList<>();

        BufferedReader fieldDataStream = new BufferedReader(
                new InputStreamReader(ComprehensiveTimingTest.class.getResourceAsStream("/2023-field.json")));
        StringJoiner jsonBuilder = new StringJoiner("\n");
        String line;
        while ((line = fieldDataStream.readLine()) != null) {
            jsonBuilder.add(line);
        }

        JSONObject fieldData = new JSONObject(jsonBuilder.toString());
        JSONArray fieldObstacles = fieldData.getJSONArray("obstacles");
        for (int i = 0; i < fieldObstacles.length(); ++i) {
            JSONObject obstacle = fieldObstacles.getJSONObject(i);
            JSONArray obstaclePoints = obstacle.getJSONArray("points");
            double buffer = obstacle.getDouble("buffer");

            double[] polygonPoints = new double[obstaclePoints.length()];
            double xCenter = 0;
            double yCenter = 0;

            for (int j = 0; j < obstaclePoints.length(); j += 2) {
                xCenter += obstaclePoints.getDouble(j);
                yCenter += obstaclePoints.getDouble(j + 1);
            }

            xCenter /= obstaclePoints.length() / 2;
            yCenter /= obstaclePoints.length() / 2;

            for (int j = 0; j < obstaclePoints.length(); j += 2) {
                double x = obstaclePoints.getDouble(j);
                double y = obstaclePoints.getDouble(j + 1);

                polygonPoints[j] = x + buffer * Math.signum(x - xCenter);
                polygonPoints[j + 1] = y + buffer * Math.signum(y - yCenter);
            }

            Polygon polygon = new Polygon(polygonPoints);
            polygonList.add(polygon);
        }

        Environment environment = new Environment(polygonList);

        long total = 0;

        for(int i = 0; i < n; ++i) {
            long startTime = System.currentTimeMillis();
            VisibilityGraph graph = VisibilityGraphGenerator.generateGraph(null, null, environment);
            long delta = System.currentTimeMillis() - startTime;

            total += delta;
        }

        System.out.printf("Ran %d iterations with avg %.8f ms running time %n", n, total / (double) n);
    }

    public static Point generateValidPoint(Environment env) {
        do {
            double xMax = 16.54175;
            double yMax = 8.0137;

            double x = Math.random() * xMax;
            double y = Math.random() * yMax;

            Point p = new Point(x, y);

            for(Polygon poly : env.getPolygons()) {
                if(GeometryMath.isPointInPolygon(p, poly)) continue;
            }

            return p;
        } while(true);
    }
}
