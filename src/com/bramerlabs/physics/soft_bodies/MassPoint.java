package com.bramerlabs.physics.soft_bodies;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class MassPoint {

    private static final int radius = 3;

    public Vector2f position, velocity, force;

    public float mass;

    public MassPoint(Vector2f position, float mass) {
        this.position = position;
        this.mass = mass;
        this.velocity = new Vector2f(0, 0);
        this.velocity = new Vector2f(0, 0);
        this.force = new Vector2f(0, 0);
    }

    public void update(Vector2f springForce) {
        // set force to 0
        force = new Vector2f(0, 0);

        // add force: gravity, spring, normals
        force = Vector2f.add(force, springForce);
        force = Vector2f.add(force, new Vector2f(0, 0.01f));

        // perform the Euler integration
        // accelerate the velocity
        velocity = Vector2f.add(velocity, Vector2f.divide(force, new Vector2f(mass)));

        // move the position
        position = Vector2f.add(position, velocity);
    }

    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        g.drawOval((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
    }

}
