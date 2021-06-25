package com.bramerlabs.physics.projectile_motion;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Bullet {

    // rendering variables
    private final static Color color = new Color(79, 62, 175);
    public static float radius = 5;

    public Vector2f position;
    public Vector2f velocity;

    public ArrayList<Vector2f> prev = new ArrayList<>();

    public boolean needsUpdate = true;

    public Bullet(float x, float y, Vector2f velocity) {
        this.position = new Vector2f(x, y);
        this.velocity = velocity;
    }

    public void update(float g) {
        prev.add(position);

        Vector2f gravity = new Vector2f(0, g);
        position = Vector2f.add(position, velocity);
        velocity = Vector2f.add(velocity, gravity);

        if (position.y > ProjectileMotion.windowSize.height + 10 || position.x > ProjectileMotion.windowSize.width + 10) {
            needsUpdate = false;
        }
    }

    public void paint(Graphics g) {
        // draw the bullet
        g.setColor(color);
        g.fillOval((int) (position.x - radius), (int) (position.y - radius), (int) (2 * radius), (int) (2 * radius));

        // draw the trail
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < prev.size() - 1; i++) {
            g.drawLine((int) prev.get(i).x, (int) prev.get(i).y, (int) prev.get(i + 1).x, (int) prev.get(i + 1).y);
        }
        g2d.dispose();
    }
}