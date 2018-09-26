package model;

/*
The basic unit of a CA.

@author Christopher Lin cl349
 */
public class Cell {
    private int currentState;
    private int nextState;
    private int row;
    private int col;


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public int getNextState() {
        return nextState;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }

    Cell(int row, int col, int initState){
        this.row = row;
        this.col = col;
        this.currentState = initState;
    }
}
