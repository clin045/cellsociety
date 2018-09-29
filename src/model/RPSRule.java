package model;

import java.util.List;

public class RPSRule extends Rule {
    @Override
    public void applyRule(Cell myCell, List<Cell> myNeighbors, int passNum) {

    }

    @Override
    public int getNeighborhoodSize() {
        return 0;
    }

    @Override
    public int getPasses() {
        return 0;
    }
}
