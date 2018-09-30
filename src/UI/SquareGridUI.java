package UI;

import UI.GridUI;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Cell;
import model.CellManager;
import model.rule.Rule;

public class SquareGridUI extends GridUI {
    private static final int USABLE_WINDOW_SIZE = 500;
    private GridPane simulatorGridPane;
    private String[] myColors;
    private int myRows;
    private int myColumns;
    private CellManager myCellManager;

    SquareGridUI(int[][] initialStates, int rows, int columns, String[] colors, Rule myRule, int[][] neighbors){
        myColors = colors;
        myRows = rows;
        myColumns = columns;
        myCellManager = new CellManager(rows, columns, initialStates, myRule, CellManager.SQUARE_GRID, neighbors);
        simulatorGridPane = new GridPane();
        simulatorGridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myColumns; j++) {
                simulatorGridPane.add(createCell(myColors[initialStates[i][j]]), j, i);
            }
        }
    }

    public void step(){
        myCellManager.nextGeneration();
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myColumns; j++) {
                BorderPane thisCellPane = (BorderPane) getNodeFromGridPane(i, j);
                Cell thisCell = myCellManager.getGrid().getCell(i, j);
                if (thisCellPane != null) {
                    updateCellAppearance(myColors[thisCell.getCurrentState()], thisCellPane);
                }
            }
        }
    }

    public BorderPane createCell(String color){
        BorderPane cell = new BorderPane();
        int cellSize = USABLE_WINDOW_SIZE / Math.max(myRows, myColumns);
        cell.setMinSize(cellSize, cellSize);
        cell.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #" + color + ";" +
                "-fx-border-width: 1;");
        cell.setOnMouseClicked(event -> toggleNextState(cell));
        return cell;

    }

    public void updateCellAppearance(String color, BorderPane myCell){
        myCell.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #" + color + ";" +
                "-fx-border-width: 1;");
    }

    public void toggleNextState(BorderPane cell){
        int numStates = myColors.length;
        Cell thisCell = myCellManager.getGrid().getCell(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
        if (thisCell.getCurrentState() == numStates - 1) {
            thisCell.setCurrentState(0);
            thisCell.setNextState(0);
        } else {
            thisCell.setNextState(thisCell.getCurrentState() + 1);
            thisCell.setCurrentState(thisCell.getCurrentState() + 1);
        }
        updateCellAppearance(myColors[thisCell.getNextState()], cell);
    }

    private Node getNodeFromGridPane(int col, int row) {
        for (Node node : simulatorGridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public GridPane getGridPane(){
        return simulatorGridPane;
    }

    public int[] getCellStateList(){
        return myCellManager.getGrid().getStateList();
    }
}
