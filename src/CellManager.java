import java.util.ArrayList;

/**
 * This class is responsible for managing the myGrid and editing cell states
 * @author Christopher Lin cl349
 **/
public class CellManager {
    private Cell[][] myGrid;
    private int myRowSize;
    private int myColSize;
    private int myNeighborhoodSize = 1;
    RuleInterface myActiveRule;

    //Initializes the myGrid of cells
    public void initializeGrid(int rows, int cols, int[][] initConditions){
        myRowSize = rows;
        myColSize = cols;
        myGrid = new Cell[rows][cols];
        for (int i = 0; i < initConditions.length; i ++){
            for(int j = 0; j < initConditions[0].length; j++){
                Cell myNewCell = new Cell(i,j, initConditions[i][j]);
                myGrid[i][j] = myNewCell;
            }
        }
        myActiveRule = new GameOfLifeRule();
    }

    //detects whether or not a cell is an edge cell
    private boolean isEdge(Cell cell){
        return (cell.getRow() == myNeighborhoodSize -1 || cell.getRow() == myRowSize -(this.myNeighborhoodSize)
                || cell.getCol() == myNeighborhoodSize -1 || cell.getCol() == myColSize -(this.myNeighborhoodSize));
    }

    public Cell getCell(int row, int col){
        return myGrid[row][col];
    }


    public ArrayList<Cell> getNeighbors(Cell cell) {
        if(isEdge(cell)){
            return null;
        }
        var myNeighbors = new ArrayList<Cell>();
        for(int i = cell.getRow()- myNeighborhoodSize; i <= cell.getRow()+ myNeighborhoodSize; i++){
            for(int j = cell.getCol()- myNeighborhoodSize; j <= cell.getCol()+ myNeighborhoodSize; j++){
                if(myGrid[i][j] != cell) {
                    myNeighbors.add(myGrid[i][j]);
                }
            }
        }
        return myNeighbors;
    }

    //TODO: REMOVE THIS METHOD. IT IS FOR TESTING PURPOSES ONLY
    public void setNextState(Cell cell, int state){
        cell.setNextState(state);
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
        for(int i = 0; i < myRowSize; i++){
            for(int j = 0; j < myColSize; j++){
                Cell currentCell = myGrid[i][j];
                ArrayList<Cell> neighborList = getNeighbors(currentCell);
                if(!isEdge(currentCell)){
                    currentCell.setNextState(myActiveRule.applyRule(currentCell, neighborList));
                }
            }
        }
        updateCells();
    }
}
