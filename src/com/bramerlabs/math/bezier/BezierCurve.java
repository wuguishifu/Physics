package com.bramerlabs.math.bezier;

import java.awt.*;

public class BezierCurve {

    private int numSegments = 500;
    float separation = 1f/numSegments;

    private Point[] points;
    Point selectedPoint = null;

    private Point bezierFunction(float t, Point[] p) {
        float x = BezierFunction.bezierFunctionX(t, p);
        float y = BezierFunction.bezierFunctionY(t, p);
        return new Point(x, y);
    }

    public void selectPoint(int x, int y) {
        for (Point p : points) {
            if (p.inBounds(x, y, Point.selectionRadius)) {
                selectedPoint = p;
                break;
            }
        }
    }

    public void deselectPoint() {

    }

    public void movePoint(int x, int y) {

    }

    public void paint(Graphics g) {

    }
}
