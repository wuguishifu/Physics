package com.bramerlabs.math.fractals;

import java.awt.*;

public class Mandelbrot {

    public static void draw(Graphics g) {
        int scale = 10;
        int offsetX = 200 * scale, offsetY = 500 * scale;
        int width = Fractals.WIDTH * scale;
        int height = Fractals.HEIGHT * scale;
        int max = 1000;
        for (int i = 0; i < Fractals.WIDTH; i++) {
            for (int j = 0; j < Fractals.HEIGHT; j++) {
                float cRE = (j + offsetX - width / 2.0f) * 4.0f / width;
                float cIM = (i + offsetY - height / 2.0f) * 4.0f / width;
                float x = 0, y = 0;
                int iterations = 0;
                while (x * x + y * y < 4 && iterations < max) {
                    float x_new = x * x - y * y + cRE;
                    y = 2 * x * y + cIM;
                    x = x_new;
                    iterations++;
                }
                if (iterations < max) {
                    float color = (float) iterations / (float) max;
                    if (color > 0.5f) {
                        g.setColor(new Color((int) (color * 255), 255, (int) (color * 255)));
                    } else {
                        g.setColor(new Color(0, (int) (color * 255), 0));
                    }
                } else {
                    g.setColor(Color.BLACK);
                }
                g.drawLine(i, j, i, j);
            }
        }
    }
}