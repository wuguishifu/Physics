package com.bramerlabs.math.poisson;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.math.ui.Slider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class PoissonRunner {

    private JFrame frame;
    private JPanel panel;

    protected static Dimension windowSize = new Dimension(800, 800);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Vector2f windowPos = new Vector2f(screenSize.width/2f - windowSize.width/2f, screenSize.height/2f - windowSize.height/2f);

    private int mouseX, mouseY;
    private boolean end = false;

    private final ArrayList<Slider> sliders = new ArrayList<>();
    private Slider movingSlider = null;

    public static void main(String[] args) {
        new PoissonRunner().init();
    }

    public ArrayList<Node> nodes = new ArrayList<>();

    public float radius = 10;
    public Vector2f regionSize = new Vector2f(windowSize.width, windowSize.height);
    public int numSamplesBeforeRejection = 30;

    public void calc() {
        ArrayList<Vector2f> points = Poisson.generatePoints(15, regionSize, numSamplesBeforeRejection);
        for (Vector2f p : points) {
            nodes.add(new Node(p));
            System.out.println(p);
        }
    }

    public void drawNodes(Graphics g) {
        for (Node node : nodes) {
            node.paint(g);
        }
    }

    public void init() {
        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        calc();

        panel = new JPanel(true) {

            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                drawNodes(g);
            }
        };
        panel.setPreferredSize(windowSize);
        MouseListener mouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                for (Slider s : sliders) {
                    if (s.inSliderBounds(mouseX, mouseY) && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        movingSlider = s;
                        break;
                    }
                }
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    movingSlider = null;
                }
            }
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
                if (movingSlider != null) {
                    movingSlider.moveSliderTo(mouseX, mouseY);
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
                        break;
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

    @SuppressWarnings("BusyWait")
    public void run() {
        while (!end) {

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
