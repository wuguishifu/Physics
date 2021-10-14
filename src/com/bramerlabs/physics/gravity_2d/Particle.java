package com.bramerlabs.physics.gravity_2d;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Particle {

    public Vector2f position;
    public Vector2f velocity = new Vector2f(0, 0);
    public Vector2f force = new Vector2f(0, 0);

    public int radius = 20;

    public Color color = new Color(45, 147, 35);

    public Particle(int x, int y) {
        this.position = new Vector2f(x, y);
    }

    public void update(ArrayList<Particle> particles) {
        this.force = new Vector2f(0, 0);
        for (Particle particle : particles) {
            if (this == particle) {
                continue;
            }
            float magnitude = (float) Math.pow(Vector2f.distance(particle.position, this.position), 2);
            Vector2f normal = Vector2f.normalize(Vector2f.subtract(particle.position, this.position), 100 * (1/magnitude));
            this.force = Vector2f.add(force, normal);
        }

        if (Vector2f.length(force) > 0) {
            float frictionMagnitude = Math.min(0.1f * Vector2f.length(force), 100.0f);
            Vector2f friction = Vector2f.normalize(Vector2f.subtract(new Vector2f(0, 0), force), frictionMagnitude);
            force = Vector2f.add(force, friction);
        }

        Vector2f newPosition = Vector2f.add(position, velocity);
        for (Particle particle : particles) {
            if (this == particle) {
                continue;
            }
            if (Vector2f.distance(particle.position, newPosition) < 2 * radius) {
                Vector2f normal = Vector2f.normalize(Vector2f.subtract(newPosition, particle.position));
                newPosition = Vector2f.add(newPosition, normal);
            }
        }

        this.position = newPosition;
        this.velocity = Vector2f.add(velocity, force);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval((int) position.x - radius, (int) position.y - radius, 2 * radius, 2 * radius);
    }

}