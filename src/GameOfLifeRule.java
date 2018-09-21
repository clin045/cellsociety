import java.util.ArrayList;

/**
 *
 * Extension of main.RuleInterface to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class GameOfLifeRule implements RuleInterface {
    final int NUM_PASSES = 1;
    final int ALIVE = 1;
    final int DEAD = 0;

    public int getPasses(){
        return NUM_PASSES;
    }
    public int applyRule(Cell cell, ArrayList<Cell> neighborsArray,int passNum) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            liveNeighborsCount += neighbor.getCurrentState();
        }

        // find next state of live cells
        if (cell.getCurrentState() == ALIVE) {
            if (liveNeighborsCount < 2 || liveNeighborsCount > 3) {
                return DEAD;
            } else {
                return ALIVE;
            }
            // find next state of dead cells
        } else{
            if (liveNeighborsCount == 3) {
                return ALIVE;
            } else {
                return DEAD;
            }
        }
    }
}
