package model.rule.foragingants;

import model.Cell;

import java.util.ArrayList;
import java.util.List;

public class ForagingAntsCell extends Cell {
    private ArrayList<Ant> antList;
    private int homeLevel;
    private int foodLevel;
    private boolean isHome;

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public boolean isFood() {
        return isFood;
    }

    public void setFood(boolean food) {
        isFood = food;
    }

    private boolean isFood;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    private boolean initialized;

    public List<Ant> getAntList(){
        return antList;
    }

    public ForagingAntsCell(int row, int col, int initState) {
        super(row, col, initState);
        initialized = false;
    }
}
