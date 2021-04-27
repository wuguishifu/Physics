package com.bramerlabs.physics.prisms;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings({"deprecation"})
public class Prisms {

    protected final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected final static int width = screenSize.width, height = screenSize.height;
    private boolean done = false, nextFrame = false;
    private int mouseX, mouseY;
    private boolean mouseDown = false;

    private static boolean fullscreen = true;
    private boolean onRadius = false, onPoint = false;

    private Prism prism;
    private Laser laser;

    public static void main(String[] args) {
        new Prisms().run();
    }

    public void run() {
        // set up objects
//        prism = new Prism(Vector2f.random(width, height), Vector2f.random(width, height), Vector2f.random(width, height), 1.0f);
        prism = new Prism(new Vector2f(width/2f, height/2f), 300, Prism.glass);
        laser = new Laser(new Vector2f(Vector2f.add(Vector2f.center(prism.v3, prism.v1), Vector2f.normalize(prism.n3, 100))), new Vector2f((float) Math.cos(Math.toRadians(-20)), (float) Math.sin(Math.toRadians(-20))));

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if (fullscreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setVisible(true);
        }
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(2));
                prism.paint(g);
                laser.paint(g, prism);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = true;
                    if (laser.onRadius(mouseEvent.getX(), mouseEvent.getY())) {
                        onRadius = true;
                    } else if (laser.onPoint(mouseEvent.getX(), mouseEvent.getY())) {
                        onPoint = true;
                    }
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                    if (onRadius) {
                        onRadius = false;
                    } else if (onPoint) {
                        onPoint = false;
                    }
                }
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (onRadius) {
                    laser.rotateLaser(mouseX, mouseY);
                } else if (onPoint) {
                    laser.translateLaser(mouseX, mouseY);
                }
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (onRadius) {
                    laser.rotateLaser(mouseX, mouseY);
                } else if (onPoint) {
                    laser.translateLaser(mouseX, mouseY);
                }
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
        panel.setBackground(Color.BLACK);
        frame.addKeyListener(keyListener);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        panel.repaint();
        if (!fullscreen) {
            frame.move(screenSize.width / 4, screenSize.height / 4);
        }

        // main application loop
        while (!done) {

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
