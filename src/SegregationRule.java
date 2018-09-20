import java.util.ArrayList;

/**
 *
 * Extension of main.RuleInterface to apply rules specifically for SegregationLife
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/

// states:
// 0: empty
// 1: blue
// 2: red
public class SegregationRule implements RuleInterface {
    final int NUM_PASSES = 2;

    public int getPasses(){
        return NUM_PASSES;
    }
    // percent of neighbors that must be similar for happiness
    private double percentSimilarTolerance;

    // set percentSimilarTolerance based on user input
    public void setPercentSimilarTolerance(int percent) {
        percentSimilarTolerance = percent;
    }

    public int applyRule(Cell cell, ArrayList<Cell> neighborsArray) {
        int similarNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            if (cell.getCurrentState() == neighbor.getCurrentState()) {
                similarNeighborsCount += neighbor.getCurrentState();
            }
        }

        // percent of neighbors that are similar
        int percentSimilarNeighbors = similarNeighborsCount / neighborsArray.size();

        // find next state of occupied cells
        if (cell.getCurrentState() == 1 || cell.getCurrentState() == 2) {
            if (percentSimilarNeighbors < percentSimilarTolerance) {
                return 0;
            } else {
                return cell.getCurrentState();
            }
        } else {
            return 0;
        }
    }
}
