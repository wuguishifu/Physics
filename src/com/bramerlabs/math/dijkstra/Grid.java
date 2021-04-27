package com.bramerlabs.math.dijkstra;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Grid {

    public int cellsHorizontal, cellsVertical, cellSize;
    public int[][] cells;

    public boolean containsStart = false, containsEnd = false;
    public int[] start, end;

    public Grid(int cellsHorizontal, int cellsVertical, int cellSize) {
        this.cellsHorizontal = cellsHorizontal;
        this.cellsVertical = cellsVertical;
        this.cellSize = cellSize;

        this.cells = new int[cellsHorizontal][cellsVertical];
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                cells[i][j] = -1;
            }
        }

        start = new int[2];
        end = new int[2];
    }

    public void findPath() {
        if (!(containsEnd && containsStart)) {
            return;
        }
        System.out.println("(" + start[0] + ", " + start[1] + ")   (" + end[0] + ", " + end[1] + ")");
    }

    public void setCell(float x, float y, int value) {
        if (!(value == 1 || value == -1)) {
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

    public void paint(Graphics g) {
        // draw the cells
        for (int i = 0; i < cellsHorizontal; i++) {
            for (int j = 0; j < cellsVertical; j++) {
                switch (cells[i][j]) {
                    case -1:
                        break;
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
}