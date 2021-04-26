package com.bramerlabs.physics.prisms;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;

public class Prism {

    public Vector2f v1, v2, v3;
    public Vector2f n1, n2, n3;
    public float refractionIndex;

    public static float
        vacuum = 1.00f,
        air = 1.0003f,
        water = 1.33f,
        oil = 1.46f,
        glass = 1.50f,
        diamond = 2.41f;

    public Prism(Vector2f v1, Vector2f v2, Vector2f v3, float refractionIndex) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.refractionIndex = refractionIndex;
        this.recalculateNormals();
    }

    public Prism(Vector2f center, float radius, float refractionIndex) {
        this.v1 = Vector2f.add(center, Vector2f.normalize(new Vector2f(0, -1), radius));
        this.v2 = Vector2f.add(center, Vector2f.normalize(new Vector2f((float) Math.cos(Math.toRadians(30)), (float) Math.sin(Math.toRadians(30))), radius));
        this.v3 = Vector2f.add(center, Vector2f.normalize(new Vector2f((float) Math.cos(Math.toRadians(-210)), (float) Math.sin(Math.toRadians(-210))), radius));
        this.refractionIndex = refractionIndex;
        this.recalculateNormals();
    }

    public static boolean insidePrism(Prism prism, Vector2f p) {
        Vector2f v1 = Vector2f.subtract(prism.v2, prism.v1);
        Vector2f v2 = Vector2f.subtract(prism.v3, prism.v1);
        float a = (Vector2f.det(p, v2) - Vector2f.det(prism.v1, v2)) / (Vector2f.det(v1, v2));
        float b = - (Vector2f.det(p, v1) - Vector2f.det(prism.v1, v1)) / (Vector2f.det(v1, v2));
        return a > 0 && b > 0 && a + b < 1;
    }

    public void recalculateNormals() {
//        float t = (float) Math.toRadians(90);
//        n1 = Vector2f.subtract(v1, v2);
//        n1 = new Vector2f((float) (n1.x * Math.cos(t) - n1.y * Math.sin(t)), (float) (n1.x * Math.sin(t) + n1.y * Math.cos(t)));
//        n2 = Vector2f.subtract(v3, v2);
//        n2 = new Vector2f((float) (n2.x * Math.cos(t) - n2.y * Math.sin(t)), (float) (n2.x * Math.sin(t) + n2.y * Math.cos(t)));
//        n3 = Vector2f.subtract(v1, v3);
//        n3 = new Vector2f((float) (n3.x * Math.cos(t) - n3.y * Math.sin(t)), (float) (n3.x * Math.sin(t) + n3.y * Math.cos(t)));

        n1 = new Vector2f((float) Math.cos(Math.toRadians(-30)), (float) Math.sin(Math.toRadians(-30)));
        n2 = new Vector2f((float) Math.cos(Math.toRadians( 90)), (float) Math.sin(Math.toRadians( 90)));
        n3 = new Vector2f((float) Math.cos(Math.toRadians(210)), (float) Math.sin(Math.toRadians(210)));

    }

    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
        g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
        g.drawLine((int) v3.x, (int) v3.y, (int) v1.x, (int) v1.y);

//        // code for drawing normals
//        Vector2f v12 = Vector2f.center(v1, v2);
//        Vector2f v23 = Vector2f.center(v2, v3);
//        Vector2f v31 = Vector2f.center(v3, v1);
//
//        Vector2f _n1 = Vector2f.add(v12, Vector2f.normalize(n1, 100));
//        Vector2f _n2 = Vector2f.add(v23, Vector2f.normalize(n2, 100));
//        Vector2f _n3 = Vector2f.add(v31, Vector2f.normalize(n3, 100));
//
//        g.setColor(Color.LIGHT_GRAY);
//        g.drawLine((int) v12.x, (int) v12.y, (int) _n1.x, (int) _n1.y);
//        g.drawLine((int) v23.x, (int) v23.y, (int) _n2.x, (int) _n2.y);
//        g.drawLine((int) v31.x, (int) v31.y, (int) _n3.x, (int) _n3.y);
//
//        // code for drawing vertices
//        g.setColor(new Color(255, 0, 0));
//        g.drawOval((int) v1.x - 5, (int) v1.y - 5, 10, 10);
//        g.setColor(new Color(0, 255, 0));
//        g.drawOval((int) v2.x - 5, (int) v2.y - 5, 10, 10);
//        g.setColor(new Color(0, 0, 255));
//        g.drawOval((int) v3.x - 5, (int) v3.y - 5, 10, 10);
//
//        // code for painting the inside of the triangle (very slow)
//        for (int i = 0; i < Main.width; i++) {
//            for (int j = 0; j < Main.height; j++) {
//                g.setColor(Prism.insidePrism(this, new Vector2f(i, j)) ? Color.LIGHT_GRAY : UIManager.getColor("Panel.background"));
//                g.drawLine(i, j, i, j);
//            }
//        }
    }
}