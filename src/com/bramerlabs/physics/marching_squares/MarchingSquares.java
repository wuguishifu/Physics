package com.bramerlabs.physics.marching_squares;

import java.awt.*;

public class MarchingSquares {

    int width, height;

    int squareSize = 1;

    int[] x, y;

    int sx, sy;

    int[][] cases;

    public MarchingSquares(int width, int height) {
        this.width = width;
        this.height = height;

        x = new int[width / squareSize + 1];
        y = new int[height / squareSize + 1];
        sx = x.length - 1;
        sy = y.length - 1;

        for (int i = 0; i < x.length; i++) {
            x[i] = squareSize * i;
        }
        System.out.println();
        for (int i = 0; i < y.length; i++) {
            y[i] = squareSize * i;
        }

        cases = new int[sy][sx]; // draw order y * sy + x

        calculateMesh();
    }

    int r = 200;

    public boolean inFunc(float x, float y) {
        x -= width/2.;
        y -= height/2.;
//        x /= 50;
//        y /= 50;
        y = -y;
//        return x * x + y * y < 300 * 200 && 2*Math.abs(y)-x>0 && (x-50)*(x-50)+(y+150)*(y+150)>300; // pac man
        return x*x*Math.sin(0.0001*x*x)-y*y < 0; // weird sin
//        return Math.pow(x*x+y*y-1,3)-x*x*y*y*y<0; // heart
//        return x*x+y*y<4*4;
    }

    public void calculateMesh() {
        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                int x1 = x[i];
                int x2 = x[i+1];
                int y1 = y[j];
                int y2 = y[j+1];
                // quadrants
                int q1 = inFunc(x2, y1) ? 1 : 0;
                int q2 = inFunc(x1, y1) ? 10 : 0;
                int q3 = inFunc(x1, y2) ? 100 : 0;
                int q4 = inFunc(x2, y2) ? 1000 : 0;
                cases[j][i] = q1 + q2 + q3 + q4;
            }
        }
    }


    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setStroke(new BasicStroke(2));
//        g.drawOval(width/2-8*25, height/2-8*25, 16*25, 16*25);
        for (int item : x) {
            for (int value : y) {
                if (inFunc(item, value)) {
                    g.setColor(Color.RED);
                }
//                g.drawLine(item, value, item, value);
                g.setColor(Color.BLACK);
            }
        }

        // halfway x: (x[i+1] + x[i])/2
        // halfway y: (y[j+1] + y[j])/2
        g.setColor(Color.RED);
        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                switch (cases[j][i]) {
                    case 0:
                    case 1111:
                        break;
                    case 1000:
                    case 111:
                        g.drawLine(x[i+1], (y[j+1] + y[j])/2, (x[i+1] + x[i])/2, y[j+1]);
                        break;
                    case 100:
                    case 1011:
                        g.drawLine(x[i], (y[j+1] + y[j])/2, (x[i+1] + x[i])/2, y[j+1]);
                        break;
                    case 1100:
                    case 11:
                        g.drawLine(x[i], (y[j+1] + y[j])/2, x[i+1], (y[j+1] + y[j])/2);
                        break;
                    case 10:
                    case 1101:
                        g.drawLine(x[i], (y[j+1] + y[j])/2, (x[i+1] + x[i])/2, y[j]);
                        break;
                    case 1010:
                        g.drawLine((x[i+1] + x[i])/2, y[j], x[i+1], (y[j+1] + y[j])/2);
                        g.drawLine(x[i], (y[j+1] + y[j])/2, (x[i+1] + x[i])/2, y[j+1]);
                        break;
                    case 110:
                    case 1001:
                        g.drawLine((x[i+1] + x[i])/2, y[j], (x[i+1] + x[i])/2, y[j+1]);
                        break;
                    case 1110:
                    case 1:
                        g.drawLine((x[i+1] + x[i])/2, y[j], x[i+1], (y[j+1] + y[j])/2);
                        break;
                    case 101:
                        g.drawLine(x[i], (y[j+1] + y[j])/2, (x[i+1] + x[i])/2, y[i]);
                        g.drawLine((x[i+1] + x[i])/2, y[j+1], x[i], (y[j+1] + y[j])/2);
                        break;
                    default:
                        g.drawLine(x[i], y[j], x[i+1], y[j+1]);
                        g.drawLine(x[i+1], y[j], x[i], y[j+1]);
                        break;
                }
//                System.out.println((x[i]-height/2) + ", " + (y[j]-height/2) + ", " + cases[j * sy + i]);
            }
        }
        g.setColor(Color.BLACK);
    }

}