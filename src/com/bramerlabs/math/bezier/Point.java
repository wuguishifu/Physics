package com.bramerlabs.math.bezier;

import java.awt.*;

public class Point {

    public final static int radius = 7;
    public final static int selectionRadius = 20;

    public float x, y;
    public Color color;

    public Point(float x, float y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
        this.color = new Color(0, 0 ,0);
    }

    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean inBounds(int x, int y, int radius) {
        return !(x < this.x - radius || x > this.x + radius || y < this.y - radius || y > this.y + radius);
    }

}