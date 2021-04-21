package com.bramerlabs.math.fractals;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Sierpinski {

    private static final Vector2f CENTER = new Vector2f(Fractals.WIDTH/2f, Fractals.HEIGHT/2f);

    private static final int LENGTH = 300, DEPTH = 8;

    public static void drawSierpinski(Graphics g) {
        Vector2f v1 = Vector2f.add(CENTER, Vector2f.normalize(new Vector2f(0, 1), LENGTH));
        Vector2f v2 = Vector2f.add(CENTER, Vector2f.normalize(new Vector2f((float) Math.cos(Math.toRadians(-30)), (float) Math.sin(Math.toRadians(-30))), LENGTH));
        Vector2f v3 = Vector2f.add(CENTER, Vector2f.normalize(new Vector2f((float) Math.cos(Math.toRadians(210)), (float) Math.sin(Math.toRadians(210))), LENGTH));
        drawTriangle((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y, 0, g);
    }

    private static void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int depth, Graphics g) {
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x3, y3, x1, y1);
        if (depth < DEPTH) {
            drawTriangle(x1, y1, (x1 + x2) / 2, (y1 + y2) / 2, (x1 + x3) / 2, (y1 + y3) / 2, depth + 1, g);
            drawTriangle((x1 + x2) / 2, (y1 + y2) / 2, x2, y2, (x2 + x3) / 2, (y2 + y3) / 2, depth + 1, g);
            drawTriangle((x1 + x3) / 2, (y1 + y3) / 2, x3, y3, (x2 + x3) / 2, (y2 + y3) / 2, depth + 1, g);
        }
    }
}