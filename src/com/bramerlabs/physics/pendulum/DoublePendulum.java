package com.bramerlabs.physics.pendulum;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;

public class DoublePendulum {

    public Bob b1, b2;
    public Vector2f connection;

    float g = 10.0f;
    float dt = 0.25f;

    // initial angles
    float t1, t2;

    // masses
    float m1, m2;

    // initial angular velocities
    float w1, w2;

    // length of rods
    float L1, L2;

    public Color armColor = new Color(0, 0, 0);
    public Color bobColor;

    public ArrayList<float[]> previousPositions = new ArrayList<>();
    int numPrevious = 255;

    public DoublePendulum(float L1, float L2, float t1, float t2, float m1, float m2, Vector2f connection, Color color) {
        this.t1 = t1;
        this.t2 = t2;
        this.L1 = L1;
        this.L2 = L2;
        this.m1 = m1;
        this.m2 = m2;
        this.connection = connection;
        this.bobColor = color;

//        L1 = Vector2f.length(Vector2f.subtract(new Vector2f(b1.x, b1.y), connection));
//        L2 = Vector2f.length(Vector2f.subtract(new Vector2f(b2.x, b2.y), new Vector2f(b1.x, b1.y)));
//
//        t1 = Vector2f.dot(new Vector2f(0, -1), Vector2f.subtract(connection, new Vector2f(b1.x, b1.y))) / Vector2f.length(Vector2f.subtract(connection, new Vector2f(b1.x, b1.y)));
//        t1 = Vector2f.dot(new Vector2f(0, -1), Vector2f.subtract(new Vector2f(b1.x, b1.y), new Vector2f(b2.x, b2.y))) / Vector2f.length(Vector2f.subtract(new Vector2f(b1.x, b1.y), new Vector2f(b2.x, b2.y)));

        w1 = 0;
        w2 = 0;

        Vector2f v1 = new Vector2f((float) Math.sin(t1), (float) Math.cos(t1));
        Vector2f v2 = new Vector2f((float) Math.sin(t2), (float) Math.cos(t2));

        v1 = Vector2f.normalize(v1, L1);
        v2 = Vector2f.normalize(v2, L2);

        v1 = Vector2f.add(connection, v1);
        v2 = Vector2f.add(v1, v2);

        b1 = new Bob(v1.x, v1.y, m1);
        b2 = new Bob(v2.x, v2.y, m2);
    }

    public void update() {

        timeStep(dt);

        Vector2f v1 = new Vector2f((float) Math.sin(t1), (float) Math.cos(t1));
        Vector2f v2 = new Vector2f((float) Math.sin(t2), (float) Math.cos(t2));

        v1 = Vector2f.normalize(v1, L1);
        v2 = Vector2f.normalize(v2, L2);

        v1 = Vector2f.add(connection, v1);
        v2 = Vector2f.add(v1, v2);

        b1.x = v1.x;
        b1.y = v1.y;

        b2.x = v2.x;
        b2.y = v2.y;

        previousPositions.add(0, new float[]{b2.x, b2.y});
        if (previousPositions.size() > numPrevious) {
            previousPositions.remove(previousPositions.size() - 1);
        }
    }

    public float potentialEnergy() {
        // compute the height of each bob
        float y1 = (float) (-L1 * Math.cos(t1));
        float y2 = (float) (y1 - L2 * Math.cos(t2));

        return m1 * g * y1 + m2 * g * y2;
    }

    public float kineticEnergy() {
        float K1 = (float) (0.5 * m1 * (L1 * w1 * L1 * w1));
        float K2 = (float) (0.5 * m2 * ((L1 * w1 * L1 * w1) + 2 * L1 * L2 * w1 * w2 * Math.cos(t1 - t2))); // questionable
        return K1 + K2;
    }

    public float mechanicalEnergy() {
        return potentialEnergy() + kineticEnergy();
    }

    public float[] langrangeRHS(float t1, float t2, float w1, float w2) {
        float a1 = (float) ((L2 / L1) * (m2 / (m1 + m2)) * Math.cos(t1 - t2));
        float a2 = (float) ((L1 / L2) * Math.cos(t1 - t2));

        float f1 = (float) (-(L2 / L1) * (m2 / (m1 + m2)) * (w2 * w2) * Math.sin(t1 - t2) - (g / L1) * Math.sin(t1));
        float f2 = (float) ((L1 / L2) * (w1 * w1) * Math.sin(t1 - t2) - (g / L2) * Math.sin(t2));

        float g1 = (f1 - a1 * f2) / (1 - a1 * a2);
        float g2 = (f2 - a2 * f1) / (1 - a1 * a2);

        return new float[]{w1, w2, g1, g2};
    }

    public void timeStep(float dt) {

        float[] y = {t1, t2, w1, w2};

        float[] k1 = this.langrangeRHS(y[0], y[1], y[2], y[3]);
        float[] k2 = this.langrangeRHS(
                y[0] + dt * k1[0] / 2,
                y[1] + dt * k1[1] / 2,
                y[2] + dt * k1[2] / 2,
                y[3] + dt * k1[3] / 2
        );
        float[] k3 = this.langrangeRHS(
                y[0] + dt * k2[0] / 2,
                y[1] + dt * k2[1] / 2,
                y[2] + dt * k2[2] / 2,
                y[3] + dt * k2[3] / 2
        );
        float[] k4 = this.langrangeRHS(
                y[0] + dt * k3[0],
                y[1] + dt * k3[1],
                y[2] + dt * k3[2],
                y[3] + dt * k3[3]
        );
        float[] R = {
                1.0f / 6.0f * dt * (k1[0] + 2.0f * k2[0] + 2.0f * k3[0] + k4[0]),
                1.0f / 6.0f * dt * (k1[1] + 2.0f * k2[1] + 2.0f * k3[1] + k4[1]),
                1.0f / 6.0f * dt * (k1[2] + 2.0f * k2[2] + 2.0f * k3[2] + k4[2]),
                1.0f / 6.0f * dt * (k1[3] + 2.0f * k2[3] + 2.0f * k3[3] + k4[3])
        };
        this.t1 += R[0];
        this.t2 += R[1];
        this.w1 += R[2];
        this.w2 += R[3];
    }

    public void paint(Graphics g) {
        g.setColor(armColor);
        g.drawLine((int) connection.x, (int) connection.y, (int) b1.x, (int) b1.y);
        g.drawLine((int) b1.x, (int) b1.y, (int) b2.x, (int) b2.y);
        g.setColor(bobColor);
        b1.paint(g);
        b2.paint(g);
        for (int i = 0; i < previousPositions.size() - 1; i++) {
            g.setColor(new Color(bobColor.getRed(), bobColor.getGreen(), bobColor.getBlue(), 255 - i));
            g.drawLine((int) previousPositions.get(i + 1)[0], (int) previousPositions.get(i + 1)[1],
                       (int) previousPositions.get(i)[0], (int) previousPositions.get(i)[1]);
        }
    }

}