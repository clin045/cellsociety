package model.rule.foragingants;

import model.Cell;
import model.rule.Rule;

import java.util.List;

public class ForagingAntsRule extends Rule {
    public final int NUM_HOME_ANTS = 50;

    @Override
    public void applyRule(Cell cell, List<Cell> neighbors, int passNum) {
        //reset all ants' hasMoved
        if(passNum == 0){
            if(!((ForagingAntsCell) cell).isInitialized()){
                if (((ForagingAntsCell) cell).isHome()){
                    for(int i = 0; i < NUM_HOME_ANTS; i ++){
                        ((ForagingAntsCell) cell).getAntList().add(new Ant());
                    }
                }
            }
            List<Ant> antList = ((ForagingAntsCell) cell).getAntList();
            for(Ant a : antList){
                a.setHasMoved(false);
            }
        }
        else{

        }
    }

    @Override
    public int getNeighborhoodSize() {
        return 0;
    }

    @Override
    public Class getCellType() {
        return ForagingAntsRule.class;
    }

    @Override
    public int getPasses() {
        return 2;
    }
}
