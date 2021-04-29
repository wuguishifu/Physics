package com.bramerlabs.physics.collisions;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Ball {

    public Vector2f position, velocity;
    public float acceleration = 0.1f, velocityScaleFactor = 0.1f;
    public int radius = 10;

    public Ball(Vector2f position, Vector2f velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void update(ArrayList<CollidableObject> objects) {
        Vector2f newPosition = Vector2f.add(position, Vector2f.scale(velocity, velocityScaleFactor));
        if (newPosition.x < 0) {
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(new Vector2f(1, 0), 2 * Vector2f.dot(new Vector2f(1, 0), velocity)));
        }
        if (newPosition.x > Collisions.WIDTH) {
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(new Vector2f(-1, 0), 2 * Vector2f.dot(new Vector2f(-1, 0), velocity)));
        }
        if (newPosition.y < 0) {
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(new Vector2f(0, 1), 2 * Vector2f.dot(new Vector2f(0, 1), velocity)));
        }
        if (newPosition.y > Collisions.HEIGHT) {
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(new Vector2f(0, -1), 2 * Vector2f.dot(new Vector2f(0, -1), velocity)));
        }
//        this.velocity = Vector2f.normalize(velocity, Vector2f.length(velocity) - 10);

        boolean garb = false;
        for (CollidableObject object : objects) {
            if (updateObject(object, newPosition)) {
                garb = true;
                break;
            }
        }

        if (!garb) {
            this.position = newPosition;
        }
    }

    public boolean updateObject(CollidableObject object, Vector2f newPosition) {
        // defined in quadrant order
        Vector2f c1 = new Vector2f(object.x + object.w, object.y);
        Vector2f c2 = new Vector2f(object.x, object.y);
        Vector2f c3 = new Vector2f(object.x, object.y + object.h);
        Vector2f c4 = new Vector2f(object.x + object.w, object.y + object.h);


        if (intersect(newPosition, position, c1, c2)) {
            Vector2f normal = new Vector2f(0, -1);
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(normal, 2 * Vector2f.dot(normal, velocity)));
            return true;
        }
        if (intersect(newPosition, position, c2, c3)) {
            Vector2f normal = new Vector2f(-1, 0);
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(normal, 2 * Vector2f.dot(normal, velocity)));
            return true;
        }
        if (intersect(newPosition, position, c3, c4)) {
            Vector2f normal = new Vector2f(0, 1);
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(normal, 2 * Vector2f.dot(normal, velocity)));
            return true;
        }
        if (intersect(newPosition, position, c4, c1)) {
            Vector2f normal = new Vector2f(1, 0);
            this.velocity = Vector2f.subtract(velocity, Vector2f.scale(normal, 2 * Vector2f.dot(normal, velocity)));
            return true;
        }
        return false;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(123, 188, 186));
        g.fillOval((int) position.x - radius, (int) position.y - radius, 2 * radius, 2 * radius);
        g.setColor(Color.BLACK);
        g.drawOval((int) position.x - radius, (int) position.y - radius, 2 * radius, 2 * radius);
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
