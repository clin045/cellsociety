package model;

import model.Cell;

import java.util.ArrayList;

/**
 ** The Rule interface can be extended to create different simulations. Implementing rules as an interface
 provides a flexible system for implementing different kinds of simulations. Rule does not edit cells, it only
 generates the next state; editing cells is the sole responsibility of main.model.CellManager.

 ** @author Scott McConnell skm44
 **/
public interface RuleInterface {
    void applyRule(Cell myCell, ArrayList<Cell> myNeighbors, int passNum);
    int getNeighborhoodSize();
    int getPasses();

}