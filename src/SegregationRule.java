import java.util.ArrayList;

/**
 *
 * Extension of main.RuleInterface to apply rules specifically for SegregationLife
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/

public class SegregationRule implements RuleInterface {
    final int NUM_PASSES = 2;

    final int VACANT = 0;
    final int BLUE = 1;
    final int RED = 2;
    final int UNSATISFIED = 3;

    private int unallocated_blue = 0;
    private int unallocated_red = 0;

    public int getPasses(){
        return NUM_PASSES;
    }
    // percent of neighbors that must be similar for happiness
    private double percentSimilarTolerance;

    // set percentSimilarTolerance based on user input
    public void setPercentSimilarTolerance(int percent) {
        percentSimilarTolerance = percent;
    }

    public int applyRule(Cell cell, ArrayList<Cell> neighborsArray, int passNum) {
        int similarNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            if (cell.getCurrentState() == neighbor.getCurrentState()) {
                similarNeighborsCount += neighbor.getCurrentState();
            }
        }

        // percent of neighbors that are similar
        int percentSimilarNeighbors = similarNeighborsCount / neighborsArray.size();

        // find next state of occupied cells
        if(passNum == 0){
            if (cell.getCurrentState() == RED || cell.getCurrentState() == BLUE) {
                if (percentSimilarNeighbors < percentSimilarTolerance) {
                    if(cell.getCurrentState() == RED){
                        unallocated_red ++;
                    }
                    else{
                        unallocated_blue ++;
                    }
                    return UNSATISFIED;
                } else {
                    return cell.getCurrentState();
                }
            } else {
                return VACANT;
            }
        }
        else{
            if(cell.getNextState() == VACANT){
                if(unallocated_blue > 0){
                    unallocated_blue--;
                    return BLUE;
                }
                else if(unallocated_red > 0){
                    unallocated_red--;
                    return RED;
                }
            }
            else if(cell.getNextState() == UNSATISFIED) {
                return VACANT;
            }
            return cell.getNextState();

        }
    }
}
