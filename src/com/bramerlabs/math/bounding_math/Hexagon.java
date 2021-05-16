package com.bramerlabs.math.bounding_math;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;

import java.awt.*;

public class Hexagon {

    protected float radius = 300;
    protected float triangleHeight = (float) Math.sqrt(3) * 0.5f * radius;
    protected Vector2f position;

    protected static final double[] hexPointAngles = new double[]{Math.toRadians(30), Math.toRadians(90), Math.toRadians(150), Math.toRadians(210), Math.toRadians(270), Math.toRadians(330), Math.toRadians(30)};
    protected static final double[] hexFaceAngles = new double[]{-Math.toRadians(0), -Math.toRadians(60), -Math.toRadians(120), -Math.toRadians(180), -Math.toRadians(240), -Math.toRadians(300), -Math.toRadians(0)};
    protected static final float height = 0.2f;

    protected boolean isSelected = false;

    protected static Vector3f[] hexCornerPoints = new Vector3f[]{
            new Vector3f((float) Math.cos(hexPointAngles[6]), 0, (float) Math.sin(hexPointAngles[6])),
            new Vector3f((float) Math.cos(hexPointAngles[5]), 0, (float) Math.sin(hexPointAngles[5])),
            new Vector3f((float) Math.cos(hexPointAngles[4]), 0, (float) Math.sin(hexPointAngles[4])),
            new Vector3f((float) Math.cos(hexPointAngles[3]), 0, (float) Math.sin(hexPointAngles[3])),
            new Vector3f((float) Math.cos(hexPointAngles[2]), 0, (float) Math.sin(hexPointAngles[2])),
            new Vector3f((float) Math.cos(hexPointAngles[1]), 0, (float) Math.sin(hexPointAngles[1])),
            new Vector3f((float) Math.cos(hexPointAngles[0]), 0, (float) Math.sin(hexPointAngles[0])),
    };

    public Hexagon(Vector2f position) {
        this.position = position;
    }

    public boolean checkCollision(float mouseX, float mouseY) {
        Vector2f mouseLoc = new Vector2f(mouseX, mouseY);
        return !(Vector2f.distance(mouseLoc, position) < triangleHeight);
    }

    public void paint(Graphics g) {
        g.setColor(isSelected ? Color.RED : Color.BLUE);
        for (int i = 0; i < hexCornerPoints.length - 1; i++) {
            Vector2f point1 = Vector2f.add(Vector2f.normalize(new Vector2f(hexCornerPoints[i].x, hexCornerPoints[i].z), radius), position);
            Vector2f point2 = Vector2f.add(Vector2f.normalize(new Vector2f(hexCornerPoints[i + 1].x, hexCornerPoints[i + 1].z), radius), position);
            g.drawLine((int) point1.x, (int) point1.y, (int) point2.x, (int) point2.y);
        }
        g.drawOval((int) (position.x - triangleHeight), (int) (position.y - triangleHeight), (int) triangleHeight * 2, (int) triangleHeight * 2);
    }

}
