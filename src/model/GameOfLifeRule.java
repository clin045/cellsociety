package model;

import java.util.ArrayList;

/**
 * Extension of main.model.Rule to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class GameOfLifeRule extends Rule {
    static final int NUM_PASSES = 1;
    static final int ALIVE = 1;
    static final int DEAD = 0;

    public int getPasses() {
        return NUM_PASSES;
    }

    public int getNeighborhoodSize() {
        return 1;
    }

    public void applyRule(Cell cell, ArrayList<Cell> neighborsArray, int passNum) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            liveNeighborsCount += neighbor.getCurrentState();
        }

        // find next state of live cells
        if (cell.getCurrentState() == ALIVE) {
            if (liveNeighborsCount < 2 || liveNeighborsCount > 3) {
                cell.setNextState(DEAD);
            } else {
                cell.setNextState(ALIVE);
            }
            // find next state of dead cells
        } else {
            if (liveNeighborsCount == 3) {
                cell.setNextState(ALIVE);
            } else {
                cell.setNextState(DEAD);
            }
        }
    }
}
