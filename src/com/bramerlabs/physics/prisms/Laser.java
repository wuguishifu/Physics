package com.bramerlabs.physics.prisms;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Laser {

    Vector2f position;
    Vector2f direction;
    public int pointRadius = 10;
    public int dragRadius = 100;

    public Laser(Vector2f position, Vector2f direction) {
        this.position = position;
        this.direction = direction;
    }

    public boolean onRadius(float x, float y) {
        Vector2f negativeDirection = Vector2f.subtract(Vector2f.zero, direction);
        Vector2f negativePosition = Vector2f.add(position, Vector2f.normalize(negativeDirection, dragRadius));
        return x <= negativePosition.x + pointRadius && x >= negativePosition.x - pointRadius && y <= negativePosition.y + pointRadius && y >= negativePosition.y - pointRadius;
    }

    public boolean onPoint(float x, float y) {
        return x <= position.x + pointRadius && x >= position.x - pointRadius && y <= position.y + pointRadius && y >= position.y - pointRadius;
    }

    public void translateLaser(float x, float y) {
        this.position = new Vector2f(x, y);
    }

    public void rotateLaser(float x, float y) {
        direction = Vector2f.normalize(Vector2f.subtract(position, new Vector2f(x, y)));
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        Vector2f end = Vector2f.add(position, Vector2f.normalize(direction, (float) Math.sqrt(Main.height * Main.height + Main.width * Main.width)));
        g.drawLine((int) position.x, (int) position.y, (int) end.x, (int) end.y);
        g.setColor(Color.DARK_GRAY);
        Vector2f negativeDirection = Vector2f.subtract(Vector2f.zero, direction);
        Vector2f negativePosition = Vector2f.add(position, Vector2f.normalize(negativeDirection, dragRadius));
        g.drawLine((int) position.x, (int) position.y, (int) negativePosition.x, (int) negativePosition.y);
        g.fillOval((int) negativePosition.x - 5, (int) negativePosition.y - 5, 10, 10);
        g.setColor(Color.RED);
        g.fillOval((int) position.x - 5, (int) position.y - 5, 10, 10);
    }

}
