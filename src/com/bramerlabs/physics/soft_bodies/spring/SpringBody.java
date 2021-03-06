package com.bramerlabs.physics.soft_bodies.spring;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SpringBody {

    private final static int width = 800, height = 600;

    private Body body;

    private boolean done = false, nextFrame = false;

    private int mouseX, mouseY;
    boolean mouseDown = false;

    private ArrayList<Object> objects;

    public static void main(String[] args) {
        new SpringBody().run();
    }

    public void run() {

        body = new Body(new Vector2f(width/4f, height/4f), 5);

        objects = new ArrayList<>();
        objects.add(new Object(new Vector2f[]{
                new Vector2f(0, 550),
                new Vector2f(1600, 550),
        }));

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (Object object : objects) {
                    object.paint(g);
                }
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
                    for (Spring spring : body.springs) {
                        spring.L0 += 1.0f;
                    }
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

            body.update(objects);
            panel.repaint();


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

        frame.dispose();

    }

}
