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

        if (liveNeighborsCount < 2 || liveNeighborsCount > 3) {
            myCell.setNextState(0);

        } else {
            myCell.setNextState(1);
        }
    }
}
