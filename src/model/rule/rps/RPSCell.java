package model.rule.rps;

import model.Cell;

public class RPSCell extends Cell {
    int rock_level;
    int paper_level;
    int scissor_level;
    RPSCell(int row, int col, int initState) {
        super(row, col, initState);
    }


}
