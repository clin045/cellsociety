
/*
This class is responsible for managing the grid and editing cell states
@author Christopher Lin cl349
 */
public class CellManager {
    private Cell[][] grid;

    //Initializes the grid of cells
    public void initializeGrid(int rows, int cols, int[][] initConditions){
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



}
