package com.bramerlabs.physics.pendulum;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.physics.soft_bodies.Spring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class Main {

    private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int width = screenSize.width, height = screenSize.height;
    private boolean done = false, nextFrame = false;
    private int mouseX, mouseY;
    boolean mouseDown = false;

    public boolean fullscreen = true;


    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        // set up objects

        ArrayList<DoublePendulum> dps = new ArrayList<>();

        int numberOfPendulums = 10;
        for (int i = 0; i < 25; i += 25 / numberOfPendulums) {
//            dps.add(new DoublePendulum(height / 4 - 20f, height / 4 - 20f, 2.8f + 0.001f * i, 1.30f + 0.001f * i, 1.0f, 1.5f, new Vector2f(width/2, height/2), new Color( 10 * i, 250 - 10 * i, 100)));
            dps.add(new DoublePendulum(height / 4 - 20f, height / 4 - 20f, 1.6f, 1.30f + 0.001f * i, 1.0f, 1.5f, new Vector2f(width/2, height/2), new Color( 10 * i, 250 - 10 * i, 100)));
        }

//        dps.add(new DoublePendulum(height / 4 - 20f, height / 4 - 20f, 1.6f, 1.30f, 1.0f, 1.5f, new Vector2f(width/2, height/2), new Color(100,  50, 100)));

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
                for (DoublePendulum dp : dps) {
                    dp.paint(g);
                }
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
        if (!fullscreen) {
            frame.move(screenSize.width / 4, screenSize.height / 4);
        }

        // main application loop
        while (!done) {

            panel.repaint();

            for (DoublePendulum dp : dps) {
                dp.update();
            }

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
