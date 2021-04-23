package com.bramerlabs.physics.soft_bodies.spring;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class MassPoint {

    private static final int drawRadius = 3;
    public static final float radius = 20f;

    public Vector2f position, velocity, force;

    public float mass;

    public MassPoint(Vector2f position, float mass) {
        this.position = position;
        this.mass = mass;
        this.velocity = new Vector2f(0, 0);
        this.velocity = new Vector2f(0, 0);
        this.force = new Vector2f(0, 0);
    }

    public void updateForce(Vector2f springForce) {
        // add force: gravity, spring, normals
        force = Vector2f.add(force, springForce);
    }

    public void updateVelocity(float gravity) {
        force = Vector2f.add(force, new Vector2f(0, gravity)); // gravity
        velocity = Vector2f.add(velocity, Vector2f.divide(force, new Vector2f(mass)));
        force = new Vector2f(0, 0);
    }

    public void updatePosition() {
        this.position = Vector2f.add(position, velocity);
    }

    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval((int) position.x - drawRadius, (int) position.y - drawRadius, drawRadius * 2, drawRadius * 2);
        g.setColor(Color.BLACK);
        g.drawOval((int) position.x - drawRadius, (int) position.y - drawRadius, drawRadius * 2, drawRadius * 2);
    }

}
