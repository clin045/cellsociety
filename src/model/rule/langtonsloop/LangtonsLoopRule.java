package model.rule.langtonsloop;


import model.Cell;
import model.rule.Rule;

import java.util.List;

/**
 * Extension of main.model.rule.Rule to apply rules specifically for Langton's Loop
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class LangtonsLoopRule extends Rule {
    static final int NUM_PASSES = 1;
    static final int VACANT = 0; //black
    static final int SIGNAL = 1; //dark blue
    static final int SHEATH = 2; //red
    static final int VACANT = 0;
    static final int TURN = 4; //green
    static final int MESSENGER = 5; //pink
    static final int VACANT = 0;
    static final int VACANT = 0;
    static final int ADVANCE = 7; //light blue


    static final int EAST = 0;
    static final int NORTH = 1;
    static final int WEST = 2;
    static final int SOUTH = 4;

    private int myDirection = EAST;

    public LangtonsLoopRule(){
        myNumStates = 8;
    }

    public int getPasses() {
        return NUM_PASSES;
    }

    public int getNeighborhoodSize() {
        return 1;
    }

    @Override
    public Class getCellType() {
        return Cell.class;
    }

    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {


    }

    //NESW
    public String statesToString(Cell cell, List<Cell> neighborsArray) {
        String cellState = Integer.toString(cell.getCurrentState());
        int aboveCount = 0;
        for (Cell c : neighborsArray) {
            if (c)

        }

        if (c.getRow() > cell.getRow()) {
            String northCell = Integer.toString(c.getCurrentState());
        }
        return "";
    }
}
