package com.bramerlabs.math.bezier;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BezierCurve {

    private JFrame frame;
    private JPanel panel;

    private Dimension windowSize = new Dimension(800, 600);
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Vector2f windowPos = new Vector2f(screenSize.width/2f - windowSize.width/2f, screenSize.height/2f - windowSize.height/2f);

    int mouseX, mouseY;

    private boolean end = false;
    private boolean mouseDown = false;

    Point[] points;
    Point translatingPoint = null;
    int numSegments = 500;
    float separation = 1f/numSegments;

    public static void main(String[] args) {
        new BezierCurve().init();
    }

    private void drawBezier(Graphics g) {
        // draw the dashed lines
        // create copy of graphics context
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{8}, 0);
        // draw the connected lines
        g2d.setStroke(dashed);
        g2d.setColor(new Color(184, 184, 184));
        g2d.drawLine((int) points[0].x, (int) points[0].y, (int) points[1].x, (int) points[1].y);
        g2d.drawLine((int) points[1].x, (int) points[1].y, (int) points[2].x, (int) points[2].y);
        g2d.drawLine((int) points[2].x, (int) points[2].y, (int) points[3].x, (int) points[3].y);
        g2d.dispose();
        // dispose of graphics context

        g.setColor(new Color(69, 69, 69));
        g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3));
        Point[] bezierPoints = new Point[numSegments];
        for (int i = 0; i < numSegments; i++) {
            bezierPoints[i] = bezier(separation * i, points);
        }
        for (int i = 0; i < numSegments - 1; i++) {
            g.drawLine((int) bezierPoints[i].x, (int) bezierPoints[i].y, (int) bezierPoints[i + 1].x, (int) bezierPoints[i + 1].y);
        }
        g2d.dispose();
    }

    private static Point bezier(float t, Point[] p) {
        float x = (1-t)*((1-t)*((1-t)*p[0].x+t*p[1].x)+t*((1-t)*p[1].x+t*p[2].x))+t*((1-t)*((1-t)*p[1].x+t*p[2].x)+t*((1-t)*p[2].x+t*p[3].x));
        float y = (1-t)*((1-t)*((1-t)*p[0].y+t*p[1].y)+t*((1-t)*p[1].y+t*p[2].y))+t*((1-t)*((1-t)*p[1].y+t*p[2].y)+t*((1-t)*p[2].y+t*p[3].y));
        return new Point(x, y);
    }



    @SuppressWarnings("deprecation")
    private void init() {
        // set up points
        int width = windowSize.width;
        int height = windowSize.height;
        points = new Point[] {
                new Point(width/3f, height/3f, new Color(123, 188, 186)),
                new Point(width/3f, 2*height/3f, new Color(255, 158, 178)),
                new Point(2*width/3f, 2*height/3f, new Color(255, 158, 178)),
                new Point(2*width/3f, height/3f, new Color(123, 188, 186)),
        };

        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                drawBezier(g);
                for (Point p : points) {
                    p.paint(g);
                }
            }
        };
        panel.setPreferredSize(windowSize);
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = true;
                    for (Point p : points) {
                        if (p.inBounds(mouseX, mouseY, Point.selectionRadius)) {
                            translatingPoint = p;
                            break;
                        }
                    }
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                    translatingPoint = null;
                }
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseDown && translatingPoint != null) {
                    translatingPoint.moveTo(mouseX, mouseY);
                }
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseDown && translatingPoint != null) {
                    translatingPoint.moveTo(mouseX, mouseY);
                }
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
        frame.move((int) windowPos.x, (int) windowPos.y);
        frame.setVisible(true);
        panel.repaint();

        this.run();
    }

    private void run() {
        while (!end) {

            panel.repaint();

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
