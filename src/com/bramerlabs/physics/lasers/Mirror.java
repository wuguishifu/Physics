package com.bramerlabs.physics.lasers;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Mirror {

    public Vector2f p1, p2, normal;
    public int pointRadius = 10;

    public Mirror(Vector2f p1, Vector2f p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.recalculateNormal();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        g.drawOval((int) p1.x - 5, (int) p1.y - 5, 10, 10);
        g.drawOval((int) p2.x - 5, (int) p2.y - 5, 10, 10);
    }

    public int onPoint(Vector2f p) {
        if (p.x <= p1.x + pointRadius && p.x >= p1.x - pointRadius && p.y <= p1.y + pointRadius && p.y >= p1.y - pointRadius) {
            return 1;
        } else if (p.x <= p2.x + pointRadius && p.x >= p2.x - pointRadius && p.y <= p2.y + pointRadius && p.y >= p2.y - pointRadius) {
            return 2;
        }
        return 0;
    }

    public void movePoint(int point, Vector2f newPosition) {
        if (point == 1) {
            p1 = newPosition;
        } else if (point == 2) {
            p2 = newPosition;
        }
        recalculateNormal();
    }

    public void recalculateNormal() {
        Vector2f t = Vector2f.subtract(p2, p1);
        float theta = (float) Math.toRadians(90);
        this.normal = new Vector2f(
                (float) (t.x * Math.cos(theta) - t.y * Math.sin(theta)),
                (float) (t.x * Math.sin(theta) + t.y * Math.cos(theta))
        );
        this.normal = Vector2f.normalize(normal);
    }

}
