package com.bramerlabs.math.ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Switch {

    private float x, y;
    private float width, height;
    private float xMid;
    private Color color;

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

    /**
     * draws centered text
     * @param g - the graphics object handed down by panel.repaint()
     * @param minX - the min x value to draw in
     * @param minY - the min y value to draw in
     * @param maxX - the max x value to draw in
     * @param maxY - the max y value to draw in
     * @param string - the string to draw
     */
    private static void drawCenteredText(Graphics g, int minX, int minY, int maxX, int maxY, String string) {
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(string, g2d);
        int x = minX + ((maxX - minX) - (int) r.getWidth()) / 2;
        int y = minY + ((maxY - minY) - (int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(string, x, y);
    }
}
