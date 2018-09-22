import java.util.ArrayList;

/**
 *
 * Extension of main.RuleInterface to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class FireRule implements RuleInterface {
    final int NUM_PASSES = 1;
    final int BURNING = 2;
    final int TREE = 1;
    final int EMPTY = 0;
    private double probCatch;

    public int getPasses(){
        return NUM_PASSES;
    }
    // Set the chance of a tree catching fire
    public void setProbCatch(double prob) { probCatch = prob; }

    public int applyRule(Cell cell, ArrayList<Cell> neighborsArray,int passNum) {
        int treeNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            if (neighbor.getCurrentState() == TREE) {
                treeNeighborsCount += 1;
            }
        }

        // find next state of live cells
        if (cell.getCurrentState() == TREE) {

        } else{
            if (liveNeighborsCount == 3) {
                return TREE;
            } else {
                return EMPTY;
            }
        }
    }
}
