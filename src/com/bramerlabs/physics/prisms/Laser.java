package com.bramerlabs.physics.prisms;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Laser {

    Vector2f position;
    Vector2f direction;
    public int pointRadius = 10;
    public int dragRadius = 100;
    public int maxDepth = 100;

    public int maxDistance = (int) Math.sqrt(Main.width * Main.width + Main.height * Main.height);
    public float stepSize = 0.1f;

    public Laser(Vector2f position, Vector2f direction) {
        this.position = position;
        this.direction = direction;
    }

    public boolean onRadius(float x, float y) {
        Vector2f negativeDirection = Vector2f.subtract(Vector2f.zero, direction);
        Vector2f negativePosition = Vector2f.add(position, Vector2f.normalize(negativeDirection, dragRadius));
        return x <= negativePosition.x + pointRadius && x >= negativePosition.x - pointRadius && y <= negativePosition.y + pointRadius && y >= negativePosition.y - pointRadius;
    }

    public boolean onPoint(float x, float y) {
        return x <= position.x + pointRadius && x >= position.x - pointRadius && y <= position.y + pointRadius && y >= position.y - pointRadius;
    }

    public void translateLaser(float x, float y) {
        this.position = new Vector2f(x, y);
    }

    public void rotateLaser(float x, float y) {
        direction = Vector2f.normalize(Vector2f.subtract(position, new Vector2f(x, y)));
    }

    public void paint(Graphics g, Prism prism) {
        g.setColor(Color.DARK_GRAY);
        Vector2f negativeDirection = Vector2f.subtract(Vector2f.zero, direction);
        Vector2f negativePosition = Vector2f.add(position, Vector2f.normalize(negativeDirection, dragRadius));
        g.drawLine((int) position.x, (int) position.y, (int) negativePosition.x, (int) negativePosition.y);
        g.fillOval((int) negativePosition.x - 5, (int) negativePosition.y - 5, 10, 10);
        g.setColor(Color.RED);
        g.fillOval((int) position.x - 5, (int) position.y - 5, 10, 10);
        paintLaser(g, position, direction, prism, 0);
    }

    private void paintLaser(Graphics g, Vector2f origin, Vector2f direction, Prism prism, int depth) {
        float totalDistance = 0;
        int intersection = -1;
        Vector2f intersectionPoint = null;
        Vector2f normal = null;
        while (totalDistance < maxDistance) {
            totalDistance += stepSize;
            Vector2f stepVector = Vector2f.add(origin, Vector2f.normalize(direction, totalDistance));
            if (intersect(prism.v1, prism.v2, origin, stepVector)) {
                intersection = 1;
                intersectionPoint = calculateInterceptionPoint(prism.v1, prism.v2, origin, stepVector);
                normal = prism.n1;
                break;
            } else if (intersect(prism.v2, prism.v3, origin, stepVector)) {
                intersection = 2;
                intersectionPoint = calculateInterceptionPoint(prism.v2, prism.v3, origin, stepVector);
                normal = prism.n2;
                break;
            } else if (intersect(prism.v3, prism.v1, origin, stepVector)) {
                intersection = 3;
                intersectionPoint = calculateInterceptionPoint(prism.v3, prism.v1, origin, stepVector);
                normal = prism.n3;
                break;
            }
        }
        if (intersection == -1 || depth > maxDepth) {
            Vector2f endPoint = Vector2f.add(origin, Vector2f.normalize(direction, totalDistance));
            g.drawLine((int) origin.x, (int) origin.y, (int) endPoint.x, (int) endPoint.y);
            System.out.println();
        } else {
            if (Vector2f.dot(direction, normal) > 0) {
                normal = Vector2f.subtract(Vector2f.zero, normal);
            }
//            g.setColor(Prism.insidePrism(prism, origin) ? Color.BLUE : Color.RED); // test function
            g.drawLine((int) origin.x, (int) origin.y, (int) intersectionPoint.x, (int) intersectionPoint.y);
            float dot = Vector2f.dot(normal, direction);
            float eta = Prism.insidePrism(prism, origin) ? prism.refractionIndex : 1 / prism.refractionIndex;
            float k = 1.0f - eta * eta * (1.0f - dot * dot);
            if (k >= 0) {
                Vector2f newDirection = Vector2f.subtract(Vector2f.scale(direction, eta), Vector2f.scale(normal, (float) (eta * dot + Math.sqrt(k))));
                paintLaser(g, Vector2f.add(intersectionPoint, Vector2f.normalize(newDirection, stepSize)), newDirection, prism, depth + 1);
            }
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
