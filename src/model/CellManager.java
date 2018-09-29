package model;

import model.rule.Rule;

import java.util.List;

/**
 * This class is responsible for managing the myGrid and editing cell states
 *
 * @author Christopher Lin cl349
 **/
public class CellManager {
    //Constants
    public static final int SQUARE_GRID = 0;
    public static final int TRIANGLE_GRID = 1;
    Rule myActiveRule;
    private Grid myGrid;
    private int myRowSize;
    private int myColSize;


    //Initializes the myGrid of cells
    public CellManager(int rows, int cols, int[][] initConditions, Rule activeRule, int gridType) {
        myRowSize = rows;
        myColSize = cols;
        if (gridType == SQUARE_GRID) {
            myGrid = new SquareGrid(rows, cols, initConditions, Grid.TOROIDAL, activeRule.getNumStates(), activeRule.getCellType());
        }
        else if (gridType == TRIANGLE_GRID){
            myGrid = new TriangleGrid(rows, cols, initConditions, Grid.TOROIDAL, activeRule.getNumStates(), activeRule.getCellType());
        }
        myActiveRule = activeRule;
    }

    public Grid getGrid() {
        return myGrid;
    }

    //Second pass sets currentState to nextState
    public void updateCells() {
        for (int i = 0; i < myRowSize; i++) {
            for (int j = 0; j < myColSize; j++) {
                Cell myCurrentCell = myGrid.getCell(i, j);
                myGrid.myStateList[myCurrentCell.getCurrentState()]--;
                myCurrentCell.setCurrentState(myCurrentCell.getNextState());
                myGrid.myStateList[myCurrentCell.getCurrentState()]++;
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
        boolean[] maskRow0 = {true, true, true};
        boolean[] maskRow1 = {true,false,true};
        boolean[] maskRow2 = {true,true,true};
        boolean[][] mask = {maskRow0,maskRow1,maskRow2};

        //apply rules
        for (int k = 0; k < numPasses; k++) {
            for (int i = 0; i < myRowSize; i++) {
                for (int j = 0; j < myColSize; j++) {
                    Cell currentCell = myGrid.getCell(i, j);
                    List neighborList = myGrid.getNeighbors(currentCell,mask);
                    myActiveRule.applyRule(currentCell, neighborList, k);
                }
            }
        }
        updateCells();
    }


}
