package com.bramerlabs.physics.soft_bodies.pressure;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    private static int width = 800, height = 600;

    private boolean done = false, nextFrame = false;

    private Body body;

    private int mouseX, mouseY;
    boolean mouseDown = false;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        body = new Body(new Vector2f(width/2f, height/2f));

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                body.paint(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
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
                    done = true;
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                    nextFrame = true;
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
                    body.movePointsTowardsCenter();
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
        panel.repaint();

        // main application loop
        while (!done) {

            panel.repaint();
            body.update();

//            // update manually
//            while (!nextFrame) {
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            nextFrame = false;

            // update automatically
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        // clean up
        frame.dispose();
    }

}
