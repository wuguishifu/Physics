package com.bramerlabs.physics.projectile_motion;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class VelocitySlider {

    public Vector2f p1, p2;
    public Vector2f value;
    public float velocity;

    public static final Color color = new Color(120, 120, 120);

    public static final int radius = 5;

    public VelocitySlider(Vector2f p1, Vector2f p2) {
        this.p1 = p1;
        this.p2 = p2;
        Vector2f normal = Vector2f.subtract(p2, p1);
        float midpoint = Vector2f.length(normal) / 2;
        value = Vector2f.add(p1, Vector2f.normalize(normal, midpoint));
        velocity = 0.5f;
    }

    public boolean inSliderBounds(int mouseX, int mouseY) {
        return mouseX >= value.x - radius && mouseX <= value.x + radius && mouseY >= value.y - radius && mouseY <= value.y + radius;
    }

    public void moveSliderTo(int mouseX) {
        if (mouseX <= p1.x || mouseX >= p2.x) {
            return;
        }
        value = new Vector2f(mouseX, value.y);
        velocity = Vector2f.length(Vector2f.subtract(value, p1)) / Vector2f.length(Vector2f.subtract(p2, p1));
    }

    public void paint(Graphics g) {
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        g2d.fillOval((int) value.x - radius, (int) value.y - radius, 2 * radius, 2 * radius);

        drawCenteredText(g, (int) p1.x, (int) p1.y - 40, (int) p2.x, (int) p2.y - 20, String.valueOf(velocity));
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
