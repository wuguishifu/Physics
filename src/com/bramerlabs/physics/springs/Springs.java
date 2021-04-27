package com.bramerlabs.physics.springs;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Springs {

    private final static int width = 800, height = 600;

    private boolean done = false, nextFrame = false;

    int connectionPointRadius = 25;
    private Vector2f connectionPoint;
    private Mass mass;
    private Spring spring;

    private int mouseX, mouseY;
    boolean mouseDown = false;

    public static void main(String[] args) {
        new Springs().run();
    }

    public void run() {
        connectionPoint = new Vector2f(width / 2f, height / 2f);
        mass = new Mass(new Vector2f(width / 2f + 100, height / 2f), 10.0f);
        spring = new Spring(connectionPoint, mass, Vector2f.length(Vector2f.subtract(connectionPoint, mass.position)), 10.0f, 0.1f);
        mass.position = Vector2f.add(mass.position, new Vector2f(50, 0));

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(2));
                spring.paint(g);
                g.setColor(new Color(170, 170, 170));
                g.fillRect((int) (connectionPoint.x - connectionPointRadius), (int) (connectionPoint.y - connectionPointRadius), 2 * connectionPointRadius, 2 * connectionPointRadius);
                g.setColor(new Color(88, 88, 88));
                g.drawRect((int) (connectionPoint.x - connectionPointRadius), (int) (connectionPoint.y - connectionPointRadius), 2 * connectionPointRadius, 2 * connectionPointRadius);
                mass.paint(g);
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
            this.updateForce();

            if (previousMassPosition2.x > previousMassPosition1.x && mass.position.x > previousMassPosition1.x) {
                System.out.println(previousMassPosition1);
            } else if (previousMassPosition2.x < previousMassPosition1.x && mass.position.x < previousMassPosition1.x) {
                System.out.println(previousMassPosition1);
            }
            previousMassPosition2 = previousMassPosition1;
            previousMassPosition1 = mass.position;


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

    Vector2f previousMassPosition1 = Vector2f.zero;
    Vector2f previousMassPosition2 = Vector2f.zero;

    private void updateForce() {
        Vector2f directionNormal = Vector2f.normalize(Vector2f.subtract(connectionPoint, mass.position));
        float forceMagnitude = 0.001f * spring.calculateForce();
        Vector2f force = Vector2f.normalize(directionNormal, forceMagnitude);
        mass.updateForce(force);
        mass.updateVelocity();
        mass.updatePosition();
    }

}
