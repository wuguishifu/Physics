package com.bramerlabs.math.sine;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.math.ui.Slider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class Sine {

    private JFrame frame;
    private JPanel panel;

    protected static Dimension windowSize = new Dimension(800, 600);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Vector2f windowPos = new Vector2f(screenSize.width/2f - windowSize.width/2f, screenSize.height/2f - windowSize.height/2f);

    private int mouseX, mouseY;
    private boolean end = false;

    private final ArrayList<Slider> sliders = new ArrayList<>();
    private Slider movingSlider = null;

    private boolean state = true;

    public static void main(String[] args) {
        new Sine().init();
    }

    public void paintSine(Graphics g, float amplitude, float period, float offset) {
        g.setColor(Color.BLACK);
        float[] y = new float[windowSize.width];
        for (int x = 0; x < windowSize.width; x++) {
            if (state) {
                y[x] = windowSize.height * offset + (float) Math.sin(x / 100.f - period) * amplitude;
            } else {
                y[x] = windowSize.height * offset + (float) Math.tan(x / 100.f - period) * amplitude;
            }
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < y.length - 1; i++) {
            if ((!(y[i] < 0) || !(y[i + 1] > windowSize.height)) && (!(y[i] > windowSize.height) || !(y[i + 1] < 0))) {
                g2d.drawLine(i, (int) y[i], i + 1, (int) y[i + 1]);
            }
        }
        g2d.dispose();
    }

    public void init() {
        int size = 200;
        sliders.add(new Slider(new Vector2f(size, windowSize.height - 100), new Vector2f(windowSize.width - size,
                windowSize.height - 100), 100, "Amplitude", new Color(141, 23, 23), false)); // amplitude
        sliders.add(new Slider(new Vector2f(size, windowSize.height - 75), new Vector2f(windowSize.width - size,
                windowSize.height - 75), 3, "Period", new Color(23, 70, 141), false)); // period
        sliders.add(new Slider(new Vector2f(size, windowSize.height - 50), new Vector2f(windowSize.width - size,
                windowSize.height - 50), 3, "Offset", new Color(23, 141, 25), true)); // period

        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel(true) {

            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                for (Slider s : sliders) {
                    s.paint(g);
                }
                paintSine(g, sliders.get(0).value(), sliders.get(1).value(), sliders.get(2).value());
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
                        state = !state;
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
