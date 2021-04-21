package com.bramerlabs.math.fractals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Fractals {

//    public final static int WIDTH = 1920, HEIGHT = 1080;
    public final static int WIDTH = 1600, HEIGHT = 1200;

    private static boolean done;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(WIDTH/2, HEIGHT/2));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Mandelbrot.draw(g);
            }
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
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(panel);
        frame.addKeyListener(keyListener);
        frame.pack();
        frame.setVisible(true);
        panel.repaint();
        while (!done) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.dispose();
    }
}