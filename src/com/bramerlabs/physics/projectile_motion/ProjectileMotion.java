package com.bramerlabs.physics.projectile_motion;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class ProjectileMotion {

    private JFrame frame;
    private JPanel panel;

    protected static Dimension windowSize = new Dimension(1400, 600);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Vector2f windowPos = new Vector2f(screenSize.width/2f - windowSize.width/2f, screenSize.height/2f - windowSize.height/2f);

    int mouseX, mouseY;

    private boolean end = false;

    private Cannon cannon;
    boolean translatingCannon = false;
    boolean movingSlider = false;

    private VelocitySlider vSlider;

    private static final float gravity = 1f;

    public static void main(String[] args) {
        new ProjectileMotion().init();
    }

    public void init() {

        cannon = new Cannon(new Vector2f(0, windowSize.height), Vector2f.add(new Vector2f(0, windowSize.height), new Vector2f(100, -100)));

        vSlider = new VelocitySlider(new Vector2f(500, windowSize.height - 50), new Vector2f(windowSize.width - 500, windowSize.height - 50));

        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel(true) {

            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                cannon.paint(g, mouseX, mouseY);
                vSlider.paint(g);
                for (Bullet b : bullets) {
                    b.paint(g);
                }
            }
        };
        panel.setPreferredSize(windowSize);
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (cannon.inHeadBounds(mouseX, mouseY, cannon.radius) && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    translatingCannon = true;
                }
                if (vSlider.inSliderBounds(mouseX, mouseY) && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    movingSlider = true;
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    translatingCannon = false;
                    movingSlider = false;
                }
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (translatingCannon) {
                    cannon.rotate(mouseX, mouseY);
                }
                if (movingSlider) {
                    vSlider.moveSliderTo(mouseX);
                }
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
            }
        };
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {}
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        end = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        launch();
                }
            }
            public void keyReleased(KeyEvent keyEvent) {}
        };
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseMotionListener);
        frame.addKeyListener(keyListener);
        frame.add(panel);
        frame.pack();
        frame.move((int) windowPos.x, (int) windowPos.y);
        frame.setVisible(true);
        panel.repaint();

        this.run();
    }

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    public void launch() {
        bullets.add(new Bullet(cannon.getHead().x, cannon.getHead().y, Vector2f.normalize(Vector2f.subtract(cannon.getHead(), cannon.getTail()), vSlider.velocity * 35)));
    }

    @SuppressWarnings("BusyWait")
    public void run() {
        while (!end) {

            for (Bullet b : bullets) {
                if (b.needsUpdate) {
                    b.update(gravity);
                }
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
