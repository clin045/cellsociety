package model;

import java.util.ArrayList;

public abstract class Grid {
    public final static int STATIC = 0;
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
    protected int[] myStateList;

    Grid(int rowSize, int colSize, int edgeType, int numStates){
        myRowSize = rowSize;
        myColSize = colSize;
        myEdgeType = edgeType;
        myStateList = new int[numStates];
    }

    public int[] getStateList(){
        return myStateList;
    }

    public abstract ArrayList<Cell> getNeighbors(Cell cell);
    public abstract Cell getCell(int row, int col);


}
