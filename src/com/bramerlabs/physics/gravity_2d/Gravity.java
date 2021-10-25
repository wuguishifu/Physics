package com.bramerlabs.physics.gravity_2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Gravity {

    private JFrame frame;
    private JPanel panel;

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Dimension windowSize = new Dimension(7 * screenSize.width / 8, 7 * screenSize.height / 8);

    int mouseX, mouseY;

    private boolean end = false;
    private boolean nextFrame = false;

    ArrayList<Particle> particles = new ArrayList<>();

    public static void main(String[] args) {
        new Gravity().init();
    }

    private void init() {
        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel(true) {

            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                for (Particle particle : particles) {
                    particle.paint(g);
                }
            }
        };
        panel.setPreferredSize(windowSize);
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    particles.add(new Particle(mouseX, mouseY, 1.0f));
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
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
                    end = true;
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                    curr = true;
                }
            }
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                    curr = false;
                }
            }
        };
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseMotionListener);
        frame.addKeyListener(keyListener);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        particles.add(new Particle(windowSize.width/2+20, windowSize.height/2, 0, -2, 1));
        particles.add(new Particle(windowSize.width/2-20, windowSize.height/2, 0,  2, 1));

        panel.repaint();
        this.run();
    }

    boolean curr = false;
    boolean run = true;
    @SuppressWarnings("BusyWait")
    private void run() {
        while (!end) {
            panel.repaint();
            if (curr || run) {
                ArrayList<Particle> particlesToRemove = new ArrayList<>();
                for (Particle particle : particles) {
                    particle.update(particles);
                    particle.move();
                    if (particle.position.x < -20 || particle.position.x > windowSize.width + 20 ||
                            particle.position.y < -20 || particle.position.y > windowSize.height + 20) {
                        particlesToRemove.add(particle);
                    }
                }
                for (Particle particle : particlesToRemove) {
                    particles.remove(particle);
                }
            }
            curr = false;
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
