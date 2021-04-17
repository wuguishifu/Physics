package com.bramerlabs.physics.soft_bodies;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Body {

    public static final int L0 = 50;
    public static final float ks = 10f;
    public static final float kd = 0.1f;

    public int numPoints = 6;

    public MassPoint[][] massPoints;
    public ArrayList<Spring> springs;

    // generates a body with a list of mass points and springs
    // uses a square cross hatch spring pattern in order to retain the shape of the body
    public Body(Vector2f position) {
        massPoints = new MassPoint[numPoints][numPoints];
        springs = new ArrayList<>();

        // generate the mass points
        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < numPoints; j++) {
                massPoints[i][j] = new MassPoint(new Vector2f((float) (i * L0 + position.x + 4 * Math.random()), (float) (j * L0 + position.y + 4 * Math.random())), 1.0f);
                massPoints[i][j] = new MassPoint(new Vector2f(i * L0 + position.x, j * L0 + position.y), 1.0f);
            }
        }

        // generate the springs
        // horizontal springs
        for (int i = 0; i < numPoints - 1; i++) {
            for (int j = 0; j < numPoints; j++) {
                springs.add(new Spring(massPoints[i][j], massPoints[i + 1][j], ks, L0, kd));
            }
        }
        // vertical springs
        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < numPoints - 1; j++) {
                springs.add(new Spring(massPoints[i][j], massPoints[i][j + 1], ks, L0, kd));
            }
        }
        // negative diagonal springs
        for (int i = 0; i < numPoints - 1; i++) {
            for (int j = 0; j < numPoints - 1; j++) {
                springs.add(new Spring(massPoints[i][j], massPoints[i + 1][j + 1], ks, (L0 * Math.sqrt(2)), kd));
            }
        }
        // positive diagonal springs
        for (int i = 1; i < numPoints; i++) {
            for (int j = 0; j < numPoints - 1; j++) {
                springs.add(new Spring(massPoints[i][j], massPoints[i - 1][j + 1], ks, (L0 * Math.sqrt(2)), kd));
            }
        }

    }

    // updates the body
    public void update(ArrayList<Object> objects) {
        for (Spring spring : springs) {
            float springForce = 0.01f * spring.calculateForce();
            Vector2f directionAB = Vector2f.normalize(Vector2f.subtract(spring.B.position, spring.A.position));
            Vector2f directionBA = Vector2f.normalize(Vector2f.subtract(spring.A.position, spring.B.position));
            Vector2f forceAB = Vector2f.scale(directionAB, springForce);
            Vector2f forceBA = Vector2f.scale(directionBA, springForce);
            spring.A.update(forceAB);
            spring.B.update(forceBA);
        }

        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < numPoints; j++) {
                for (Object object : objects) {
                    Vector2f[] collisionLine = object.collides(massPoints[i][j].position);
                    if (collisionLine != null) {
                        Vector2f line = Vector2f.subtract(collisionLine[1], collisionLine[0]);
                        float a = Vector2f.dot(massPoints[i][j].position, Vector2f.normalize(line));
                        Vector2f aVec = Vector2f.normalize(line, a);
//                        Vector2f direction = Vector2f.subtract(line, massPoints[i][j].position);
//                        System.out.println(direction);
                        Vector2f normal = Vector2f.normalize(Vector2f.subtract(aVec, massPoints[i][j].position));
                        massPoints[i][j].velocity = Vector2f.subtract(massPoints[i][j].velocity, Vector2f.scale(normal, 2 * Vector2f.dot(massPoints[i][j].velocity, normal)));
//                        massPoints[i][j].position = direction;
                    }
                }
            }
        }
        for (int i = 0; i < numPoints; i++) { // check collision between mass points
            for (int j = 0; j < numPoints; j++) {
                if (i > 0) {
                    checkPointCollision(massPoints[i][j], massPoints[i - 1][j]);
                }
                if (j > 0) {
                    checkPointCollision(massPoints[i][j], massPoints[i][j - 1]);
                }
                if (i + 1 < numPoints) {
                    checkPointCollision(massPoints[i][j], massPoints[i + 1][j]);
                }
                if (j + 1 < numPoints) {
                    checkPointCollision(massPoints[i][j], massPoints[i][j + 1]);
                }
                if (i > 0 && j > 0) {
                    checkPointCollision(massPoints[i][j], massPoints[i - 1][j - 1]);
                }
                if (i > 0 && j + 1 < numPoints) {
                    checkPointCollision(massPoints[i][j], massPoints[i - 1][j + 1]);
                }
                if (i + 1 < numPoints && j > 0) {
                    checkPointCollision(massPoints[i][j], massPoints[i + 1][j - 1]);
                }
                if (i + 1 < numPoints && j + 1 < numPoints) {
                    checkPointCollision(massPoints[i][j], massPoints[i + 1][j + 1]);
                }
            }
        }
    }

    // checks collision between two mass points checking A against B means move A if it intersects with B
    public void checkPointCollision(MassPoint A, MassPoint B) {
        if (A.position.equals(B.position)) {
            return;
        }
        Vector2f direction = Vector2f.subtract(A.position, B.position);
        if (Vector2f.length(direction) < 2 * MassPoint.radius) {
            direction.normalize(2 * MassPoint.radius);
            A.position = Vector2f.add(B.position, direction);
            Vector2f normal = Vector2f.normalize(direction);
            A.velocity = Vector2f.subtract(A.velocity, Vector2f.scale(normal, 2 * Vector2f.dot(A.velocity, normal)));
        }
    }

    // paints the body
    public void paint(Graphics g) {
        for (Spring spring : springs) {
            spring.paint(g);
        }
        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < numPoints; j++) {
                massPoints[i][j].paint(g);
            }
        }
    }
}
