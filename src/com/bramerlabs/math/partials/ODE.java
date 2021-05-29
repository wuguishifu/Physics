package com.bramerlabs.math.partials;

public class ODE {
    private float[] bounds;

    public float function(float x, float y, float z) {
        return -1;
    }

    public float[] bounds(float xL, float xR, float yL, float yR) {
        this.bounds = new float[]{xL, xR, yL, yR};
        return this.bounds;
    }
}
