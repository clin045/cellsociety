package model;

/*
The basic unit of a CA.

@author Christopher Lin cl349
 */
public class Cell {
    private int myCurrentState;
    private int myNextState;
    private int myRow;
    private int myCol;
    private int myGridStateList[];


    public int getRow() {
        return myRow;
    }

    public int getCol() {
        return myCol;
    }

    public int getCurrentState() {
        return myCurrentState;
    }

    public void setCurrentState(int currentState) {
        this.myCurrentState = currentState;
    }

    public int getNextState() {
        return myNextState;
    }

    public void setNextState(int nextState) {
        myGridStateList[myNextState] --;
        myNextState = nextState;
        myGridStateList[nextState] ++;
    }

    Cell(int row, int col, int initState, int[] gridStateList){
        myRow = row;
        myCol = col;
        myCurrentState = initState;
        myGridStateList = gridStateList;
    }
}
