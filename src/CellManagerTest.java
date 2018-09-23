import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CellManagerTest {
    @Test
    public void getNeighbors(){
        CellManager littleTest = new CellManager();
        int[][] init = new int[3][3];
        int counter = 0;
        for(int i = 0; i < init.length; i++){
            for(int j = 0; j < init.length; j++){
                init[i][j] = counter;
                counter ++;
            }
        }
        littleTest.initializeGrid(3,3,init, new GameOfLifeRule());
        assert(littleTest.getCell(0,3) == littleTest.getCell(0,0));
        assert(littleTest.getCell(0,-1) == littleTest.getCell(0,2));
        assert(littleTest.getCell(0,3) == littleTest.getCell(0,0));
        assert(littleTest.getCell(3,3) == littleTest.getCell(0,0));
        ArrayList<Cell> tester = littleTest.getNeighbors(littleTest.getCell(1,1));
        for(Cell c : tester){
            System.out.print(c.getCurrentState());
        }


    }

}