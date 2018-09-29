package model.rule.rps;

import model.Cell;
import model.rule.Rule;

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
    public Class getCellType() {
        return RPSCell.class;
    }

    @Override
    public int getPasses() {
        return 0;
    }
}
