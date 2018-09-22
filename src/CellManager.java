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
    public void initializeGrid(int rows, int cols, int[][] initConditions, RuleInterface activeRule){
        myRowSize = rows;
        myColSize = cols;
        myGrid = new Cell[rows][cols];
        myActiveRule = activeRule;
        for (int i = 0; i < initConditions.length; i ++){
            for(int j = 0; j < initConditions[0].length; j++){
                Cell myNewCell = new Cell(i,j, initConditions[i][j]);
                myGrid[i][j] = myNewCell;
            }
        }
    }



    public Cell getCell(int row, int col){
        return myGrid[row][col];
    }


    public ArrayList<Cell> getNeighbors(Cell cell) {
        var myNeighbors = new ArrayList<Cell>();
        int upperBound;
        int lowerBound;
        int leftBound;
        int rightBound;

        if(cell.getRow()-myNeighborhoodSize > 0){
            upperBound = cell.getRow()-myNeighborhoodSize;
        }
        else{
            upperBound = 0;
        }
        if(cell.getRow() + myNeighborhoodSize < myRowSize-1){
            lowerBound = cell.getRow() + myNeighborhoodSize;
        }
        else{
            lowerBound = myRowSize-1;
        }
        if(cell.getCol() - myNeighborhoodSize > 0){
            leftBound = cell.getCol() - myNeighborhoodSize;
        }
        else{
            leftBound = 0;
        }
        if(cell.getCol() + myNeighborhoodSize < myColSize-1){
            rightBound = cell.getCol() + myNeighborhoodSize;
        }
        else{
            rightBound = myColSize-1;
        }

        //add all non-wrapping neighbors
        for(int i = upperBound; i <= lowerBound; i++){
            for(int j = leftBound; j <= rightBound; j++){
                if(myGrid[i][j] != cell){
                    myNeighbors.add(myGrid[i][j]);
                }
            }
        }
        //add all wrapping neighbors
        int upperExceeded = myNeighborhoodSize - cell.getRow();
        int lowerExceeded = (cell.getRow()+myNeighborhoodSize) - (myRowSize-1);
        int leftExceeded = myNeighborhoodSize - cell.getCol();
        int rightExceeded = (cell.getCol()+myNeighborhoodSize) - (myColSize-1);

        if(upperExceeded > 0){
            for(int i = (myRowSize)-upperExceeded; i <= myRowSize-1; i ++){
                for(int j = leftBound; j <= rightBound; j++){
                    if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                        myNeighbors.add(myGrid[i][j]);
                    }
                }
                if(leftExceeded > 0 ){
                    for(int j = (myColSize)-leftExceeded; j <= myColSize-1; j ++){
                        if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
                if(rightExceeded > 0){
                    for(int j = 0; j <= rightExceeded-1; j++){
                        if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
            }
        }
        if(lowerExceeded > 0){
            for(int i = 0; i <= lowerExceeded-1; i++){
                for(int j = leftBound; j <= rightBound; j++){
                    if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                        myNeighbors.add(myGrid[i][j]);
                    }
                }
                if(leftExceeded > 0 ){
                    for(int j = (myColSize)-leftExceeded; j <= myColSize-1; j ++){
                        if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
                if(rightExceeded > 0){
                    for(int j = 0; j <= rightExceeded-1; j++){
                        if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
            }
        }
        if(leftExceeded > 0){
            for(int j = (myColSize)-leftExceeded; j <= myColSize-1; j ++){
                for(int i = upperBound; i <= lowerBound; i++){
                    if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                        myNeighbors.add(myGrid[i][j]);
                    }
                }
                if(upperExceeded > 0){
                    for(int i = (myRowSize)-upperExceeded; i <= myRowSize-1; i ++){
                        if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
                if(lowerExceeded > 0) {
                    for (int i = 0; i <= lowerExceeded - 1; i++) {
                        if (!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell) {
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
            }
        }
        if(rightExceeded > 0){
            for(int j = 0; j <= rightExceeded-1; j++){
                for(int i = upperBound; i <= lowerBound; i++){
                    if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                        myNeighbors.add(myGrid[i][j]);
                    }
                }
                if(upperExceeded > 0){
                    for(int i = (myRowSize)-upperExceeded; i <= myRowSize-1; i ++){
                        if(!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell){
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
                if(lowerExceeded > 0) {
                    for (int i = 0; i <= lowerExceeded - 1; i++) {
                        if (!myNeighbors.contains(myGrid[i][j]) && myGrid[i][j] != cell) {
                            myNeighbors.add(myGrid[i][j]);
                        }
                    }
                }
            }
        }
        //wrap corners
        if(upperExceeded > 0 && rightExceeded > 0){

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
        int numPasses = myActiveRule.getPasses();

        for(int i = 0; i < myRowSize; i++){
            for(int j = 0; j < myColSize; j++){
                Cell currentCell = myGrid[i][j];
                ArrayList<Cell> neighborList = getNeighbors(currentCell);
                for (int k = 0; k < numPasses; k++){
                    currentCell.setNextState(myActiveRule.applyRule(currentCell, neighborList, k));
                }

            }
        }
        updateCells();
    }
}
