package com.bramerlabs.math.bounding_math;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Circle {

    protected float x, y, radius;

    protected boolean isSelected = false;

    public Circle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean checkCollision(float x, float y) {
        return (Vector2f.distance(new Vector2f(x, y), new Vector2f(this.x, this.y)) < radius);
    }

    public void paint(Graphics g) {
        g.setColor(isSelected ? Color.RED : Color.BLUE);
        g.drawOval((int) (x-radius), (int) (y-radius), (int) (2*radius), (int) (2*radius));
    }

}
