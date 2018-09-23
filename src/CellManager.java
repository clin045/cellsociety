import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class is responsible for managing the myGrid and editing cell states
 * @author Christopher Lin cl349
 **/
public class CellManager {
    private Cell[][] myGrid;
    private int myRowSize;
    private int myColSize;
    private int myNeighborhoodSize;
    RuleInterface myActiveRule;

    //Initializes the myGrid of cells
    public void initializeGrid(int rows, int cols, int[][] initConditions, RuleInterface activeRule){
        myRowSize = rows;
        myColSize = cols;
        myGrid = new Cell[rows][cols];
        myActiveRule = activeRule;
        myNeighborhoodSize = activeRule.getNeighborhoodSize();
        for (int i = 0; i < initConditions.length; i ++){
            for(int j = 0; j < initConditions[0].length; j++){
                Cell myNewCell = new Cell(i,j, initConditions[i][j]);
                myGrid[i][j] = myNewCell;
            }
        }
    }



    public Cell getCell(int row, int col){
        int adjustedRow;
        int adjustedCol;
        if (row < 0){
            adjustedRow = (myRowSize) + row;
        }
        else if(row > myRowSize-1){
            adjustedRow = row-myRowSize;
        }
        else{
            adjustedRow = row;
        }
        if (col < 0){
            adjustedCol = (myColSize) + col;
        }
        else if(col > myColSize-1){
            adjustedCol = col-myColSize;
        }
        else{
            adjustedCol = col;
        }

        return myGrid[adjustedRow][adjustedCol];
    }


    public ArrayList<Cell> getNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        for(int i = cell.getRow()-myNeighborhoodSize; i <= cell.getRow() + myNeighborhoodSize; i ++){
            for(int j = cell.getCol()-myNeighborhoodSize; j <= cell.getCol() + myNeighborhoodSize; j++){
                if(getCell(i,j) != cell){
                    neighbors.add(getCell(i,j));
                }
            }
        }
        return neighbors;
    }


    //Second pass sets currentState to nextState
    public void updateCells() {
        for(int i = 0; i < myRowSize; i++){
            for(int j = 0; j < myColSize; j++){
                Cell myCurrentCell = getCell(i,j);
                myCurrentCell.setCurrentState(myCurrentCell.getNextState());
            }
        }
    }

    public void nextGeneration(){
        int numPasses = myActiveRule.getPasses();
        //default all nextState to currentState to make some simulations easier
        for(int i = 0; i < myRowSize; i++){
            for(int j = 0; j < myColSize; j++){
                myGrid[i][j].setNextState(myGrid[i][j].getCurrentState());
            }
        }
        //apply rules
        for (int k = 0; k < numPasses; k++) {
            for (int i = 0; i < myRowSize; i++) {
                for (int j = 0; j < myColSize; j++) {
                    Cell currentCell = myGrid[i][j];
                    ArrayList<Cell> neighborList = getNeighbors(currentCell);
                    myActiveRule.applyRule(currentCell, neighborList, k);

                }
            }
        }
        updateCells();
    }
}
