package com.bramerlabs.physics.pendulum;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class MassPoint {

    public Vector2f position, velocity, force;
    public final float mass;

    private static final int radius = 20;
    private static final Color color = new Color(52, 175, 88, 142);

    public MassPoint(Vector2f position, float mass) {
        this.position = position;
        this.mass = mass;
        this.velocity = new Vector2f(0, 0);
        this.force = new Vector2f(0, 0);
    }

    public void updateForce(Vector2f force) {
        this.force = force;
    }

    public void updateVelocity() {
        this.velocity = Vector2f.add(velocity, Vector2f.scale(force, 1 / mass));
        this.force = Vector2f.zero;
    }

    public void updatePosition() {
        this.position = Vector2f.add(position, velocity);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval((int) this.position.x - radius, (int) this.position.y - radius, 2 * radius, 2 * radius);
    }

}
