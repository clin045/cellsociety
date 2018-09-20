import java.util.ArrayList;

/*
This class is responsible for managing the grid and editing cell states
@author Christopher Lin cl349
 */
public class CellManager {
    private Cell[][] grid;
    private int rowSize;
    private int colSize;
    private int neighborhoodSize = 1;
    RuleInterface myActiveRule;

    //Initializes the grid of cells
    public void initializeGrid(int rows, int cols, int[][] initConditions){
        this.rowSize = rows;
        this.colSize = cols;
        this.grid = new Cell[rows][cols];
        for (int i = 0; i < initConditions.length; i ++){
            for(int j = 0; j < initConditions[0].length; j++){
                Cell myNewCell = new Cell(i,j, initConditions[i][j]);
                grid[i][j] = myNewCell;
            }
        }
        myActiveRule = new GameOfLifeRule();
    }

    //detects whether or not a cell is an edge cell
    private boolean isEdge(Cell cell){
        return (cell.getRow() == this.neighborhoodSize-1 || cell.getRow() == rowSize-(this.neighborhoodSize)
                || cell.getCol() == this.neighborhoodSize-1 || cell.getCol() == colSize-(this.neighborhoodSize));
    }

    public Cell getCell(int row, int col){
        return grid[row][col];
    }


    public ArrayList<Cell> getNeighbors(Cell cell) {
        if(isEdge(cell)){
            return null;
        }
        var myNeighbors = new ArrayList<Cell>();
        for(int i = cell.getRow()-neighborhoodSize; i <= cell.getRow()+neighborhoodSize;i++){
            for(int j = cell.getCol()-neighborhoodSize; j <= cell.getCol()+neighborhoodSize; j++){
                if(grid[i][j] != cell) {
                    myNeighbors.add(grid[i][j]);
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
        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize; j++){
                Cell myCurrentCell = getCell(i,j);
                myCurrentCell.setCurrentState(myCurrentCell.getNextState());
            }
        }
    }

    public void nextGeneration(){
        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize;j++){
                Cell currentCell = grid[i][j];
                ArrayList<Cell> neighborList = getNeighbors(currentCell);
                if(!isEdge(currentCell)){
                    currentCell.setNextState(myActiveRule.applyRule(currentCell, neighborList));
                }
            }
        }
        updateCells();
    }
}
