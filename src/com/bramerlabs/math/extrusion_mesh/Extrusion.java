package com.bramerlabs.math.extrusion_mesh;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class Extrusion {

    private JFrame frame;
    private JPanel panel;

    protected static Dimension windowSize = new Dimension(800, 800);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Vector2f windowPos = new Vector2f(screenSize.width/2f - windowSize.width/2f, screenSize.height/2f - windowSize.height/2f);

    private int mouseX, mouseY;
    private boolean end = false;

    private ArrayList<Point> activePoints = new ArrayList<>();
    private final ArrayList<ArrayList<Point>> curves = new ArrayList<>();

    private final static int numSubdivisions = 4;

    public static void main(String[] args) {
        new Extrusion().init();
    }

    public void drawCurve(Graphics g, ArrayList<Point> curve) {
        // draw top curve
        for (int i = 0; i < curve.size() - 1; i++) {
            g.drawLine(curve.get(i).x, curve.get(i).y, curve.get(i + 1).x, curve.get(i + 1).y);
        }

        // draw reflection
        int h = windowSize.height;
        for (int i = 0; i < curve.size() - 1; i++) {
            g.drawLine(curve.get(i).x, h - curve.get(i).y, curve.get(i + 1).x, h - curve.get(i + 1).y);
        }

        // draw connecting lines
        for (int i = 0; i < curve.size() - 1; i++) {
            g.drawLine(curve.get(i).x, curve.get(i).y, curve.get(i).x, h - curve.get(i).y);
        }

        ArrayList<Point> curve2 = new ArrayList<>();
        for (Point point : curve) {
            curve2.add(new Point(point.x, h - point.y));
        }
        subdivide(g, curve, curve2, numSubdivisions);
    }

    public void subdivide(Graphics g, ArrayList<Point> c1, ArrayList<Point> c2, int depth) {
        if (depth <= 0) {
            return;
        }
        ArrayList<Point> sub = new ArrayList<>();
        for (int i = 0; i < c1.size(); i++) {
            sub.add(new Point(c1.get(i).x, (c1.get(i).y + c2.get(i).y) / 2));
        }
        for (int i = 0; i < sub.size() - 1; i++) {
            g.drawLine(sub.get(i).x, sub.get(i).y, sub.get(i + 1).x, sub.get(i + 1).y);
        }
        subdivide(g, c1, sub, depth - 1);
        subdivide(g, c2, sub, depth - 1);
    }

    public void init() {
        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        panel = new JPanel(true) {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setStroke(new BasicStroke(1));
                super.paint(g);
                g.setColor(new Color(219, 207, 117));
                drawCurve(g, activePoints);
                for (ArrayList<Point> curve : curves) {
                    drawCurve(g, curve);
                }
            }
        };
        panel.setPreferredSize(windowSize);
        panel.setBackground(new Color(22, 22, 22));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                activePoints = new ArrayList<>();
                activePoints.add(new Point(mouseX, mouseY));
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                activePoints.add(new Point(mouseX, mouseY));
                curves.add(activePoints);
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
//                if (mouseX % 20 == 0) {
                    activePoints.add(new Point(mouseX, mouseY));
//                }
//                activePoints.add(new Point((int) (20 * Math.floor(mouseX / 20f)), mouseY));
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
            }
        };
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {}
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        end = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        break;
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

    @SuppressWarnings("BusyWait")
    public void run() {
        while (!end) {

            panel.repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        frame.dispose();
    }

}
