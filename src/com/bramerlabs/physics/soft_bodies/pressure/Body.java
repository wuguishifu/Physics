package com.bramerlabs.physics.soft_bodies.pressure;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Body {

    public static float L0;
    public static final float initVectorLength = 100;
    public static final float ks = 10;
    public static final float kd = 0.1f;
    public static final float mass = 1f;
//    public static final float gravity = 0.5f;
    public static final float gravity = 0;

    public final float initialVolume;
    public static final float initialPressure = 1;

    public float dt = (float) Math.toRadians(30);
    public float t = 0;
    public float numPoints = 0;

    public ArrayList<MassPoint> massPoints;
    public ArrayList<Spring> springs;

    public Vector2f center;

    public Body(Vector2f position) {
        massPoints = new ArrayList<>();
        springs = new ArrayList<>();

        Vector2f v1 = Vector2f.normalize(new Vector2f((float) Math.cos(Math.toRadians( 0)), (float) Math.sin(Math.toRadians( 0))), initVectorLength);
        Vector2f v2 = Vector2f.normalize(new Vector2f((float) Math.cos(Math.toRadians(30)), (float) Math.sin(Math.toRadians(30))), initVectorLength);
        L0 = Vector2f.length(Vector2f.subtract(v1, v2));

        // add the mass points
        for (int i = 0; i < Math.toRadians(360) / dt; i++) {
            Vector2f v = Vector2f.normalize(new Vector2f((float) Math.cos(t), (float) Math.sin(t)), initVectorLength);
//            v = Vector2f.add(v, new Vector2f((float) (20 * Math.random()), (float) (20 * Math.random())));
            massPoints.add(new MassPoint(Vector2f.add(position, v), mass));
            t += dt;
            numPoints++;
        }

        // add the springs
        for (int i = 0; i < massPoints.size() - 1; i++) {
            springs.add(new Spring(massPoints.get(i), massPoints.get(i + 1), ks, L0, kd));
        }
        springs.add(new Spring(massPoints.get(0), massPoints.get(massPoints.size() - 1), ks, L0, kd));

        initialVolume = calculateVolume();
    }

    public void update() {
        float volume = calculateVolume();
        float pressure = (initialPressure * initialVolume) / volume;
        float pressureMagnitude = pressure / numPoints;
//        System.out.println(volume + ", " + pressure);
        // iterate over each point
        for (MassPoint mp : massPoints) {
            Vector2f direction = Vector2f.subtract(mp.position, center);
            Vector2f force = Vector2f.normalize(direction, pressureMagnitude);
            mp.updateForce(force);
        }

        for (Spring spring : springs) {
            float springForce = 0.01f * spring.calculateForce();
            Vector2f directionAB = Vector2f.normalize(Vector2f.subtract(spring.B.position, spring.A.position));
            Vector2f directionBA = Vector2f.normalize(Vector2f.subtract(spring.A.position, spring.B.position));
            Vector2f forceAB = Vector2f.scale(directionAB, springForce);
            Vector2f forceBA = Vector2f.scale(directionBA, springForce);
            spring.A.updateForce(forceAB);
            spring.B.updateForce(forceBA);
        }

        // update velocities
        for (MassPoint mp : massPoints) {
            mp.updateVelocity(gravity);
        }

        for (MassPoint mp : massPoints) {
            mp.updatePosition();
        }
    }

    public float calculateVolume() {
        // consider the 2d shape as a sum of triangles
        // calculate the center point
        Vector2f totalMassVelocity = new Vector2f(0, 0);
        float totalMass = 0;
        for (MassPoint mp : massPoints) {
            totalMassVelocity = Vector2f.add(totalMassVelocity, Vector2f.scale(mp.position, mp.mass));
            totalMass += mp.mass;
        }
        center = Vector2f.divide(totalMassVelocity, new Vector2f(totalMass));

        float totalVolume = 0;

        for (int i = 0; i < massPoints.size(); i++) {
            int i2 = i + 1;
            if (i == massPoints.size() - 1) {
                i2 = 0;
            }
            MassPoint m1 = massPoints.get(i);
            MassPoint m2 = massPoints.get(i2);
            Vector2f v1 = m1.position;
            Vector2f v2 = m2.position;

            // calculate the length of each side
            float s1 = Vector2f.length(Vector2f.subtract(v1, v2));
            float s2 = Vector2f.length(Vector2f.subtract(v2, center));
            float s3 = Vector2f.length(Vector2f.subtract(center, v1));

            // calculate the p value
            float p = (s1 + s2 + s3) / 2f;

            // calculate the area of the triangle using Heron's formula
            float triangleArea = (float) Math.sqrt(p * (p - s1) * (p - s2) * (p - s3));
            totalVolume += triangleArea;
        }

        return totalVolume / 1000;

    }

    // test method
    public void movePointsTowardsCenter() {
        for (MassPoint mp : massPoints) {
            Vector2f direction = Vector2f.subtract(center, mp.position);
            mp.position = Vector2f.add(mp.position, Vector2f.normalize(direction, 10.0f));
        }
    }

    // paints the body
    public void paint(Graphics g) {
        for (Spring spring : springs) {
            spring.paint(g);
        }
        for (MassPoint mp : massPoints) {
            mp.paint(g);
        }
        g.setColor(Color.BLUE);
        g.fillOval((int) (center.x - 10), (int) (center.y - 10), 20, 20);
    }

}
