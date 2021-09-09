package com.bramerlabs.math.color_blender;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.math.ui.Slider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class ColorBlenderMain {

    private JFrame frame;
    private JPanel panel;

    protected static Dimension windowSize = new Dimension(800, 600);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Vector2f windowPos = new Vector2f(screenSize.width/2f - windowSize.width/2f, screenSize.height/2f - windowSize.height/2f);

    private int mouseX, mouseY;
    private boolean end = false;

    private final ArrayList<Slider> sliders = new ArrayList<>();
    private Slider movingSlider = null;

    private ColorFader3C colorFader;

    public static void main(String[] args) {
        new ColorBlenderMain().init();
    }

    public void init() {
        colorFader = new ColorFader3C(Color.GREEN, Color.YELLOW, Color.RED, 0.5f);

        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        float sliderHeight = windowSize.height - 50;
        float sliderRight = windowSize.width - 100;
        sliders.add(new Slider(new Vector2f(100, sliderHeight), new Vector2f(sliderRight, sliderHeight), 10, "Midpoint", new Color(0, 0, 0), true));

        panel = new JPanel(true) {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                for (Slider slider : sliders) {
                    slider.paint(g);
                }
                for (int i = 0; i < 80; i++) {
                    Vector3f color = colorFader.getColor((i * 10)/800.0f);
                    g.setColor(new Color(Math.abs(color.x), Math.abs(color.y), Math.abs(color.z)));
                    g.fillRect(i * 10, 200, i * 10 + 10, 200);
                }
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
                    colorFader = new ColorFader3C(Color.GREEN, Color.YELLOW, Color.RED, sliders.get(0).value());
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
