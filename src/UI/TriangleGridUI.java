package UI;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Cell;
import model.CellManager;
import model.rule.Rule;


public class TriangleGridUI extends GridUI {
    private static final int USABLE_WINDOW_SIZE = 500;
    private GridPane simulatorGridPane;
    private String[] myColors;
    private int myRows;
    private int myColumns;
    private CellManager myCellManager;
    private Polygon[][] myTriangles;

    TriangleGridUI(int[][] initialStates, int rows, int columns, String[] colors, Rule myRule, int[][] neighbors){
        myColors = colors;
        myRows = rows;
        myColumns = columns;
        double triangleWidth = USABLE_WINDOW_SIZE / (myColumns / 2.0 + .5);
        double triangleHeight = (double)USABLE_WINDOW_SIZE / myRows;
        myTriangles = new Polygon[myRows][myColumns];
        myCellManager = new CellManager(rows, columns, initialStates, myRule, CellManager.TRIANGLE_GRID, neighbors);
        simulatorGridPane = new GridPane();
        simulatorGridPane.setAlignment(Pos.CENTER);
        Pane triangleHolder = generateTriangles(triangleWidth, triangleHeight, initialStates);
        simulatorGridPane.add(triangleHolder, 0, 0);
    }

    private Pane generateTriangles(double triangleWidth, double triangleHeight, int[][] initialStates){
        Pane triangleHolder = new Pane();
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myColumns; j++) {
                myTriangles[i][j] = new Polygon();
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    myTriangles[i][j].getPoints().addAll(
                            triangleWidth * (j / 2.0), triangleHeight * (i + 1),
                            triangleWidth * (j / 2.0) + triangleWidth, triangleHeight * (i + 1),
                            triangleWidth * (j / 2.0) + triangleWidth / 2.0, triangleHeight * i
                    );
                } else {
                    myTriangles[i][j].getPoints().addAll(
                            triangleWidth * (j / 2.0), triangleHeight * i,
                            triangleWidth * (j / 2.0) + triangleWidth, triangleHeight * i,
                            triangleWidth * (j / 2.0) + triangleWidth / 2.0, triangleHeight * (i + 1)
                    );
                }
                myTriangles[i][j].setFill(Color.web(myColors[initialStates[i][j]]));
                myTriangles[i][j].setStroke(Color.BLACK);
                int togglei = i;
                int togglej = j;
                myTriangles[i][j].setOnMouseClicked(event -> toggleNextState(togglei, togglej));
                triangleHolder.getChildren().add(myTriangles[i][j]);
            }
        }
        return triangleHolder;
    }

    public void step(){
        myCellManager.nextGeneration();
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myColumns; j++) {
                Cell thisCell = myCellManager.getGrid().getCell(i, j);
                updateCellAppearance(myColors[thisCell.getCurrentState()], i, j);
            }
        }
    }

    private void updateCellAppearance(String color, int i, int j){
        myTriangles[i][j].setFill(Color.web(color));
    }

    private void toggleNextState(int i, int j){
        int numStates = myColors.length;
        Cell thisCell = myCellManager.getGrid().getCell(i, j);
        this.setNextStates(thisCell, numStates);
        updateCellAppearance(myColors[thisCell.getNextState()], i, j);
    }

    public GridPane getGridPane(){
        return simulatorGridPane;
    }

    public int[] getCellStateList(){
        return myCellManager.getGrid().getStateList();
    }
}
