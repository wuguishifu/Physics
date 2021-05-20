package com.bramerlabs.math.bounding_math;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings({"deprecation"})
public class BoundingBox {

    protected final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected final static int width = screenSize.width, height = screenSize.height;
    private boolean done = false, nextFrame = false;
    private int mouseX, mouseY;
    private boolean mouseDown;

    private static boolean fullscreen = true;

    public static void main(String[] args) {
        new BoundingBox().run();
    }

    public void run() {
        // set up objects
        Hexagon hexagon = new Hexagon(new Vector2f(screenSize.width/2f, screenSize.height/2f));
        Rectangle rectangle = new Rectangle(200, 200, 400, 250, false);
        Circle circle = new Circle(500, 700, 40);

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
                hexagon.paint(g);
                rectangle.paint(g);
                circle.paint(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = true;
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                }
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {}
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
        if (!fullscreen) {
            frame.move(screenSize.width / 4, screenSize.height / 4);
        }

        // main application loop
        while (!done) {

            hexagon.isSelected = hexagon.checkCollision(mouseX, mouseY);
            rectangle.isSelected = rectangle.checkCollision(mouseX, mouseY);
            circle.isSelected = circle.checkCollision(mouseX, mouseY);

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
