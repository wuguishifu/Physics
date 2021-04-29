package com.bramerlabs.physics.collisions;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Collisions {

    protected final static int WIDTH = 1600;
    protected final static int HEIGHT = 1200;

    private boolean done = false;

    private ArrayList<CollidableObject> objects = new ArrayList<>();

    public static void main(String[] args) {
        new Collisions().run();
    }

    private Vector2f initialPosition, direction;

    private Ball ball = null;

    private int mouseDown = -1;

    private void run() {
        // set up objects
        objects.add(new CollidableObject(100, 100, 100, 100));
        for (int i = 0; i < 20; i++) {
            objects.add(new CollidableObject(WIDTH * Math.random(), HEIGHT * Math.random(), 500 * Math.random(), 500 * Math.random()));
        }
        initialPosition = Vector2f.zero;
        direction = Vector2f.zero;

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (CollidableObject object : objects) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setStroke(new BasicStroke(2));
                    object.paint(g);
                    g.setColor(new Color(196, 196, 196));
                    g.fillOval((int) initialPosition.x - 5, (int) initialPosition.y - 5, 10, 10);
                    g.drawLine((int) initialPosition.x, (int) initialPosition.y, (int) direction.x + (int) initialPosition.x, (int) direction.y + (int) initialPosition.y);
                    if (ball != null) {
                        ball.paint(g);
                    }
                }
            }
        };
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseDown = mouseEvent.getButton();
                if (mouseDown == MouseEvent.BUTTON1) {
                    initialPosition = new Vector2f(mouseEvent.getX(), mouseEvent.getY());
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseDown == MouseEvent.BUTTON1) {
                    direction = Vector2f.subtract(initialPosition, mouseEvent.getX(), mouseEvent.getY());
                    ball = new Ball(initialPosition, direction);
                }
                mouseDown = -1;
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                if (mouseDown == MouseEvent.BUTTON1) {
                    direction = Vector2f.subtract(initialPosition, mouseEvent.getX(), mouseEvent.getY());
                }
            }
            public void mouseMoved(MouseEvent mouseEvent) {}
        };
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {}
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    done = true;
                }
            }
            public void keyReleased(KeyEvent keyEvent) {}
        };
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseMotionListener);
        frame.addKeyListener(keyListener);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        // main application loop
        while (!done) {
            panel.repaint();
            if (ball != null) {
                ball.update(objects);
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.dispose();
    }
}