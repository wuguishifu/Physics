package com.bramerlabs.physics.cellular_autonoma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Conways {

    private JFrame frame;
    private JPanel panel;

    private Dimension screenSize = new Dimension(800, 800);
    private int gridSize = 25;
    private int squareSize = screenSize.height / gridSize;
    private int[][] grid = new int[gridSize][gridSize];
    private int[][] nextGrid = new int[gridSize][gridSize];

    int mouseX, mouseY;

    private boolean end = false;
    private boolean run = false;

    public static void main(String[] args) {
        new Conways().init();
    }

    private void init() {
        // grid init
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = 0;
                nextGrid[i][j] = 0;
            }
        }

        // frame init
        frame = new JFrame();
        frame.setSize(screenSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // panel init
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                // paint grid
                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < gridSize; j++) {
                        if (grid[i][j] == 1) {
                            g.setColor(Color.BLACK);
                            g.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
                        }
                    }
                }

                // paint grid lines
                g.setColor(new Color(218, 218, 218));
                for (int i = 0; i < gridSize; i++) {
                    g.drawLine(i * squareSize, 0, i * squareSize, screenSize.height);
                    g.drawLine(0, i * squareSize, screenSize.width, i * squareSize);
                }
            }
        };
        panel.setPreferredSize(screenSize);
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                int gridX = (int)((float) mouseX - (mouseX % squareSize)) / squareSize;
                int gridY = (int)((float) mouseY - (mouseY % squareSize)) / squareSize;
                if (grid[gridX][gridY] == 1) {
                    grid[gridX][gridY] = 0;
                } else {
                    grid[gridX][gridY] = 1;
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
                    case KeyEvent.VK_ESCAPE: end = true; break;
                    case KeyEvent.VK_SPACE: run = !run; break;
                    case KeyEvent.VK_R: clear(); break;
                    case KeyEvent.VK_S: print(); break;
                    case KeyEvent.VK_L: load(); break;
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

        this.run();
    }

    private void clear() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = 0;
                nextGrid[i][j] = 0;
            }
        }
    }

    private void print() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[j][i] == 0) {
                    System.out.print("\u25A1");
                } else {
                    System.out.print("\u25A0");
                }
            }
            System.out.println();
        }
    }

    private void updateGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int count = 0;
                int up = j - 1 >= 0 ? j - 1 : gridSize - 1;
                int down = j + 1 < gridSize ? j + 1 : 0;
                int left = i - 1 >= 0 ? i - 1 : gridSize - 1;
                int right = i + 1 < gridSize ? i + 1 : 0;
                if (grid[left][up] == 1) count++;
                if (grid[i][up] == 1) count++;
                if (grid[right][up] == 1) count++;
                if (grid[left][j] == 1) count++;
                if (grid[right][j] == 1) count++;
                if (grid[left][down] == 1) count++;
                if (grid[i][down] == 1) count++;
                if (grid[right][down] == 1) count++;
                if (grid[i][j] == 0 && count == 3) nextGrid[i][j] = 1;
                else if (grid[i][j] == 1 && count < 2) nextGrid[i][j] = 0;
                else if (grid[i][j] == 1 && count >= 4) nextGrid[i][j] = 0;
                else nextGrid[i][j] = grid[i][j];
            }
        }
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = nextGrid[i][j];
                nextGrid[i][j] = 0;
            }
        }
    }

    private void load() {
//        String file = "□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■□□□□□□□□□□□□□□□□□□□□□□□□■□■□□□□□□□□□□□□□□□□□□□□□□□□□■■□□□□□□□□□■■□□□□□□□□□□□□■■□□□□■■□□□■■□□□□□□□□□□□□■■□□□□■■□□□□□□□□□□□□□□■□■□□□□□□□□□□□□□□□□□□□□□□■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□";
        String file = "□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■□□□□□□□□□□□□□□□□□□□□□□■□■□□□□□□□□□□□□□□□□□□□□□□□■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□";
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                char c = file.charAt(25 * i + j);
                if (c == '\u25A1') {
                    grid[j][i] = 0;
                } else {
                    grid[j][i] = 1;
                }
            }
        }
    }

    private void run() {
        while (!end) {
            if (run) {
                updateGrid();
            }
            panel.repaint();

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