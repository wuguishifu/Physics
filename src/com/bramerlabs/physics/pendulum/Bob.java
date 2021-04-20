package com.bramerlabs.physics.pendulum;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;

import java.awt.*;

public class Bob {

    public static final float g = 0.2f;
    public static final Vector2f gravity = new Vector2f(0, g);

    public Vector2f position, velocity, force, connectedTo;
    public final float length;
    public final float mass;
    public float angle = 0.0f;

    Vector2f tension, weight;

    private static final int radius = 20;
    private static final Color color = new Color(52, 175, 88, 142);

    public Bob(Vector2f position, Vector2f connectedTo, float mass) {
        this.position = position;
        this.mass = mass;
        this.connectedTo = connectedTo;
        this.velocity = new Vector2f(0, 0);
        this.force = new Vector2f(0, 0);

        length = Vector2f.length(Vector2f.subtract(connectedTo, position));

        updateAngle();
    }

    public void update() {
        updateForce();
        updateVelocity();
        updatePosition();
    }

    public void updateForce() {
        Vector2f radius = Vector2f.subtract(connectedTo, position);
        tension = Vector2f.normalize(radius, Vector2f.dot(radius, gravity) + mass * Vector2f.length(velocity)/(length * length));
        weight = Vector2f.scale(gravity, mass);
        force = Vector2f.add(force, Vector2f.add(tension, weight));
    }

    public void updateVelocity() {
        this.velocity = Vector2f.add(velocity, Vector2f.scale(force, 1 / mass));
        this.force = Vector2f.zero;
    }

    public void updatePosition() {
        this.position = Vector2f.add(position, velocity);
        updateAngle();
    }

    private void updateAngle() {
        angle = (float) (Math.acos(Vector2f.dot(Vector2f.subtract(position, connectedTo), Vector2f.e2) / Vector2f.length(Vector2f.subtract(position, connectedTo))));
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (this.position.x - radius), (int) (this.position.y - radius), 2 * radius, 2 * radius);
        g.setColor(Color.BLACK);
        g.drawLine((int) position.x, (int) position.y, (int) (position.x + tension.x * 1000), (int) (position.y + tension.y * 1000));
        g.drawLine((int) position.x, (int) position.y, (int) (position.x + weight.x * 1000), (int) (position.y + weight.y * 1000));
        g.setColor(Color.BLUE);
        g.drawLine((int) position.x, (int) position.y, (int) (position.x + velocity.x * 10), (int) (position.y + velocity.y * 10));
    }

}