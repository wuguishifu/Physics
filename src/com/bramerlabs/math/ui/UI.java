package com.bramerlabs.math.ui;

import com.bramerlabs.engine.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class UI {

    private JFrame frame;
    private JPanel panel;

    protected static Dimension windowSize = new Dimension(800, 600);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Vector2f windowPos = new Vector2f(screenSize.width/2f - windowSize.width/2f, screenSize.height/2f - windowSize.height/2f);

    private int mouseX, mouseY;
    private boolean end = false;

    private final ArrayList<Slider> sliders = new ArrayList<>();
    private Slider movingSlider = null;

    private final ArrayList<Switch> switches = new ArrayList<>();
    private final ArrayList<ToggleButton> toggleButtons = new ArrayList<>();

    public static void main(String[] args) {
        new UI().init();
    }

    public void init() {
        sliders.add(new Slider(new Vector2f(100, 200), new Vector2f(200, 200),
                20, "label", new Color(62, 160, 75), false)); // amplitude
        switches.add(new Switch(300, 300, 40, 15, false, new Color(215, 73, 73)));
        toggleButtons.add(new ToggleButton(300, 100, 15, 15, false, new Color(34, 78, 177)));

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
                for (Switch s : switches) {
                    s.paint(g);
                }
                for (ToggleButton t : toggleButtons) {
                    t.paint(g);
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
                for (Switch s : switches) {
                    if (s.inBounds(mouseX, mouseY) && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        s.toggle();
                        break;
                    }
                }
                for (ToggleButton t : toggleButtons) {
                    if (t.inBounds(mouseX, mouseY) && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        t.toggle();
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
