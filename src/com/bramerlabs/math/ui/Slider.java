package com.bramerlabs.math.ui;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public class Slider {

    private final Vector2f p1, p2;
    private Vector2f m;
    private float v;

    private final Vector2f normal;
    private final float dist;
    private final float amplitude;
    private final String label;

    private final Color color;
    public static final int radius = 5;

    private final static float strength = 0.01f;
    private final boolean normalized;

    DecimalFormat df = new DecimalFormat("0.000");

    public Slider(Vector2f p1, Vector2f p2, float amplitude, String label, Color color, boolean normalized) {
        this.p1 = p1;
        this.p2 = p2;
        this.amplitude = amplitude;
        this.label = label;
        this.color = color;
        this.normalized = normalized;
        normal = Vector2f.subtract(p2, p1);
        float midpoint = Vector2f.length(normal) / 2;
        m = Vector2f.add(p1, Vector2f.normalize(normal, midpoint));
        v = 0.5f;
        dist = Vector2f.distance(p2, p1);
    }

    public float value() {
        return normalized ? this.v : amplitude * (2 * this.v - 1);
    }

    public boolean inSliderBounds(float mouseX, float mouseY) {
        return mouseX >= m.x - radius && mouseX <= m.x + radius && mouseY >= m.y - radius && mouseY <= m.y + radius;
    }

    public void moveSliderTo(int mouseX, int mouseY) {
        float aProj = Vector2f.dot(Vector2f.subtract(new Vector2f(mouseX, mouseY), p1),
                Vector2f.normalize(Vector2f.subtract(p2, p1)));
        if (aProj < 0) {
            aProj = 0;
        } else if (aProj > dist) {
            aProj = dist;
        } else if (aProj >= dist / 2 - dist * strength && aProj <= dist / 2 + dist * strength) {
            aProj = dist / 2;
        }
        m = Vector2f.add(p1, Vector2f.normalize(normal, aProj));
        v = Vector2f.distance(m, p1) / Vector2f.distance(p2, p1);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        g2d.fillOval((int) m.x - radius, (int) m.y - radius, 2 * radius, 2 * radius);
        drawCenteredText(g, (int) p2.x, (int) p2.y, (int) p2.x + 20, (int) p2.y - 20, df.format(normalized ? this.v : 2 * this.v - 1));
        drawCenteredText(g, (int) p1.x - 20, (int) p1.y - 20, (int) p1.x, (int) p1.y, label);
    }

    public void drawLabel(Graphics g, int minX, int minY, int maxX, int maxY) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawString((new DecimalFormat("0.000")).format(normalized ? this.v : 2 * this.v - 1), (maxX - minX - (int) g2d.getFontMetrics().getStringBounds((new DecimalFormat("0.000")).format(normalized ? this.v : 2 * this.v - 1), g2d).getWidth() / 2 + minX), (maxY - minY - (int) g2d.getFontMetrics().getStringBounds((new DecimalFormat("0.000")).format(normalized ? this.v : 2 * this.v - 1), g2d).getWidth() / 2 + minY + g2d.getFontMetrics().getAscent()));
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
