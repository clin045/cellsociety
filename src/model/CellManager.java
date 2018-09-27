package model;

import java.util.ArrayList;

/**
 * This class is responsible for managing the myGrid and editing cell states
 *
 * @author Christopher Lin cl349
 **/
public class CellManager {
    //Constants
    public static final int SQUARE_GRID = 0;
    Rule myActiveRule;
    private Grid myGrid;
    private int myRowSize;
    private int myColSize;

    public Grid getGrid() {
        return myGrid;
    }

    //Initializes the myGrid of cells
    public void initializeGrid(int rows, int cols, int[][] initConditions, Rule activeRule, int gridType) {
        myRowSize = rows;
        myColSize = cols;
        if (gridType == SQUARE_GRID) {
            myGrid = new SquareGrid(rows, cols, initConditions, Grid.TOROIDAL, 2);
        }
        myActiveRule = activeRule;
    }


    //Second pass sets currentState to nextState
    public void updateCells() {
        for (int i = 0; i < myRowSize; i++) {
            for (int j = 0; j < myColSize; j++) {
                Cell myCurrentCell = myGrid.getCell(i, j);
                myCurrentCell.setCurrentState(myCurrentCell.getNextState());
            }
        }
    }

    public void nextGeneration() {
        int numPasses = myActiveRule.getPasses();
        //default all nextState to currentState to make some simulations easier
        for (int i = 0; i < myRowSize; i++) {
            for (int j = 0; j < myColSize; j++) {
                myGrid.getCell(i, j).setNextState(myGrid.getCell(i, j).getCurrentState());
            }
        }
        //apply rules
        for (int k = 0; k < numPasses; k++) {
            for (int i = 0; i < myRowSize; i++) {
                for (int j = 0; j < myColSize; j++) {
                    Cell currentCell = myGrid.getCell(i, j);
                    ArrayList<Cell> neighborList = myGrid.getNeighbors(currentCell);
                    myActiveRule.applyRule(currentCell, neighborList, k);

                }
            }
        }
        updateCells();
    }


}
