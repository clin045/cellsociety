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
    public int getPasses(){
        return NUM_PASSES;
    }
    public int applyRule(Cell cell, ArrayList<Cell> neighborsArray) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            liveNeighborsCount += neighbor.getCurrentState();
        }

        // find next state of live cells
        if (cell.getCurrentState() == 1) {
            if (liveNeighborsCount < 2 || liveNeighborsCount > 3) {
                return 0;
            } else {
                return 1;
            }
            // find next state of dead cells
        } else{
            if (liveNeighborsCount == 3) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
