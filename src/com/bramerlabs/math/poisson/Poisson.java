package com.bramerlabs.math.poisson;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.util.ArrayList;

public class Poisson {

    public static ArrayList<Vector2f> generatePoints(float radius, Vector2f sampleRegionSize,
                                                     int numSamplesBeforeRejection) {

        ArrayList<Vector2f> points = new ArrayList<>();

        int sampleX = (int) Math.ceil(sampleRegionSize.x / (radius / Math.sqrt(2)));
        int sampleY = (int) Math.ceil(sampleRegionSize.y / (radius / Math.sqrt(2)));
        int[][] sampleArray = new int[sampleX][sampleY];

        return points;
    }

}
