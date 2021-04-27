package com.bramerlabs.math.marching_squares;

import javax.swing.*;
import java.awt.*;

public class MarchingSquares {

    static float radius = 5;

    static int width = 1600, height = 1200;

    static MarchingSquaresMesh mesh = new MarchingSquaresMesh(width, height);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Marching Cubes");
        JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                mesh.paint(g);
            }
        };
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(width, height));
        frame.add(panel);
        frame.getContentPane().setPreferredSize(new Dimension(width + 1, height + 1));
        frame.pack();
        frame.setVisible(true);

        panel.repaint();

    }
}