package model.rule.foragingants;

import model.Cell;
import model.rule.Rule;

import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ForagingAntsRule extends Rule {
    public final int NEUTRAL = 0;
    public final int HOME = 1;
    public final int FOOD = 2;
    public final int OBSTACLE = 3;
    public final int DISPLAY_FOOD_PHEROMONE = 4;
    public final int DISPLAY_HOME_PHEROMONE = 5;


    public final int NUM_HOME_ANTS = 50;
    public final int MAX_HOME_LEVEL = 50;
    public final int MAX_FOOD_LEVEL = 50;
    public final int DESIRE_ADJUST = 2;
    public final int MAX_ANT_DENSITY = 10;
    public final double DISPLAY_FOOD_THRESHOLD = 10;
    public final double DISPLAY_HOME_THRESHOLD = 10;
    public final double EVAPORATION_RATE = 0.2;

    @Override
    public void applyRule(Cell cell, List<Cell> neighbors, int passNum) {
        //reset all ants' hasMoved
        if(passNum == 0){
            //initiialize things on first pass
            if(!((ForagingAntsCell) cell).isInitialized()){
                if(cell.getCurrentState() == HOME) {
                    ((ForagingAntsCell) cell).setHome(true);
                    for (int i = 0; i < NUM_HOME_ANTS; i++) {
                        ((ForagingAntsCell) cell).getAntList().add(new Ant());
                    }
                }
                if(cell.getCurrentState() == FOOD){
                    ((ForagingAntsCell) cell).setFood(true);
                }
                if(cell.getCurrentState() == OBSTACLE){
                    ((ForagingAntsCell) cell).setObstacle(true);
                }
            }
            List<Ant> antList = ((ForagingAntsCell) cell).getAntList();
            for(Ant a : antList){
                a.setHasMoved(false);
            }
            ((ForagingAntsCell) cell).setFoodLevel(((ForagingAntsCell) cell).getFoodLevel() * (1-EVAPORATION_RATE));
            ((ForagingAntsCell) cell).setHomeLevel(((ForagingAntsCell) cell).getHomeLevel()*(1-EVAPORATION_RATE));
        }
        else if(passNum == 1){
            List<Ant> currentAntList = ((ForagingAntsCell) cell).getAntList();
            for(Ant a : currentAntList){
                if(!a.getHasFoodItem() && ((ForagingAntsCell) cell).isFood()){
                    a.setHasFoodItem(true);
                }
                if(a.getHasFoodItem() && ((ForagingAntsCell) cell).isHome()){
                    a.setHasFoodItem(false);
                }
                if(!a.hasMoved()){
                    if(a.getHasFoodItem()){
                        returnToNest(a, (ForagingAntsCell) cell, neighbors);
                    }
                    else{
                        findFood(a, (ForagingAntsCell) cell, neighbors);
                    }
                }
            }
        }
        else {
            if (((ForagingAntsCell) cell).getFoodLevel() > DISPLAY_FOOD_THRESHOLD) {
                cell.setNextState(DISPLAY_FOOD_PHEROMONE);
            }
            if (((ForagingAntsCell) cell).getFoodLevel() > DISPLAY_HOME_THRESHOLD) {
                cell.setNextState(DISPLAY_HOME_PHEROMONE);
            }
            else{
                if(cell.getCurrentState() != HOME && cell.getCurrentState() != FOOD && cell.getCurrentState() != OBSTACLE){
                    cell.setNextState(NEUTRAL);
                }
            }
        }
    }

    private void findFood(Ant ant, ForagingAntsCell cell, List<Cell> neighbors) {
        List<Cell> possNeighbors = neighbors;
        dropHomePheromones(cell, getMaxHomePheromones(neighbors));
        //move ant
        for(int i = 0; i < possNeighbors.size(); i++){
            ForagingAntsCell currentCell = (ForagingAntsCell) possNeighbors.get(i);
            if(currentCell.isObstacle()){
                possNeighbors.remove(i);
            }
            if(currentCell.getAntList().size() > MAX_ANT_DENSITY){
                possNeighbors.remove(i);
            }
        }
        double[] indexProbs = new double[possNeighbors.size()];
        for(int i = 0; i < possNeighbors.size(); i++){
            indexProbs[i] = ((ForagingAntsCell) possNeighbors.get(i)).getFoodLevel();
        }
        int destIndex = indexSelector(indexProbs);
        ForagingAntsCell destCell = (ForagingAntsCell) possNeighbors.get(destIndex);
        destCell.getAntList().add(ant);
        cell.getAntList().remove(ant);
    }

    private int indexSelector(double[] indexProbs){
        double sum =0;
        for(double d : indexProbs){
            sum = sum + d;
        }

        double rand = ThreadLocalRandom.current().nextDouble(0, sum);
        int index = 0;
        double runningSum = 0;
        while(rand < runningSum){
            runningSum += indexProbs[index];
            index++;
        }
        return index;
    }

    private ForagingAntsCell getMaxFoodPheromones(List<Cell> neighbors){
        ForagingAntsCell maxFoodPheromones = (ForagingAntsCell) neighbors.get(0);
        for(Cell c : neighbors){
            if(((ForagingAntsCell)c).getFoodLevel() > maxFoodPheromones.getFoodLevel()){
                maxFoodPheromones = (ForagingAntsCell) c;
            }
        }
        return maxFoodPheromones;
    }
    private ForagingAntsCell getMaxHomePheromones(List<Cell> neighbors){
        ForagingAntsCell maxHomePheromones = (ForagingAntsCell) neighbors.get(0);
        for(Cell c : neighbors){
            if(((ForagingAntsCell)c).getHomeLevel() > maxHomePheromones.getHomeLevel()){
                maxHomePheromones = (ForagingAntsCell) c;
            }
        }
        return maxHomePheromones;
    }


    private void returnToNest(Ant ant, ForagingAntsCell cell, List<Cell> neighbors) {
            ForagingAntsCell maxFoodPheromones = getMaxFoodPheromones(neighbors);
            ForagingAntsCell maxHomePheromones = getMaxHomePheromones(neighbors);
            dropFoodPheromones(cell, maxFoodPheromones);
            //move ant
            maxHomePheromones.getAntList().add(ant);
            cell.getAntList().remove(ant);
            ant.setHasMoved(true);

    }

    private void dropHomePheromones(ForagingAntsCell cell, ForagingAntsCell maxCell) {
        if(cell.isHome()){
            cell.setHomeLevel(MAX_HOME_LEVEL);
        }
        else{
            double maxLevel = maxCell.getHomeLevel();
            double desired = maxLevel - DESIRE_ADJUST;
            double toDeposit = desired - cell.getHomeLevel();
            if(toDeposit > 0){
                cell.setHomeLevel(cell.getHomeLevel()+toDeposit);
            }
        }
    }

    private void dropFoodPheromones(ForagingAntsCell cell, ForagingAntsCell maxCell) {
        if(cell.isFood()){
            cell.setFoodLevel(MAX_FOOD_LEVEL);
        }
        else{
            double maxLevel = maxCell.getFoodLevel();
            double desired = maxLevel - DESIRE_ADJUST;
            double toDeposit = desired - cell.getFoodLevel();
            if(toDeposit > 0){
                cell.setFoodLevel(cell.getFoodLevel()+toDeposit);
            }
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
        return 3;
    }
}
