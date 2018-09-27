package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Extension of main.model.Rule to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class FireRule extends Rule {
    static final int NUM_PASSES = 1;
    static final int BURNING = 2;
    static final int TREE = 1;
    static final int EMPTY = 0;
    static final double PROB_CATCH = 0.8;

    public int getPasses() {
        return NUM_PASSES;
    }


    public int getNeighborhoodSize() {
        return 1;
    }

    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {
        boolean nearFire = false;
        //throw out diagonals
        for (Cell c : neighborsArray) {
            if (c.getRow() != cell.getRow() && cell.getCol() != cell.getCol()) {
                neighborsArray.remove(c);
            }
        }
        for (Cell neighbor : neighborsArray) {
            if (neighbor.getCurrentState() == BURNING) {
                nearFire = true;
            }
        }
        // find next state of tree cells
        if (cell.getCurrentState() == TREE) {
            if (nearFire && new Random().nextDouble() <= PROB_CATCH) {
                cell.setNextState(BURNING);
            }
        }
        // if cell is empty or burning, make it empty next
        else {
            cell.setNextState(EMPTY);
        }
    }
}
