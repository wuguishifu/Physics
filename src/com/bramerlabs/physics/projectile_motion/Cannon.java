package com.bramerlabs.physics.projectile_motion;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Cannon {

    private final static Color color = new Color(206, 83, 144);

    private final Vector2f p1;
    private Vector2f p2;

    public final float cannonLength = Vector2f.length(new Vector2f(100, 100));

    public final int radius = 5;

    public Cannon(Vector2f p1, Vector2f p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public float getAngle() {
        float y = p1.y - p2.y;
        float x = p2.x - p1.x;
        return (float) Math.toDegrees(Math.atan(y / x));
    }

    public boolean inHeadBounds(int x, int y, int displayRadius) {
        return x >= p2.x - displayRadius && x <= p2.x + displayRadius && y >= p2.y - displayRadius && y <= p2.y + displayRadius;
    }

    public void rotate(int x, int y) {
        if (x <= p1.x || y >= p1.y) {
            return;
        }
        Vector2f newDirection = new Vector2f(x, y);
        Vector2f normal = Vector2f.normalize(Vector2f.subtract(newDirection, p1), cannonLength);
        p2 = Vector2f.add(p1, normal);
    }

    public void paint(Graphics g, int mouseX, int mouseY) {
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);

        if (inHeadBounds(mouseX, mouseY, radius * 4)) {
            g2d.fillOval((int) p2.x - radius, (int) p2.y - radius, 2 * radius, 2 * radius);
        }
        g2d.dispose();
//        System.out.println(getAngle());
    }

    public Vector2f getHead() {
        return this.p2;
    }

    public Vector2f getTail() {
        return this.p1;
    }

}
