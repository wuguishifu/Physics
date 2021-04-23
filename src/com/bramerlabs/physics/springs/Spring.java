package com.bramerlabs.physics.springs;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Spring {

    public Vector2f connectionPoint;
    public Mass mass;

    public float L0, ks, kd;

    public Spring(Vector2f connectionPoint, Mass mass, float L0, float ks, float kd) {
        this.connectionPoint = connectionPoint;
        this.mass = mass;
        this.L0 = L0;
        this.ks = ks;
        this.kd = kd;
    }

    public float calculateForce() {
        // calculate the spring force
        double spring = (Vector2f.length(Vector2f.subtract(connectionPoint, mass.position)) - L0) * ks;

        // calculate the dampening force
        double damp = Vector2f.dot(Vector2f.subtract(connectionPoint, mass.position), Vector2f.subtract(connectionPoint, mass.position)) * kd;

        // sum
        return (float) (spring + damp);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(172, 45, 45));
        g.drawLine((int) connectionPoint.x, (int) connectionPoint.y, (int) mass.position.x, (int) mass.position.y);
    }

}
