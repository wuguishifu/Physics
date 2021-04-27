package com.bramerlabs.math.dijkstra;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dijkstra {

    protected final static int CELL_SIZE = 40;
    protected final static int CELLS_HORIZONTAL = 40;
    protected final static int CELLS_VERTICAL = 30;
    protected final static int WIDTH = CELLS_HORIZONTAL * CELL_SIZE;
    protected final static int HEIGHT = CELLS_VERTICAL * CELL_SIZE;

    private Vector2f initialMousePosition, currentMousePosition;

    private int keyDown = -1;
    private int buttonDown = -1;

    private boolean useCorners = false;

    private boolean done = false, nextFrame = false, mouseDown = false;

    public Grid grid;

    public static void main(String[] args) {
        new Dijkstra().run();
    }

    private void run() {
        // set up objects
        grid = new Grid(CELLS_HORIZONTAL, CELLS_VERTICAL, CELL_SIZE);
        initialMousePosition = Vector2f.zero;
        currentMousePosition = Vector2f.zero;

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                grid.paint(g);
//                g.drawLine((int) initialMousePosition.x, (int) initialMousePosition.y, (int) currentMousePosition.x, (int) currentMousePosition.y);
            }
        };

        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                buttonDown = mouseEvent.getButton();
                switch (mouseEvent.getButton()) {
                    case MouseEvent.BUTTON1:
                        initialMousePosition = new Vector2f(mouseEvent.getX(), mouseEvent.getY());
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
            public void mouseReleased(MouseEvent mouseEvent) {
                buttonDown = -1;
                initialMousePosition = Vector2f.zero;
                grid.copyTempCells();
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                currentMousePosition = new Vector2f(mouseEvent.getX(), mouseEvent.getY());
                if (buttonDown == MouseEvent.BUTTON1) {
                    if ((int) currentMousePosition.x / CELL_SIZE != (int) initialMousePosition.x / CELL_SIZE || (int) currentMousePosition.y / CELL_SIZE != (int) initialMousePosition.y / CELL_SIZE) {
                        if (keyDown == KeyEvent.VK_S) {
                            grid.setTempCellsSquare(initialMousePosition, currentMousePosition);
                        } else {
                            grid.setTempCells(initialMousePosition, currentMousePosition, true);
                        }
                    }
                }
                if (buttonDown == MouseEvent.BUTTON3) {
                    grid.setCell(mouseEvent.getX(), mouseEvent.getY(), -1);
                }
            }
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
                        grid.findPath(useCorners); break;
                    case KeyEvent.VK_C:
                        System.out.println(grid.containsStart + ", " + grid.containsEnd); break;
                    case KeyEvent.VK_P:
                        grid.printGrid(); break;
                    case KeyEvent.VK_A:
                        useCorners = !useCorners;
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