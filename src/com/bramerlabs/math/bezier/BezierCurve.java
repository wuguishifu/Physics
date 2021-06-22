package com.bramerlabs.math.bezier;

import java.awt.*;

public abstract class BezierCurve {
    abstract Point bezierFunction(float t, Point[] p);
    abstract void selectPoint(int x, int y);
    abstract void deselectPoint();
    abstract void movePoint(int x, int y);
    abstract void paint(Graphics g);
}
