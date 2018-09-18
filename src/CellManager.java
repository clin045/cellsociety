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

    //Initializes the grid of cells
    public void initializeGrid(int rows, int cols, int[][] initConditions){
        rowSize = rows;
        colSize = cols;
        grid = new Cell[rows][cols];
        for (int i = 0; i < initConditions.length; i ++){
            for(int j = 0; j < initConditions[0].length; j++){
                Cell newCell = new Cell(i,j, initConditions[i][j]);
                grid[i][j] = newCell;
            }
        }
    }

    //detects whether or not a cell is an edge cell
    private boolean isEdge(Cell cell){
        return (cell.getRow() == 0 || cell.getRow() == grid.length || cell.getCol() == 0 || cell.getRow() == grid[0].length);
    }

    public Cell getCell(int row, int col){
        return grid[row][col];
    }


    public ArrayList<Cell> getNeighbors(Cell cell) {
        if(isEdge(cell)){
            return null;
        }
        var neighbors = new ArrayList<Cell>();
        for(int i = cell.getRow()-neighborhoodSize; i <= cell.getRow()+neighborhoodSize;i++){
            for(int j = cell.getCol()-neighborhoodSize; j <= cell.getCol()+neighborhoodSize; j++){
                if(grid[i][j] != cell) {
                    neighbors.add(grid[i][j]);
                }
            }
        }
        System.out.println(neighbors.size());
        return neighbors;
    }

    //TODO: REMOVE THIS METHOD. IT IS FOR TESTING PURPOSES ONLY
    public void setNextState(Cell cell, int state){
        cell.setNextState(state);
    }

    //Second pass sets currentState to nextState
    public void updateCells() {
        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize; j++){
                Cell currentCell = getCell(i,j);
                currentCell.setCurrentState(currentCell.getNextState());
            }
        }
    }
}
