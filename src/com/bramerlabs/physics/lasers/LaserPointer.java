package com.bramerlabs.physics.lasers;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class LaserPointer {

    public Vector2f position;
    public Vector2f direction;
    public int maxDistance = (int) Math.sqrt(Lasers.width * Lasers.width + Lasers.height * Lasers.height) + 100;
    public float stepSize = 0.1f;

    public LaserPointer(Vector2f position, Vector2f direction) {
        this.position = position;
        this.direction = direction;
    }

    public void paint(Graphics g, ArrayList<Mirror> mirrors, Vector2f startPosition, Vector2f startDirection) {
        Mirror intersectMirror = null;
        g.setColor(Color.RED);
        g.drawRect((int) position.x - 5, (int) position.y - 5, 10, 10);
        float totalDistance = 0;
        Vector2f intersect = null;
        while (totalDistance < maxDistance) {
            totalDistance += stepSize;
            Vector2f stepVector = Vector2f.add(startPosition, Vector2f.normalize(startDirection, totalDistance));
            for (Mirror mirror : mirrors) {
                if (intersect(mirror.p1, mirror.p2, startPosition, stepVector)) {
                    intersect = calculateInterceptionPoint(mirror.p1, mirror.p2, startPosition, stepVector);
                    intersectMirror = mirror;
//                    g.drawRect((int) intersect.x - 5, (int) intersect.y - 5, 10, 10);
                    break;
                }
            }
            if (intersect != null) {
                break;
            }
        }
        if (intersect != null) {
            Vector2f d = Vector2f.subtract(intersect, startPosition);
            float a1 = Vector2f.dot(d, Vector2f.normalize(intersectMirror.normal)) * 2;
            Vector2f r = Vector2f.subtract(d, Vector2f.scale(Vector2f.normalize(intersectMirror.normal), a1));

            Vector2f endPoint = Vector2f.add(startPosition, Vector2f.normalize(startDirection, totalDistance));
            g.drawLine((int) startPosition.x, (int) startPosition.y, (int) endPoint.x, (int) endPoint.y);
//            g.drawRect((int) startPosition.x - 5, (int) startPosition.y - 5, 10, 10);
//            g.drawRect((int) intersect.x - 5, (int) intersect.y - 5, 10, 10);

            Vector2f newStartDirection = Vector2f.normalize(r);
            paint(g, mirrors, Vector2f.add(intersect, Vector2f.normalize(newStartDirection, stepSize)), newStartDirection);
        } else {
            Vector2f endPoint = Vector2f.add(startPosition, Vector2f.normalize(startDirection, totalDistance));
            g.drawLine((int) startPosition.x, (int) startPosition.y, (int) endPoint.x, (int) endPoint.y);
//            g.drawRect((int) startPosition.x - 5, (int) startPosition.y - 5, 10, 10);
//            g.drawRect((int) endPoint.x - 5, (int) endPoint.y - 5, 10, 10);
        }
    }

    public static Vector2f calculateInterceptionPoint(Vector2f s1, Vector2f s2, Vector2f d1, Vector2f d2) {
        double a1 = s2.y - s1.y;
        double b1 = s1.x - s2.x;
        double c1 = a1 * s1.x + b1 * s1.y;

        double a2 = d2.y - d1.y;
        double b2 = d1.x - d2.x;
        double c2 = a2 * d1.x + b2 * d1.y;

        double delta = a1 * b2 - a2 * b1;
        return new Vector2f((float) ((b2 * c1 - b1 * c2) / delta), (float) ((a1 * c2 - a2 * c1) / delta));
    }

    public static int orientation(Vector2f p, Vector2f q, Vector2f r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0.0)
            return 0;
        return (val > 0) ? 1 : 2;
    }

    public static boolean intersect(Vector2f p1, Vector2f q1, Vector2f p2, Vector2f q2) {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        return o1 != o2 && o3 != o4;
    }

}