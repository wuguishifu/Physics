package com.bramerlabs.physics.gravity_2d;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Particle {

    public Vector2f position;
    public Vector2f velocity;
    public Vector2f acceleration;
    public Vector2f force;

    public int radius = 10;
    public float mass;

    public Color color = new Color(45, 147, 35);

    public Particle(int x, int y, float mass) {
        this.position = new Vector2f(x, y);
        this.velocity = new Vector2f(0, 0);
        this.acceleration = new Vector2f(0, 0);
        this.force = new Vector2f(0, 0);
        this.mass = mass;
    }

    public Particle(int x, int y, int dx, int dy, float mass) {
        this.position = new Vector2f(x, y);
        this.velocity = new Vector2f(dx, dy);
        this.acceleration = new Vector2f(0, 0);
        this.force = new Vector2f(0, 0);
        this.mass = mass;
    }

    final static float G = 100;
    public void update(ArrayList<Particle> particles) {
        this.force = new Vector2f(0, 0);
        this.acceleration = new Vector2f(0, 0);
        for (Particle particle : particles) {
            if (particle != this) {
                Vector2f normal = Vector2f.subtract(this.position, particle.position);
                Vector2f v = this.position;
                Vector2f u = particle.position;
                float dx = 0.5f * (v.x - u.x);
                float dy = 0.5f * (v.y - u.y);
                float magnitude = -G * particle.mass * this.mass / (dx * dx + dy * dy);
                this.force = Vector2f.add(force, Vector2f.normalize(normal, magnitude));
            }
        }
        this.acceleration = Vector2f.scale(force, 1f/mass);
        this.velocity = Vector2f.add(velocity, acceleration);
    }

    public void move() {
        this.position = Vector2f.add(position, velocity);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval((int) position.x - radius, (int) position.y - radius, 2 * radius, 2 * radius);
        g.setColor(Color.RED);
        g.drawLine((int) position.x, (int) position.y, (int) (position.x + velocity.x * 10), (int) (position.y + velocity.y * 10));
        g.setColor(Color.BLUE);
        g.drawLine((int) position.x, (int) position.y, (int) (position.x + force.x * 100), (int) (position.y + force.y * 100));
    }

}
