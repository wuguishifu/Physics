package com.bramerlabs.physics.soft_bodies;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Spring {

    // the mass points
    public MassPoint A, B;

    // stiffness
    float ks;

    // rest length
    float L0;

    // damping factor
    float kd;

    // default constructor
    public Spring(MassPoint A, MassPoint B, float ks, float L0, float kd) {
        this.A = A;
        this.B = B;
        this.ks = ks;
        this.L0 = L0;
        this.kd = kd;
    }

    // calculate the magnitude of force between the two mass points
    public float calculateForce() {
        // calculate the spring force
        float spring = (Vector2f.length(Vector2f.subtract(B.position, A.position)) - L0) * ks;

        // calculate the dampening force
        float damp = kd * Vector2f.length(Vector2f.multiply(Vector2f.subtract(B.position, A.position), Vector2f.subtract(B.velocity, A.velocity)));

        // sum
        return spring + damp;
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine((int) A.position.x, (int) A.position.y, (int) B.position.x, (int) B.position.y);
    }
}