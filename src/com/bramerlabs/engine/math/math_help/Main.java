package com.bramerlabs.engine.math.math_help;

import com.bramerlabs.engine.math.vector.Vector2f;

public class Main {

    public static void main(String[] args) {
        Vector2f p1 = new Vector2f(8, 8);
        Vector2f p2 = new Vector2f(1, 4);
        Vector2f c1 = new Vector2f(2, 2);
        Vector2f c2 = new Vector2f(8, 10);

        System.out.println(calculateInterceptionPoint(p1, p2, c1, c2));

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

    public static Vector2f reflectVector2f(Vector2f v1, Vector2f v2) {
        return null;
    }

}
