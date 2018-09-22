import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CellManagerTest {
    CellManager littleGrid;
    CellManager bigGrid;

    CellManagerTest(){
        littleGrid = new CellManager();
        int littleInit[][] = new int[2][2];
        littleInit[0][0] = 1;
        littleInit[0][1] = 2;
        littleInit[1][0] = 3;
        littleInit[1][1] = 4;
        littleGrid.initializeGrid(2,2,littleInit, new GameOfLifeRule());

        bigGrid = new CellManager();
        int bigInit[][] = new int[3][3];
        for (int i = 0; i < bigInit.length; i++){
            for(int j = 0; j < bigInit[0].length; j++){
                bigInit[i][j] = 0;
            }
        }
        bigInit[1][1] = 1;
        bigGrid.initializeGrid(3,3,bigInit,new GameOfLifeRule());

    }
    @org.junit.jupiter.api.Test
    void initializeGrid() {
        assert(littleGrid.getCell(0,0).getCurrentState() == 1);
        assert(littleGrid.getCell(0,1).getCurrentState() == 2);
        assert(littleGrid.getCell(1,0).getCurrentState() == 3);
        assert(littleGrid.getCell(1,1).getCurrentState() == 4);
    }




    @Test
    void updateCells(){
        littleGrid.setNextState(littleGrid.getCell(0,0),5);
        littleGrid.updateCells();
        assert(littleGrid.getCell(0,0).getCurrentState() == 5);
    }
    @Test
    void nextGeneration(){
        assert(bigGrid.getCell(1,1).getCurrentState() == 1);
        bigGrid.nextGeneration();
        assert(bigGrid.getCell(1,1).getCurrentState() == 0);
    }

    @Test
    void getNeighbors(){
        ArrayList<Cell> neighborlist = littleGrid.getNeighbors(littleGrid.getCell(0,0));
        assert(neighborlist.size()==3);
        neighborlist = bigGrid.getNeighbors(bigGrid.getCell(1,1));
        assert(neighborlist.size() == 8);
        neighborlist = bigGrid.getNeighbors((bigGrid.getCell(0,1)));
        assert(neighborlist.size() == 8);
        neighborlist = bigGrid.getNeighbors((bigGrid.getCell(1,0)));
        assert(neighborlist.size()==8);
        neighborlist = bigGrid.getNeighbors((bigGrid.getCell(0,0)));
        assert(neighborlist.size()==8);
        neighborlist = bigGrid.getNeighbors((bigGrid.getCell(2,2)));
        assert (neighborlist.size() == 8);
    }
}