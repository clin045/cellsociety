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
// 1: blue  (shark)
// 2: green (fish)
public class PredatorPreyRule implements RuleInterface {

    ArrayList<Cell> openNeighbors = new ArrayList<>();
    ArrayList<Cell> sharkNeighbors = new ArrayList<>();
    ArrayList<Cell> fishNeighbors = new ArrayList<>();
    public final static int PASSES = 2;

    public int getPasses(){
        return PASSES;
    }

    public int applyRule(Cell cell, ArrayList<Cell> neighborsArray, int passNum) {
        // find states of neighbors
        for (Cell neighbor : neighborsArray) {
            if (neighbor.getCurrentState() == 0) {
                openNeighbors.add(neighbor);
            } else if (neighbor.getCurrentState() == 1) {
                sharkNeighbors.add(neighbor);
            } else if (neighbor.getCurrentState() == 2) {
                fishNeighbors.add(neighbor);
            }
        }

        // find next state of shark cells
        if (cell.getCurrentState() == 1) {
            if (fishNeighbors.size() > 0) {
                return 0;
            } else if (openNeighbors.size() > 0){
                return 0;
            } else {
                return cell.getCurrentState();
            }
        }
        // find next state of fish cells
        if (cell.getCurrentState() == 2) {
            if (openNeighbors.size() > 0) {
                return 0;
            } else {
                return cell.getCurrentState();
            }
        }
        return 0;
    }
}
