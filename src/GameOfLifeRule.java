/**
 *
 * Extension of RuleInterface to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class GameOfLifeRule implements RuleInterface{
    public void applyRule(Cell myCell, Cell[] myNeighbors) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : myNeighbors) {
            liveNeighborsCount += neighbor.getCurrentState();
        }

        // find next state of live cells
        if (myCell.getCurrentState() == 1) {
            if (liveNeighborsCount < 2 || liveNeighborsCount > 3) {
                myCell.setNextState(0);
            } else {
                myCell.setNextState(1);
            }
        // find next state of dead cells
        } else if (myCell.getCurrentState() == 0) {
            if (liveNeighborsCount == 3) {
                myCell.setNextState(1);
            } else {
                myCell.setNextState(0);
            }
        }
    }
}
