package com.bramerlabs.math.bezier;

import java.util.Arrays;

public class BezierFunction {

    public static Point bezierFunction(float t, Point[] p) {
        float x = bezierFunctionX(t, p);
        float y = bezierFunctionY(t, p);
        return new Point(x, y);
    }

    private static float bezierFunctionX(float t, Point[] p) {
        if (p.length == 1) {
            return p[0].x;
        }
        return t * bezierFunctionX(t, Arrays.copyOfRange(p, 1, p.length)) + (1-t) *
                bezierFunctionX(t, Arrays.copyOf(p, p.length-1));
    }

    private static float bezierFunctionY(float t, Point[] p) {
        if (p.length == 1) {
            return p[0].y;
        }
        return t * bezierFunctionY(t, Arrays.copyOfRange(p, 1, p.length)) + (1-t) *
                bezierFunctionY(t, Arrays.copyOf(p, p.length-1));
    }

}
