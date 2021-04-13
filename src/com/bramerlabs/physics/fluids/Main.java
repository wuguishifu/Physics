package com.bramerlabs.physics.fluids;

import com.bramerlabs.engine.math.vector.Vector3f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

public class Main {

    // the data regarding the fluid screen
    int size = 32, boxSize = 32;
    float[][] density = new float[size][size];
    float[][] density_new = new float[size][size];
    Vector3f[][] velocity = new Vector3f[size][size];
    Vector3f[][] velocity_new = new Vector3f[size][size];

    float k = 0.01f;
    int numIter = 5;
    private float dx0y0;

    float mouseX, mouseY;
    int newX, newY;
    boolean mouseDown = false;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                density_new[i][j] = 0;
                velocity_new[i][j] = new Vector3f(0);
            }
        }

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(size * boxSize, size * boxSize));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {

                        // draw the color
                        Vector3f color = new Vector3f(255);
                        color.scale(density[i][j]);
                        color.maximize(0);
                        color.minimize(255);
                        g.setColor(new Color((int) color.x, (int) color.y, (int) color.z));
                        g.fillRect(i * boxSize, j * boxSize, boxSize, boxSize);
//
//                        // draw the density values (for testing)
//                        g.setColor(Color.BLACK);
//                        drawCenteredText(g, i * boxSize, j * boxSize, (i + 1) * boxSize, (j + 1) * boxSize, String.format("%.3f", density[i][j]));

                        // draw the grid
                        g.setColor(Color.BLACK);
                        g.drawRect(i * boxSize, j * boxSize, boxSize, boxSize);

                        // draw the mouse selection
                        int drawX = (int) (mouseX - (mouseX % boxSize));
                        int drawY = (int) (mouseY - (mouseY % boxSize));
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setStroke(new BasicStroke(3));
                        g2d.setColor(Color.BLUE);
                        g2d.drawRect(drawX, drawY, boxSize, boxSize);
                        g2d.setStroke(new BasicStroke(1));
                    }
                }
            }
        };
        panel.setPreferredSize(new Dimension(size * boxSize, size * boxSize));

        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseDown = true;
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseDown = false;
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };

        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                newX = (int) ((mouseX - (mouseX % boxSize)) / boxSize);
                newY = (int) ((mouseY - (mouseY % boxSize)) / boxSize);
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                newX = (int) ((mouseX - (mouseX % boxSize)) / boxSize);
                newY = (int) ((mouseY - (mouseY % boxSize)) / boxSize);
            }
        };

        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseMotionListener);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        panel.repaint();

        while (true) {
            // take mouse inputs
            if (mouseDown) {
                density[Math.min(Math.max(newX, 0), size - 1)][Math.min(Math.max(newY, 0), size - 1)] += 5.0f;
            }

            // update density
            calculateDensity();

            // repaint the
            panel.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
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

    /**
     * calculates the density of a specific cell
     */
    public void calculateDensity() {
        for (int iter = 0; iter < numIter; iter++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    float N = i - 1 > 0 ? density_new[i - 1][j] : -1;
                    float S = i + 1 < size ? density_new[i + 1][j] : -1;
                    float E = j - 1 > 0 ? density_new[i][j - 1] : -1;
                    float W = j + 1 < size ? density_new[i][j + 1] : -1;
                    float den = (N != -1 ? N : 0)
                            + (S != -1 ? S : 0)
                            + (E != -1 ? E : 0)
                            + (W != -1 ? W : 0);
                    float num = (N != -1 ? 1 : 0)
                            + (S != -1 ? 1 : 0)
                            + (E != -1 ? 1 : 0)
                            + (W != -1 ? 1 : 0);
                    num = num > 0 ? num : 1;
                    float ds = den / num;
                    density_new[i][j] = (density[i][j] + k * ds) / (1 + k);
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    density[i][j] = density_new[i][j];
                }
            }
        }
    }
}