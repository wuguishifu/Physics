package com.bramerlabs.math.bounding_math;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Rectangle {

    protected float x, y, width, height;
    protected boolean isSelected = false;

    public Rectangle(float x, float y, float width, float height, boolean byPoints) {
        if (byPoints) {
            this.width = width - x;
            this.height = height - y;
        } else {
            this.width = width;
            this.height = height;
        }
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(float px, float py) {
        return (px <= x + width && px >= x && py <= y + height && py >= y);
    }

    public void paint(Graphics g) {
        g.setColor(isSelected ? Color.RED : Color.BLUE);
        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

}
