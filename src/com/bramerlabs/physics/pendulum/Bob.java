package com.bramerlabs.physics.pendulum;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Bob {

    // location variables
    public float x, y;
    public float mass;

    // drawing variables
    private static final float density = 0.1f;
    private int radius;

    public Bob(float x, float y, float mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        radius = (int) (mass / density);
    }

    public void paint(Graphics g) {
        g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
    }

}
