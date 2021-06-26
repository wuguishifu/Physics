package com.bramerlabs.math.ui;

import java.awt.*;

public class ToggleButton {

    private final float x, y;
    private final float width, height;
    private final Color color;

    private boolean value;

    public ToggleButton(float x, float y, float width, float height, boolean value, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.value = value;
        this.color = color;
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
            g.setColor(color);
            g.fillRect((int) x, (int) y, (int) width, (int) height);
        }
        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

}
