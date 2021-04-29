package com.bramerlabs.math.dijkstra;

import com.bramerlabs.engine.math.vector.Vector2f;

import java.awt.*;

public class Grid {

    public int cellsHorizontal, cellsVertical, cellSize;
    public int[][] cells;
    public int[][] tempCells;
    public int[][] bfs;
    public int[][] path;

    public boolean containsStart = false, containsEnd = false;
    public int[] start, end;

    public final float stepSize;

    public Grid(int cellsHorizontal, int cellsVertical, int cellSize) {
        this.cellsHorizontal = cellsHorizontal;
        this.cellsVertical = cellsVertical;
        this.cellSize = cellSize;
        this.stepSize = 0.1f;

        this.cells = new int[cellsHorizontal][cellsVertical];
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                cells[i][j] = -1;
            }
        }

        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                cells[i][j] = Math.random() < 0.2 ? 1 : -1;
            }
        }

        this.tempCells = new int[cellsHorizontal][cellsVertical];
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                tempCells[i][j] = -1;
            }
        }

        this.bfs = new int[cellsHorizontal][cellsVertical];
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                bfs[i][j] = cellsVertical * cellsHorizontal + 1;
            }
        }

        this.path = new int[cellsHorizontal][cellsVertical];
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                path[i][j] = cellsVertical * cellsHorizontal + 1;
            }
        }

        start = new int[2];
        end = new int[2];
    }

    public void findPath(boolean useCorners) {
        this.bfs = new int[cellsHorizontal][cellsVertical];
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                bfs[i][j] = cellsVertical * cellsHorizontal + 1;
            }
        }

        this.path = new int[cellsHorizontal][cellsVertical];
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                path[i][j] = cellsVertical * cellsHorizontal + 1;
            }
        }

        clearCells(4);

        if (!(containsEnd && containsStart)) {
            return;
        }

        // do the algorithm here
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                if (cells[i][j] == 1) {
                    bfs[i][j] = 1000;
                }
            }
        }
        bfs(start[0], start[1], 0);
        addSmallestDelta(end[0], end[1], useCorners);
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                if (path[i][j] == 1 && cells[i][j] == -1) {
                    cells[i][j] = 4;
                }
            }
        }

    }

    private void addSmallestDelta(int curX, int curY, boolean useCorners) {
        if (curX == start[0] && curY == start[1]) {
            return;
        }
        if (curX - 1 >= 0 && curX + 1 < cellsHorizontal && curY - 1 >= 0 && curY + 1 <= cellsVertical) {
            int smallX = -1;
            int smallY = -1;
            int smallestValue = cellsHorizontal * cellsVertical;
            if (bfs[curX - 1][curY] < smallestValue) {
                smallX = curX - 1;
                smallY = curY;
                smallestValue = bfs[curX - 1][curY];
            }
            if (bfs[curX][curY - 1] < smallestValue) {
                smallX = curX;
                smallY = curY - 1;
                smallestValue = bfs[curX][curY - 1];
            }
            if (bfs[curX + 1][curY] < smallestValue) {
                smallX = curX + 1;
                smallY = curY;
                smallestValue = bfs[curX + 1][curY];
            }
            if (bfs[curX][curY + 1] < smallestValue) {
                smallX = curX;
                smallY = curY + 1;
                smallestValue = bfs[curX][curY + 1];
            }
            if (useCorners) {
                if (bfs[curX - 1][curY - 1] < smallestValue) {
                    smallX = curX - 1;
                    smallY = curY - 1;
                    smallestValue = bfs[curX - 1][curY - 1];
                }
                if (bfs[curX + 1][curY - 1] < smallestValue) {
                    smallX = curX + 1;
                    smallY = curY - 1;
                    smallestValue = bfs[curX + 1][curY - 1];
                }
                if (bfs[curX + 1][curY + 1] < smallestValue) {
                    smallX = curX + 1;
                    smallY = curY + 1;
                    smallestValue = bfs[curX + 1][curY + 1];
                }
                if (bfs[curX - 1][curY + 1] < smallestValue) {
                    smallX = curX - 1;
                    smallY = curY + 1;
                }
            }
            path[smallX][smallY] = 1;
            addSmallestDelta(smallX, smallY, useCorners);
        }
    }

    private void bfs(int curX, int curY, int depth) {
        if (curX >= cellsHorizontal || curX < 0 || curY < 0 || curY >= cellsVertical) {
            return;
        }
        if (bfs[curX][curY] == 1000) {
            return;
        }
        if (bfs[curX][curY] != -1) {
            if (bfs[curX][curY] > depth) {
                bfs[curX][curY] = depth;
            } else {
                return;
            }
        }
        bfs(curX + 1, curY, depth + 1);
        bfs(curX, curY + 1, depth + 1);
        bfs(curX - 1, curY, depth + 1);
        bfs(curX, curY - 1, depth + 1);
    }

    public void setTempCellsSquare(Vector2f p1, Vector2f p2) {
        Vector2f p12 = new Vector2f(p1.x, p2.y);
        Vector2f p21 = new Vector2f(p2.x, p1.y);
        clearTempCells();
        setTempCells(p1, p21, false);
        setTempCells(p1, p12, false);
        setTempCells(p2, p21, false);
        setTempCells(p2, p12, false);
    }

    public void setTempCells(Vector2f p1, Vector2f p2, boolean clear) {
        p1.x = (float) (cellSize * (int) (p1.x / cellSize)) + cellSize / 2f;
        p1.y = (float) (cellSize * (int) (p1.y / cellSize)) + cellSize / 2f;
        p2.x = (float) (cellSize * (int) (p2.x / cellSize)) + cellSize / 2f;
        p2.y = (float) (cellSize * (int) (p2.y / cellSize)) + cellSize / 2f;
        if (clear) {
            clearTempCells();
        }
        Vector2f incidentVector = p1;
        Vector2f step = Vector2f.normalize(Vector2f.subtract(p2, p1), stepSize);
        float maxDistance = Vector2f.distance(p1, p2);
        float currentDistance = 0;
        while (currentDistance < maxDistance) {
            currentDistance += stepSize;
            incidentVector = Vector2f.add(incidentVector, step);
            setTempCell(incidentVector.x, incidentVector.y);
        }
    }

    public void copyTempCells() {
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                if (tempCells[i][j] == 1) {
                    setCell(i * cellSize, j * cellSize, 1);
                }
            }
        }
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                tempCells[i][j] = -1;
            }
        }
    }

    public void setTempCell(float x, float y) {
        if (x < 0 || x > Dijkstra.WIDTH || y < 0 || y > Dijkstra.HEIGHT) {
            return;
        }
        tempCells[(int) (x / cellSize)][(int) (y / cellSize)] = 1;
    }

    public void setCell(float x, float y, int value) {
        if (x < 0 || x > Dijkstra.WIDTH || y < 0 || y > Dijkstra.HEIGHT) {
            return; // check bounds
        }
        if (cells[(int) (x / cellSize)][(int) (y / cellSize)] == value) {
            return; // optimization
        }
        if (value == 2 || value == 3) {
            clearCells(value);
        }
        if (value == 2) {
            containsStart = true;
            start[0] = (int) (x / cellSize);
            start[1] = (int) (y / cellSize);
        } else if (value == 3) {
            containsEnd = true;
            end[0] = (int) (x / cellSize);
            end[1] = (int) (y / cellSize);
        }
        if (value == 1 || value == -1) {
            switch (cells[(int) (x / cellSize)][(int) (y / cellSize)]) {
                case 2:
                    start[0] = -1;
                    start[1] = -1;
                    containsStart = false;
                    break;
                case 3:
                    end[0] = -1;
                    end[1] = -1;
                    containsEnd = false;
                    break;
            }
        }
        cells[(int) (x / cellSize)][(int) (y / cellSize)] = value;
    }

    public void clearCells(int value) {
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                if (cells[i][j] == value) {
                    cells[i][j] = -1;
                }
            }
        }
    }

    public void clearTempCells() {
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                tempCells[i][j] = -1;
            }
        }
    }

    public void paint(Graphics g) {
        // draw the cells
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                switch (cells[i][j]) {
                    case 1:
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                        break;
                    case 2:
                        g.setColor(Color.BLUE);
                        g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                        break;
                    case 3:
                        g.setColor(Color.RED);
                        g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                        break;
                    case 4:
                        g.setColor(Color.GREEN);
                        g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                        break;
                }
            }
        }

        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                if (tempCells[i][j] == 1) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                }
            }
        }

        // draw the grid
        g.setColor(Color.BLACK);
        for (int i = 0; i < cellsHorizontal; i++) {
            g.drawLine(i * cellSize, 0, i*cellSize, cellsVertical * cellSize);
        }
        for (int i = 0; i < cellsVertical; i++) {
            g.drawLine(0, i * cellSize, cellsHorizontal * cellSize, i * cellSize);
        }
    }

    public void printGrid() {
        bfs(start[0], start[1], 0);
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                if (cells[i][j] == 1) {
                    bfs[i][j] = 1000;
                }
            }
        }
        for (int j = 0; j < cellsVertical; j++) {
            for (int i = 0; i < cellsHorizontal; i++) {
                if (bfs[i][j] == 1000) {
                    System.out.print("--" + "\t");
                } else {
                    System.out.print(bfs[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }
}