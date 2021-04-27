package com.bramerlabs.math.dijkstra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dijkstra {

    protected final static int CELL_SIZE = 40;
    protected final static int CELLS_HORIZONTAL = 40;
    protected final static int CELLS_VERTICAL = 30;
    protected final static int WIDTH = CELLS_HORIZONTAL * CELL_SIZE;
    protected final static int HEIGHT = CELLS_VERTICAL * CELL_SIZE;

    private int mouseX, mouseY;

    private int keyDown = -1;

    private boolean done = false, nextFrame = false, mouseDown = false;

    public Grid grid;

    public static void main(String[] args) {
        new Dijkstra().run();
    }

    private void run() {
        // set up objects
        grid = new Grid(CELLS_HORIZONTAL, CELLS_VERTICAL, CELL_SIZE);

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                grid.paint(g);
            }
        };

        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                switch (mouseEvent.getButton()) {
                    case MouseEvent.BUTTON1:
                        switch (keyDown) {
                            case -1:
                                grid.setCell(mouseEvent.getX(), mouseEvent.getY(), 1);
                                break;
                            case KeyEvent.VK_Q:
                                grid.setCell(mouseEvent.getX(), mouseEvent.getY(), 2);
                                break;
                            case KeyEvent.VK_W:
                                grid.setCell(mouseEvent.getX(), mouseEvent.getY(), 3);
                                break;
                        }
                        break;
                    case MouseEvent.BUTTON3:
                        grid.setCell(mouseEvent.getX(), mouseEvent.getY(), -1);
                        break;
                }
            }
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
                        done = true; break;
                    case KeyEvent.VK_SPACE:
                        nextFrame = true; break;
                    case KeyEvent.VK_ENTER:
                        grid.findPath(); break;
                    case KeyEvent.VK_C:
                        System.out.println(grid.containsStart + ", " + grid.containsEnd); break;
                }
                keyDown = keyEvent.getKeyCode();
            }
            public void keyReleased(KeyEvent keyEvent) {
                keyDown = -1;
            }
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