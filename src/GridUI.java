import javafx.scene.layout.GridPane;

public abstract class GridUI {

    public abstract void step();

    public abstract GridPane getGridPane();

    public abstract int[] getCellStateList();

}
