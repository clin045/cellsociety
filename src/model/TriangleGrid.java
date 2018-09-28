package model;

import java.util.ArrayList;
import java.util.List;

public class TriangleGrid extends Grid {

    TriangleGrid(int rowSize, int colSize, int[][] initialConditions, int edgeType, int numStates) {
        super(rowSize, colSize, edgeType, numStates);
        myGrid = new Cell[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                myGrid[i][j] = new Cell(i, j, initialConditions[i][j]);
                myStateList[initialConditions[i][j]] += 1;
            }
        }
    }
    @Override
    public List<Cell> getNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        //get neighbors on same row
        for(int i = cell.getCol()-2; i <= cell.getCol() + 2; i++){
            neighbors.add(getCell(cell.getRow(), i));
        }
        //get neighbors in row w/ 3 neighbors
        int rowDisplacement;
        //triangle faces up
        if(cell.getCol()%2 == 1){
            rowDisplacement = -1;
        }
        else{
            rowDisplacement = 1;
        }
        for(int i = cell.getCol()-1; i <= cell.getCol()+1; i++){
            neighbors.add(getCell(cell.getRow()+ rowDisplacement, i));
        }

        //get neighbors in row w/5 neighbors
        if(cell.getCol()%2 == 1){
            rowDisplacement = 1;
        }
        else{
            rowDisplacement = -1;
        }
        for(int i = cell.getCol()-2; i <= cell.getCol()+2; i++){
            neighbors.add(getCell(cell.getRow()+ rowDisplacement, i));
        }
        return neighbors;
    }

    @Override
    protected Cell getWrappingCell(int row, int col) {
        return null;
    }

    @Override
    protected Cell getFiniteCell(int row, int col) {
        return null;
    }
}
