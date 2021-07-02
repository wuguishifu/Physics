package com.bramerlabs.math.poisson;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.util.ArrayList;

public class Poisson {

    public static ArrayList<Vector2f> generatePoints(float radius, Vector2f sampleRegionSize,
                                                     int numSamplesBeforeRejection) {

        ArrayList<Vector2f> points = new ArrayList<>();
        ArrayList<Vector2f> spawnPoints = new ArrayList<>();

        spawnPoints.add(new Vector2f(sampleRegionSize.x/2, sampleRegionSize.y/2));
        while (spawnPoints.size() > 0) {
            int spawnIndex = (int) (spawnPoints.size() * Math.random());
            Vector2f spawnCenter = spawnPoints.get(spawnIndex);
            boolean candidateAccepted = false;

            for (int i = 0; i < numSamplesBeforeRejection; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                Vector2f dir = new Vector2f((float) Math.sin(angle), (float) Math.cos(angle));
                Vector2f candidate = Vector2f.add(spawnCenter, Vector2f.scale(dir,
                        (float) (200 * Math.random())));
                if (isValid(candidate, sampleRegionSize, radius, points)) {
                    points.add(candidate);
                    spawnPoints.add(candidate);
                    candidateAccepted = true;
                    break;
                }
            }
            if (!candidateAccepted) {
                spawnPoints.remove(spawnIndex);
            }
        }
        return points;
    }

    private static boolean isValid(Vector2f candidate, Vector2f sampleRegionSize, float radius,
                                   ArrayList<Vector2f> points) {
        if (candidate.x >= 0 && candidate.x < sampleRegionSize.x &&
                candidate.y >= 0 && candidate.y < sampleRegionSize.y) {

            for (Vector2f test : points) {
                if (Vector2f.distance(test, candidate) < 2 * radius) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
