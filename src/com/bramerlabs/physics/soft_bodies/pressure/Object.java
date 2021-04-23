package com.bramerlabs.physics.soft_bodies.pressure;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Object {

    // a closed loop of vertices
    // all points will be defined in the range x,y in [0, inf)
    // force closed loop by ensuring the last vertex is the same as the first vertex pls
    public Vector2f[] vertices;

    public Object(Vector2f[] vertices) {
        this.vertices = vertices;
    }

    public Vector2f[] collides(Vector2f position) {
        // general ray cast method:
        // 1) iterate over each line
        // 2) count amount of passes
        // 3) determine if inside or outside of shape

        // standard position always outside of the shape
        Vector2f standard = new Vector2f(position.x, -100);
        // iterate over each line
        Vector2f c1 = new Vector2f(0, 0);
        Vector2f c2 = new Vector2f(0, 0);
        float closestDistance = Float.MAX_VALUE;
        int count = 0;
        for (int i = 0; i < vertices.length - 1; i++) {
            float x1 = vertices[i].x;
            float y1 = vertices[i].y;
            float x2 = vertices[i + 1].x;
            float y2 = vertices[i + 1].y;

            if (x1 > x2) {
                x1 = x1 + x2;
                x2 = x1 - x2;
                x1 = x1 - x2;
                y1 = y1 + y2;
                y2 = y1 - y2;
                y1 = y1 - y2;
            }

            if (x1 <= position.x && position.x <= x2) {
                float yValue = equation1(x1, x2, y1, y2, position.x);
                if (standard.y <= yValue && yValue <= position.y) {
                    count ++;
                    float distance = equation2(x1, x2, y1, y2, position);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        c1 = vertices[i];
                        c2 = vertices[i + 1];
                    }
                }
            }
        }
        if (count % 2 == 0) {
            return null;
        } else {
            return new Vector2f[]{c1, c2};
        }
    }

    // simple dynamic implementation of y = mx + b
    public float equation1(float x1, float x2, float y1, float y2, float val) {
        return ((y2 - y1) / (x2 - x1)) * (val - x1) + y1;
    }

    // distance to a line equation
    public float equation2(float x1, float x2, float y1, float y2, Vector2f position) {
        float a = (y2 - y1) / (x2 - x1);
        float b = 1;
        float c = a * x1 + y1;
        return (float) (Math.abs(a * position.x + b * position.y + c) / Math.sqrt(a * a + b * b));
    }

    public void paint(Graphics g) {
        for (int i = 0; i < vertices.length - 1; i++) {
            g.setColor(Color.BLACK);
            g.drawLine((int) vertices[i].x, (int) vertices[i].y, (int) vertices[i + 1].x, (int) vertices[i + 1].y);
        }
    }

}
