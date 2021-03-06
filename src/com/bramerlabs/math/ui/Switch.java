package com.bramerlabs.math.ui;

import java.awt.*;

public class Switch {

    private final float x, y;
    private final float width, height;
    private final float xMid;
    private final Color color;

    private boolean value; // off my default
    public Switch(float x, float y, float width, float height, boolean value, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.value = value;
        this.color = color;
        this.xMid = x + width / 2;
    }

    public boolean value() {
        return this.value;
    }

    public boolean inBounds(float mouseX, float mouseY) {
        float xMax = x + width;
        float yMax = y + height;
        return mouseX >= x && mouseX <= xMax && mouseY >= y && mouseY <= yMax;
    }

    public void toggle() {
        value = !value;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        if (value) {
            g.fillRect((int) x, (int) y, (int) width / 2, (int) height);
            g.drawRect((int) x, (int) y, (int) width, (int) height);
        } else {
            g.drawRect((int) x, (int) y, (int) width, (int) height);
            g.fillRect((int) xMid, (int) y, (int) width / 2, (int) height);
        }
    }
}
