import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Extension of main.RuleInterface to apply rules specifically for SegregationLife
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 * @author Christopher Lin cl349
 **/


public class PredatorPreyRule implements RuleInterface {

    public final int EMPTY = 0;
    public final int SHARK = 1;
    public final int FISH = 2;

    public final int FISH_REPRODUCTION_TIME = 1;
    public final int SHARK_REPRODUCTION_TIME = 2;

    public final static int PASSES = 2;

    private ArrayList<Fish> fishList;
    private ArrayList<Shark> sharkList;

    public int getPasses(){
        return PASSES;
    }
    public int getNeighborhoodSize(){
        return 1;
    }
    PredatorPreyRule(){
        fishList = new ArrayList<>();
        sharkList = new ArrayList<>();
    }
    public void applyRule(Cell cell, ArrayList<Cell> neighborsArray, int passNum) {
        if(passNum == 1){
            //maintain parity between cells and list of sharks/fish
            updateLists(cell);
        }

        if(passNum == 2){
            //throw out diagonal cells
            for (Cell c : neighborsArray) {
                if (c.getCol() != cell.getCol() && c.getRow() != cell.getRow()) {
                    neighborsArray.remove(c);
                }
            }
            moveFish(cell, neighborsArray);
            reproduceFish(cell, neighborsArray);
            sharkEatFish(cell, neighborsArray);
            reproduceShark(cell, neighborsArray);
        }


    }

    private void updateLists(Cell cell){
        if(cell.getCurrentState()==FISH){
            for(Fish f : fishList){
                if(f.getCell() == cell){
                    return;
                }
            }
            fishList.add(new Fish(cell));
        }
        if(cell.getCurrentState()==SHARK){
            for(Shark s : sharkList){
                if(s.getCell() == cell){
                    return;
                }
            }
            sharkList.add(new Shark(cell));
        }
    }

    private void moveFish(Cell cell, ArrayList<Cell> neighbors){
        if(!(cell.getNextState()==FISH)){
            return;
        }
        //move fish
        var emptyAdjacent = new ArrayList<Cell>();
        for(Cell c : neighbors){
            if (c.getNextState()==EMPTY){
                emptyAdjacent.add(c);
            }
        }
        if(emptyAdjacent.size()>0){
            cell.setNextState(EMPTY);
            int destinationCell = ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size());
            emptyAdjacent.get(destinationCell).setNextState(FISH);
            for(Fish f : fishList){
                if(f.getCell() == cell){
                    f.setAge(f.getAge()+1);
                    f.setCell(emptyAdjacent.get(destinationCell));
                }
            }
        }
    }

    private void reproduceFish(Cell cell, ArrayList<Cell> neighbors){
        if(!(cell.getNextState()==FISH)){
            return;
        }
        Fish currentFish = null;
        for(Fish f : fishList){
            if(f.getCell() == cell){
                currentFish = f;
            }
        }
        if(currentFish == null){
            System.out.println("Error: fish not found in fishList");
        }
        var emptyAdjacent = new ArrayList<Cell>();
        for(Cell c : neighbors){
            if (c.getNextState()==EMPTY){
                emptyAdjacent.add(c);
            }
        }
        if(currentFish.getAge() > FISH_REPRODUCTION_TIME && emptyAdjacent.size()>0){
            int destinationCell = ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size());
            emptyAdjacent.get(destinationCell).setNextState(FISH);
            fishList.add(new Fish(emptyAdjacent.get(destinationCell)));
            currentFish.setAge(0);
        }
    }
    private void sharkEatFish(Cell cell, ArrayList<Cell> neighbors){
        if(cell.getNextState() != SHARK){
            return;
        }
        //age shark
        for(Shark s : sharkList){
            if(s.getCell() == cell){
                s.setAge(s.getAge()+1);
            }
        }
        var fishNeighbors = new ArrayList<Cell>();
        for(Cell c : neighbors){
            if(c.getNextState() == FISH){
                fishNeighbors.add(c);
            }
        }
        if(fishNeighbors.size() > 0){
            int toEatCell = ThreadLocalRandom.current().nextInt(0, fishNeighbors.size());
            Cell fishToEat = fishNeighbors.get(toEatCell);
            for(Fish f : fishList){
                if(f.getCell() == fishToEat){
                    fishList.remove(f);
                }
            }
            fishToEat.setNextState(EMPTY);
        }
        else{
            moveShark(cell, neighbors);
        }
    }
    private void moveShark(Cell cell, ArrayList<Cell> neighbors){
        if(!(cell.getNextState()==SHARK)){
            return;
        }
        var emptyAdjacent = new ArrayList<Cell>();
        for(Cell c : neighbors){
            if (c.getNextState()==EMPTY){
                emptyAdjacent.add(c);
            }
        }
        if(emptyAdjacent.size()>0){
            cell.setNextState(EMPTY);
            int destinationCell = ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size());
            emptyAdjacent.get(destinationCell).setNextState(FISH);
            for(Shark s : sharkList){
                if(s.getCell() == cell){
                    s.setCell(emptyAdjacent.get(destinationCell));
                }
            }
        }
    }

    private void reproduceShark(Cell cell, ArrayList<Cell> neighbors){
        if(!(cell.getNextState()==SHARK)){
            return;
        }
        Shark currentShark = null;
        for(Shark s : sharkList){
            if(s.getCell() == cell){
                currentShark = s;
            }
        }
        if(currentShark == null){
            System.out.println("Error: shark not found in sharkList");
        }
        var emptyAdjacent = new ArrayList<Cell>();
        for(Cell c : neighbors){
            if (c.getNextState()==EMPTY){
                emptyAdjacent.add(c);
            }
        }
        if(currentShark.getAge() > SHARK_REPRODUCTION_TIME && emptyAdjacent.size()>0){
            int destinationCell = ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size());
            emptyAdjacent.get(destinationCell).setNextState(SHARK);
            sharkList.add(new Shark(emptyAdjacent.get(destinationCell)));
            currentShark.setAge(0);
        }
    }


}
