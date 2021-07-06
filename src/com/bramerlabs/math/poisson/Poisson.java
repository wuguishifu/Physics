package com.bramerlabs.math.poisson;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.util.ArrayList;

public class Poisson {

    public static ArrayList<Vector2f> generatePoints(float radius, Vector2f sampleRegionSize,
                                                     int numSamplesBeforeRejection) {

        ArrayList<Vector2f> spawnPoints = new ArrayList<>();
        ArrayList<Vector2f> points = new ArrayList<>();

        float cellSize = radius / (float) Math.sqrt(2);
        int sampleX = (int) Math.ceil(sampleRegionSize.x / cellSize);
        int sampleY = (int) Math.ceil(sampleRegionSize.y / cellSize);
        int[][] sampleArray = new int[sampleX][sampleY];
        for (int i = 0; i < sampleX; i++) {
            for (int j = 0; j < sampleY; j++) {
                sampleArray[i][j] = 0;
            }
        }

        spawnPoints.add(new Vector2f(sampleRegionSize.x/2, sampleRegionSize.y/2));
        while (spawnPoints.size() > 0) {
            int spawnIndex = (int) (spawnPoints.size() * Math.random());
            Vector2f spawnCenter = spawnPoints.get(spawnIndex);
            boolean candidateAccepted = false;

            for (int i = 0; i < numSamplesBeforeRejection; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                Vector2f direction = new Vector2f((float) Math.sin(angle), (float) Math.cos(angle));
                float distance = (float) (Math.random() * radius + radius);
                Vector2f candidate = Vector2f.add(spawnCenter, Vector2f.scale(direction, distance));

                if (isValid(candidate, sampleRegionSize, cellSize, radius, points, sampleArray, sampleX, sampleY)) {
                    points.add(candidate);
                    spawnPoints.add(candidate);
                    sampleArray[(int) (candidate.x/cellSize)][(int) (candidate.y/cellSize)] = points.size();
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

    static boolean isValid(Vector2f candidate, Vector2f sampleRegionSize, float cellSize, float radius,
                           ArrayList<Vector2f> points, int[][] grid, int sampleX, int sampleY) {
        if (candidate.x < 0 || candidate.x >= sampleRegionSize.x ||
                candidate.y < 0 || candidate.y >= sampleRegionSize.y) {
            return false;
        }
        int cellX = (int) (candidate.x/cellSize);
        int cellY = (int) (candidate.y/cellSize);
        int searchStartX = Math.max(0, cellX - 2);
        int searchEndX = Math.min(cellX + 2, sampleX - 1);
        int searchStartY = Math.max(0, cellY - 2);
        int searchEndY = Math.min(cellY + 2, sampleY - 1);

        for (int x = searchStartX; x <= searchEndX; x++) {
            for (int y = searchStartY; y <= searchEndY; y++) {
                int pointIndex = grid[x][y] - 1;
                if (pointIndex >= 0) {
                    Vector2f distance = Vector2f.subtract(candidate, points.get(pointIndex));
                    float squareDistance = distance.x * distance.x + distance.y * distance.y;
                    if (squareDistance < radius * radius) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
