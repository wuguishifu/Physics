package com.bramerlabs.physics.collisions;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class CollidableObject {

    public int x, y;
    public int w, h;

    public Color color = new Color(134, 188, 164);

    public CollidableObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean collides(Vector2f p) {
        return p.x <= x + w && p.x >= x && p.y <= y + h && p.y >= y;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, w, h);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
    }

}
