package com.bramerlabs.visualizations.orbits;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Orbit {

    private JFrame frame;
    private JPanel panel;

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Dimension windowSize = new Dimension(7 * screenSize.width / 8, 7 * screenSize.height / 8);

    int mouseX, mouseY;

    private boolean end = false;

    public Vector2f p1, p2;
    public int r1 = 5, r2 = 5, r3 = 5;
    float aV1 = -0.05f, av2 = -0.1f;

    int current = 0;

    private ArrayList<Line> lines = new ArrayList<>();

    private static class Line {
        public Vector2f v1, v2;
        public Line(Vector2f v1, Vector2f v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
        public void paint(Graphics g, Dimension w) {
            g.drawLine((int) (v1.x + 0.5 * w.width), (int) (v1.y + 0.5 * w.height), (int) (v2.x + 0.5 * w.width), (int) (v2.y + 0.5 * w.height));
        }
    }

    public void update() {
        current++;
        p1 = rotate(p1, aV1);
        p2 = rotate(p2, av2);
        if (current > 10) {
            lines.add(new Line(p1, p2));
        }
    }

    public static Vector2f rotate(Vector2f v, float a) {
        float x = (float) (v.x * Math.cos(a) - v.y * Math.sin(a));
        float y = (float) (v.x * Math.sin(a) + v.y * Math.cos(a));
        return new Vector2f(x, y);
    }

    public static void main(String[] args) {
        new Orbit().init();
    }

    private void init() {
        p1 = new Vector2f(100, 0);
        p2 = new Vector2f(200, 0);

        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel(true) {

            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);

                g2d.fillOval(windowSize.width / 2 - r3, windowSize.height / 2 - r3, 2 * r3, 2 * r3);
                g2d.fillOval((int) p1.x - r1 + windowSize.width / 2, (int) p1.y - r1 + windowSize.height / 2, 2 * r1, 2 * r1);
                g2d.fillOval((int) p2.x - r2 + windowSize.width / 2, (int) p2.y - r2 + windowSize.height / 2, 2 * r2, 2 * r2);

                for (Line l : lines) {
                    l.paint(g, windowSize);
                }
            }
        };
        panel.setPreferredSize(windowSize);
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
            }
        };
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {}
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    end = true;
                }
            }
            public void keyReleased(KeyEvent keyEvent) {}
        };
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseMotionListener);
        frame.addKeyListener(keyListener);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.repaint();
        this.run();
    }

    @SuppressWarnings("BusyWait")
    private void run() {
        while (!end) {
            panel.repaint();
            this.update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        frame.dispose();
    }

}
