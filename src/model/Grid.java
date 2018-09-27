package model;

import java.util.ArrayList;

public abstract class Grid {
    public final static int TOROIDAL = 1;

    protected Cell[][] myGrid;

    public int getRowSize() {
        return myRowSize;
    }

    public int getColSize() {
        return myColSize;
    }

    public int getEdgeType() {
        return myEdgeType;
    }

    private int myRowSize;
    private int myColSize;
    private int myEdgeType;

    Grid(int rowSize, int colSize, int edgeType){
        myRowSize = rowSize;
        myColSize = colSize;
        myEdgeType = edgeType;
    }

    public abstract ArrayList<Cell> getNeighbors(Cell cell);
    public abstract Cell getCell(int row, int col);


}
