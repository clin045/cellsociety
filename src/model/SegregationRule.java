package model;


import java.util.List;

/**
 * Extension of main.model.Rule to apply rules specifically for SegregationLife
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/

public class SegregationRule extends Rule {
    static final int NUM_PASSES = 2;
    static final int VACANT = 0;
    static final int BLUE = 1;
    static final int RED = 2;
    static final int UNSATISFIED = 3;
    static final double PERCENT_SIMILAR_TOLERANCE = 0.3;

    private int unallocated_blue = 0;
    private int unallocated_red = 0;

    public SegregationRule(){
        myNumStates = 4;
    }

    public int getPasses() {
        return NUM_PASSES;
    }
    // percent of neighbors that must be similar for happiness


    public int getNeighborhoodSize() {
        return 1;
    }


    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {
        int similarNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            if (cell.getCurrentState() == neighbor.getCurrentState()) {
                similarNeighborsCount += neighbor.getCurrentState();
            }
        }

        // percent of neighbors that are similar
        double percentSimilarNeighbors = similarNeighborsCount / neighborsArray.size();

        // find next state of occupied cells
        if (passNum == 0) {
            markUnsatisfied(cell, percentSimilarNeighbors);
        } else {
            moveCells(cell);

        }
    }

    private void moveCells(Cell cell) {
        if (cell.getNextState() == VACANT) {
            if (unallocated_blue > 0) {
                unallocated_blue--;
                cell.setNextState(BLUE);
            } else if (unallocated_red > 0) {
                unallocated_red--;
                cell.setNextState(RED);
            }
        } else if (cell.getNextState() == UNSATISFIED) {
            cell.setNextState(VACANT);
        }
        cell.setNextState(cell.getNextState());
    }

    private void markUnsatisfied(Cell cell, double percentSimilarNeighbors) {
        if (cell.getCurrentState() == RED || cell.getCurrentState() == BLUE) {
            if (percentSimilarNeighbors < PERCENT_SIMILAR_TOLERANCE) {
                if (cell.getCurrentState() == RED) {
                    unallocated_red++;
                } else {
                    unallocated_blue++;
                }
                cell.setNextState(UNSATISFIED);
            } else {
                cell.setNextState(cell.getCurrentState());
            }
        } else {
            cell.setNextState(VACANT);
        }
    }
}
