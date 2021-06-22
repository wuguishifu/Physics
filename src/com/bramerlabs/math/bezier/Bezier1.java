package com.bramerlabs.math.bezier;

import java.awt.*;

public class Bezier1 extends BezierCurve {

    private int numSegments = 500;
    float separation = 1f/numSegments;

    private Point[] points;
    Point selectedPoint = null;

    public Bezier1() {
        int width = Bezier.windowSize.width;
        int height = Bezier.windowSize.height;
        points = new Point[] {
                new Point(width/3f, height/3f, new Color(123, 188, 186)),
                new Point(2*width/3f, height/3f, new Color(123, 188, 186)),
        };
    }

    @Override
    Point bezierFunction(float t, Point[] p) {
        float x = (1-t)*p[0].x + t*p[1].x;
        float y = (1-t)*p[0].y + t*p[1].y;
        return new Point(x, y);
    }

    @Override
    void selectPoint(int x, int y) {
        for (Point p : points) {
            if (p.inBounds(x, y, Point.selectionRadius)) {
                selectedPoint = p;
                break;
            }
        }
    }

    @Override
    void deselectPoint() {
        selectedPoint = null;
    }

    @Override
    void movePoint(int x, int y) {
        if (selectedPoint != null) {
            selectedPoint.moveTo(x, y);
        }
    }

    @Override
    void paint(Graphics g) {
        // draw the dashed lines
        Graphics2D g2d = (Graphics2D) g.create();
//        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{8}, 0);
//        g2d.setStroke(dashed);
//        g2d.setColor(new Color(184, 184, 184));
//        g2d.drawLine((int) points[0].x, (int) points[0].y, (int) points[1].x, (int) points[1].y);
//        g2d.dispose();

        // draw the bezier curve
        g.setColor(new Color(69, 69, 69));
        g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3));
        Point[] bezierPoints = new Point[numSegments];
        for (int i = 0; i < numSegments; i++) {
            bezierPoints[i] = bezierFunction(separation * i, points);
        }
        for (int i = 0; i < numSegments - 1; i++) {
            g.drawLine((int) bezierPoints[i].x, (int) bezierPoints[i].y, (int) bezierPoints[i + 1].x, (int) bezierPoints[i + 1].y);
        }
        g2d.dispose();

        // draw the points
        for (Point p : points) {
            p.paint(g);
        }
    }
}
