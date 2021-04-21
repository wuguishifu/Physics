package com.bramerlabs.math.fractals;

import java.awt.*;

public class Tree {

    private static final int DEPTH = 6, INITIAL_LENGTH = 150;
    private static final float DT = (float) Math.toRadians(30), IT = (float) Math.toRadians(-90);

    public static void drawTree(Graphics g) {
        drawBranch(400, 500, INITIAL_LENGTH, IT, 0, g);
    }

    private static void drawBranch(int x1, int y1, int length, float theta, int depth, Graphics g) {

        int x2 = (int) (Math.cos(theta) * length) + x1;
        int y2 = (int) (Math.sin(theta) * length) + y1;
        g.drawLine(x1, y1, x2, y2);
        if (depth < DEPTH) {
            drawBranch(x2, y2, 2*length/3, theta + DT, depth + 1, g);
            drawBranch(x2, y2, 2*length/3, theta - DT, depth + 1, g);
        }
    }

}
