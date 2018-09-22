import java.util.ArrayList;
import java.util.Random;

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

    public int getNeighborhoodSize() {
        return 1;
    }

    public void applyRule(Cell cell, ArrayList<Cell> neighborsArray,int passNum) {
        boolean nearFire = false;
        for (Cell neighbor : neighborsArray) {
            if (neighbor.getCurrentState() == BURNING) {
                nearFire = true;
            }
        }

        // find next state of tree cells
        if (cell.getCurrentState() == TREE) {
            if (nearFire && new Random().nextDouble() <= probCatch) {
                cell.setNextState(BURNING);
            }
        }
        // if cell is empty or burning, make it empty next
        else {
            cell.setNextState(EMPTY);
        }
    }
}
