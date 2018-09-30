package UI;

import javafx.scene.layout.GridPane;
import model.Cell;

public abstract class GridUI {

    public abstract void step();

    public abstract GridPane getGridPane();

    public abstract int[] getCellStateList();

    public void setNextStates(Cell thisCell, int numStates){
        if (thisCell.getCurrentState() == numStates - 1) {
            thisCell.setCurrentState(0);
            thisCell.setNextState(0);
        } else {
            thisCell.setNextState(thisCell.getCurrentState() + 1);
            thisCell.setCurrentState(thisCell.getCurrentState() + 1);
        }
    }

}
