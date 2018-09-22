import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Extension of main.RuleInterface to apply rules specifically for SegregationLife
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
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
    public void applyRule(Cell cell, ArrayList<Cell> neighborsArray, int passNum) {
        if(passNum == 1){
            //maintain parity between cells and list of sharks/fish
            updateLists(cell);
        }
        //throw out diagonal cells
        for (Cell c : neighborsArray) {
            if (c.getCol() != cell.getCol() && c.getRow() != cell.getRow()) {
                neighborsArray.remove(c);
            }
        }
        if(passNum == 2){
            moveFish(cell, neighborsArray);
        }


    }

    private void updateLists(Cell cell){
        if(cell.getCurrentState()==FISH){
            for(Fish f : fishList){
                if(f.getCell() == cell){
                    return;
                }
            }
            fishList.add(new Fish(cell,0));
        }
        if(cell.getCurrentState()==SHARK){
            for(Shark s : sharkList){
                if(s.getCell() == cell){
                    return;
                }
            }
            sharkList.add(new Shark(cell,0));
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
                    f.incAge();
                    f.setCell(emptyAdjacent.get(destinationCell));
                }
            }
        }



    }

}
