package com.bramerlabs.math.partials;

public class ODE {
    private float[] bounds;
    private float[] ic;

    public float function(float x, float y, float z) {
        return -1;
    }

    public float[] bounds(float xL, float xR, float yL, float yR) {
        this.bounds = new float[]{xL, xR, yL, yR};
        return this.bounds;
    }

    public float[] ic(float x0, float y0) {
        this.ic = new float[]{x0, y0};
        return this.ic;
    }
}
