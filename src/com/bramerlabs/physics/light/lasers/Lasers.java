package com.bramerlabs.physics.light.lasers;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings({"deprecation"})
public class Lasers {

    protected final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected final static int width = screenSize.width, height = screenSize.height;
    private boolean done = false, nextFrame = false;
    private int mouseX, mouseY;
    private boolean mouseDown = false;

    private static boolean fullscreen = true;

    private ArrayList<Mirror> mirrors = new ArrayList<>();
    private LaserPointer pointer;
    private Mirror selectedMirror;
    private int selectedPosition;

    public static void main(String[] args) {
        new Lasers().run();
    }

    public void run() {
        // set up objects
        mirrors.add(new Mirror(new Vector2f((float) Math.random() * width, (float) Math.random() * height), new Vector2f((float) Math.random() * width, (float) Math.random() * height)));
        mirrors.add(new Mirror(new Vector2f((float) Math.random() * width, (float) Math.random() * height), new Vector2f((float) Math.random() * width, (float) Math.random() * height)));
        mirrors.add(new Mirror(new Vector2f((float) Math.random() * width, (float) Math.random() * height), new Vector2f((float) Math.random() * width, (float) Math.random() * height)));
        pointer = new LaserPointer(new Vector2f(400, 400), Vector2f.e1);

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
                pointer.paint(g, mirrors, pointer.position, pointer.direction);
                for (Mirror mirror : mirrors) {
                    mirror.paint(g);
                }
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = true;
                }
                for (Mirror mirror : mirrors) {
                    if (mirror.onPoint(new Vector2f(mouseEvent.getX(), mouseEvent.getY())) == 1 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        selectedPosition = 1;
                        selectedMirror = mirror;
                        break;
                    } else if (mirror.onPoint(new Vector2f(mouseEvent.getX(), mouseEvent.getY())) == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        selectedPosition = 2;
                        selectedMirror = mirror;
                        break;
                    }
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                }
                for (Mirror mirror : mirrors) {
                    if (mirror.onPoint(new Vector2f(mouseEvent.getX(), mouseEvent.getY())) == 1 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        selectedPosition = 0;
                        selectedMirror = null;
                        break;
                    } else if (mirror.onPoint(new Vector2f(mouseEvent.getX(), mouseEvent.getY())) == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        selectedPosition = 0;
                        selectedMirror = null;
                        break;
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
                if (mouseDown && selectedMirror != null) {
                    selectedMirror.movePoint(selectedPosition, new Vector2f(mouseX, mouseY));
                }
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseDown && selectedMirror != null) {
                    selectedMirror.movePoint(selectedPosition, new Vector2f(mouseX, mouseY));
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
