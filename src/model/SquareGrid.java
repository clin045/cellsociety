package model;

import java.util.ArrayList;
import java.util.List;

public class SquareGrid extends Grid {

    SquareGrid(int rowSize, int colSize, int[][] initialConditions, int edgeType, int numStates) {
        super(rowSize, colSize, edgeType, numStates);
        myGrid = new Cell[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                myGrid[i][j] = new Cell(i, j, initialConditions[i][j], myStateList);
                myStateList[initialConditions[i][j]] += 1;
            }
        }
    }

    @Override
    public List getNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        for (int i = cell.getRow() - 1; i <= cell.getRow() + 1; i++) {
            for (int j = cell.getCol() - 1; j <= cell.getCol() + 1; j++) {
                if (getCell(i, j) != cell && getCell(i, j) != null) {
                    neighbors.add(getCell(i, j));
                }
            }
        }
        return neighbors;
    }


    @Override
    public Cell getCell(int row, int col) {
        if (getEdgeType() == Grid.TOROIDAL) {
            return (getWrappingCell(row, col));
        } else if (getEdgeType() == Grid.STATIC) {
            return (getStaticCell(row, col));
        } else {
            return null;
        }
    }

    private Cell getStaticCell(int row, int col) {
        if (row == 0 || col == 0 || row == myGrid.length - 1 || col == myGrid[0].length - 1) {
            return null;
        } else {
            return myGrid[row][col];
        }
    }

    private Cell getWrappingCell(int row, int col) {
        int adjustedRow;
        int adjustedCol;
        if (row < 0) {
            adjustedRow = getRowSize() + row;
        } else if (row > getRowSize() - 1) {
            adjustedRow = row - getRowSize();
        } else {
            adjustedRow = row;
        }
        if (col < 0) {
            adjustedCol = (getColSize()) + col;
        } else if (col > getColSize() - 1) {
            adjustedCol = col - getColSize();
        } else {
            adjustedCol = col;
        }

        return myGrid[adjustedRow][adjustedCol];
    }
}
