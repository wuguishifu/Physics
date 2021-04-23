package com.bramerlabs.physics.springs;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Mass {

    public float mass;
    public Vector2f position, force, velocity;

    int radius = 10;

    public Mass(Vector2f position, float mass) {
        this.position = position;
        this.force = Vector2f.zero;
        this.velocity = Vector2f.zero;
        this.mass = mass;
    }

    public void updateForce(Vector2f dF) {
        this.force = Vector2f.add(force, dF);
    }

    public void updateVelocity() {
        this.velocity = Vector2f.add(velocity, Vector2f.divide(force, mass));
        this.force = Vector2f.zero;
    }

    public void updatePosition() {
        this.position = Vector2f.add(position, velocity);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(170, 170, 170));
        g.fillOval((int) (position.x - radius), (int) (position.y - radius), 2 * radius, 2 * radius);
        g.setColor(new Color(88, 88, 88));
        g.drawOval((int) (position.x - radius), (int) (position.y - radius), 2 * radius, 2 * radius);
    }

}
