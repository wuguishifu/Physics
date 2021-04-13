package com.bramerlabs.physics.fluids;

import com.bramerlabs.engine.math.vector.Vector3f;

public class Main {

    // the data regarding the fluid screen
    int size = 16;
    float[][] density = new float[size][size];
    float[][] density_new = new float[size][size];
    Vector3f[][] velocity = new Vector3f[size][size];
    Vector3f[][] velocity_new = new Vector3f[size][size];

    float k = 0.5f;

    public static void main(String[] args) {

    }

    /**
     * calculates the density of a specific cell
     * @param x - the x value of the cell
     * @param y - the y value of the cell
     * @return - the density of that cell
     */
    public float calculateDensity(int x, int y) {
        return 0.0f;
    }

}
