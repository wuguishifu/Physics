package com.bramerlabs.math.poisson;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Node {

    private Vector2f pos;
    private int radius = 5;
    private Color color = new Color(79, 100, 137);

    public Node(Vector2f pos) {
        this.pos = pos;
//        this.color = new Color((int) (256 * Math.random()), (int) (256 * Math.random()), (int) (256 * Math.random()));
    }

    public void paint(Graphics g, int radius) {
        g.setColor(color);
        g.fillOval((int) (pos.x - radius), (int) (pos.y - radius), 2 * radius, 2 * radius);
    }

    public float getX() {
        return this.pos.x;
    }

    public float getY() {
        return this.pos.y;
    }

    public Vector2f getPos() {
        return this.pos;
    }

}
