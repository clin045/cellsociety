import java.util.ArrayList;

/**
 *
 * Extension of main.RuleInterface to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class GameOfLifeRule implements RuleInterface {
    public int applyRule(Cell cell, ArrayList<Cell> myNeighbors) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : myNeighbors) {
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
        } else if (cell.getCurrentState() == 0) {
            if (liveNeighborsCount == 3) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
