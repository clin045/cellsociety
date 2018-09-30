import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Cell;
import model.CellManager;
import model.Rule;


public class TriangleGridUI extends GridUI {
    private static final int USABLE_WINDOW_SIZE = 500;
    private GridPane simulatorGridPane;
    private String[] myColors;
    private int myRows;
    private int myColumns;
    private CellManager myCellManager;
    private Polygon[][] myTriangles;
    private double triangleWidth;
    private double triangleHeight;

    TriangleGridUI(int[][] initialStates, int rows, int columns, String[] colors, Rule myRule){
        myColors = colors;
        myRows = rows;
        myColumns = columns;
        triangleWidth = USABLE_WINDOW_SIZE/(myColumns/2 + .5);
        triangleHeight = USABLE_WINDOW_SIZE/myRows;
        myTriangles = new Polygon[myRows][myColumns];
        myCellManager = new CellManager(rows, columns, initialStates, myRule, CellManager.TRIANGLE_GRID);
        simulatorGridPane = new GridPane();
        simulatorGridPane.setAlignment(Pos.CENTER);
        Pane triangleHolder = new Pane();
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myColumns; j++) {
                myTriangles[i][j] = new Polygon();
                if(i%2 == 0){
                    //even row
                    if(j%2 == 0){
                        //even col
                        myTriangles[i][j].getPoints().addAll(
                                triangleWidth*(j/2.0), triangleHeight*(i+1),
                                triangleWidth*(j/2.0)+triangleWidth, triangleHeight*(i+1),
                                triangleWidth*(j/2.0)+triangleWidth/2.0, triangleHeight*i
                        );
                    }
                    else {
                        //odd col
                        myTriangles[i][j].getPoints().addAll(
                                triangleWidth*(j/2.0), triangleHeight*i,
                                triangleWidth*(j/2.0)+triangleWidth, triangleHeight*i,
                                triangleWidth*(j/2.0)+triangleWidth/2.0, triangleHeight*(i+1)
                        );
                    }
                }
                else {
                    //odd row
                    if(j%2 == 0){
                        //even col
                        myTriangles[i][j].getPoints().addAll(
                                triangleWidth*(j/2.0), triangleHeight*i,
                                triangleWidth*(j/2.0)+triangleWidth, triangleHeight*i,
                                triangleWidth*(j/2.0)+triangleWidth/2.0, triangleHeight*(i+1)
                        );
                    }
                    else {
                        //odd col
                        myTriangles[i][j].getPoints().addAll(
                                triangleWidth*(j/2.0), triangleHeight*(i+1),
                                triangleWidth*(j/2.0)+triangleWidth, triangleHeight*(i+1),
                                triangleWidth*(j/2.0)+triangleWidth/2.0, triangleHeight*i
                        );
                    }
                }
                myTriangles[i][j].setFill(Color.web(myColors[initialStates[i][j]]));
                myTriangles[i][j].setStroke(Color.BLACK);
                int togglei = i;
                int togglej = j;
                myTriangles[i][j].setOnMouseClicked(event -> toggleNextState(togglei, togglej));
                triangleHolder.getChildren().add(myTriangles[i][j]);
            }
        }
        simulatorGridPane.add(triangleHolder, 0, 0);
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

    public void updateCellAppearance(String color, int i, int j){
        myTriangles[i][j].setFill(Color.web(color));
    }

    public void toggleNextState(int i, int j){
        int numStates = myColors.length;
        Cell thisCell = myCellManager.getGrid().getCell(i, j);
        if (thisCell.getCurrentState() == numStates - 1) {
            thisCell.setCurrentState(0);
            thisCell.setNextState(0);
        } else {
            thisCell.setNextState(thisCell.getCurrentState() + 1);
            thisCell.setCurrentState(thisCell.getCurrentState() + 1);
        }
        updateCellAppearance(myColors[thisCell.getNextState()], i, j);
    }

    public GridPane getGridPane(){
        return simulatorGridPane;
    }

    public int[] getCellStateList(){
        return myCellManager.getGrid().getStateList();
    }
}
