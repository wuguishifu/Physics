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

    private void run() {
        // set up objects
        objects.add(new CollidableObject(100, 100, 100, 100));

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (CollidableObject object : objects) {
                    object.paint(g);
                }
            }
        };
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {}
            public void mouseMoved(MouseEvent mouseEvent) {}
        };
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {}
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        done = true;
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
        frame.setVisible(true);

        // main application loop
        while (!done) {
            panel.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.dispose();
    }
}