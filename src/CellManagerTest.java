import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CellManagerTest {
    CellManager manager;

    CellManagerTest(){
        manager = new CellManager();
        int init[][] = new int[2][2];
        init[0][0] = 1;
        init[0][1] = 2;
        init[1][0] = 3;
        init[1][1] = 4;
        manager.initializeGrid(2,2,init);

    }
    @org.junit.jupiter.api.Test
    void initializeGrid() {
        assert(manager.getCell(0,0).getCurrentState() == 1);
        assert(manager.getCell(0,1).getCurrentState() == 2);
        assert(manager.getCell(1,0).getCurrentState() == 3);
        assert(manager.getCell(1,1).getCurrentState() == 4);
    }

    @Test
    void getNeighbors(){
        ArrayList<Cell> neighbors = manager.getNeighbors(manager.getCell(0,0));
        assert(neighbors.contains(manager.getCell(1,0)));
        assert(neighbors.contains(manager.getCell(0,1)));
        assert(neighbors.contains(manager.getCell(1,1)));
        assert(!neighbors.contains(manager.getCell(0,0)));

    }

    @Test
    void updateCells(){
        manager.setNextState(manager.getCell(0,0),5);
        manager.updateCells();
        assert(manager.getCell(0,0).getCurrentState() == 5);
    }
}