package com.bramerlabs.math.bezier;

import java.awt.*;

public class BezierCurve {

    private final int numSegments = 500;
    float separation = 1f/numSegments;

    private final Point[] points;
    Point selectedPoint = null;

    public BezierCurve(int order) {
        int width = Bezier.windowSize.width;
        int height = Bezier.windowSize.height;
        switch (order) {
            case 1:
                points = new Point[] {
                        new Point(width/3f, height/3f, new Color(123, 188, 186)),
                        new Point(2*width/3f, height/3f, new Color(123, 188, 186)),
                };
                break;
            case 2:
                points = new Point[] {
                        new Point(width/3f, height/3f, new Color(123, 188, 186)),
                        new Point(width/2f, 2*height/3f, new Color(255, 158, 178)),
                        new Point(2*width/3f, height/3f, new Color(123, 188, 186)),
                };
                break;
            case 3:
                points = new Point[] {
                        new Point(width/3f, height/3f, new Color(123, 188, 186)),
                        new Point(width/3f, 2*height/3f, new Color(255, 158, 178)),
                        new Point(2*width/3f, 2*height/3f, new Color(255, 158, 178)),
                        new Point(2*width/3f, height/3f, new Color(123, 188, 186)),
                };
                break;
            default:
                points = new Point[order + 1];
                float diff = width/(3f * order + 3);
                for (int i = 0; i < points.length; i++) {
                    if (i == 0) {
                        points[i] = new Point(width/3f + diff * i, height/2f, new Color(123, 188, 186));
                    } else if (i == points.length - 1) {
                        points[i] = new Point(width/3f + diff * i, height/2f, new Color(123, 188, 186));
                    } else {
                        points[i] = new Point(width/3f + diff * i, height/2f, new Color(255, 158, 178));
                    }
                }
        }
    }

    private Point bezierFunction(float t, Point[] p) {
        return BezierFunction.bezierFunction(t, p);
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
        selectedPoint = null;
    }

    public void movePoint(int x, int y) {
        if (selectedPoint != null) {
            selectedPoint.moveTo(x, y);
        }
    }

    public void paint(Graphics g) {
        // draw dashed lines
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{8}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(new Color(184, 184, 184));
        for (int i = 0; i < points.length - 1; i++) {
            g2d.drawLine((int) points[i].x, (int) points[i].y, (int) points[i + 1].x, (int) points[i + 1].y);
        }
        g2d.dispose();

        // draw the bezier curve
        g.setColor(new Color(69, 69, 69));
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
