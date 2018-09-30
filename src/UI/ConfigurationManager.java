package UI;

import javafx.scene.control.Slider;
import model.rule.Rule;
import model.rule.fire.FireRule;
import model.rule.foragingants.ForagingAntsRule;
import model.rule.gameoflife.GameOfLifeRule;
import model.rule.predatorprey.PredatorPreyRule;
import model.rule.rps.RPSRule;
import model.rule.segregation.SegregationRule;

import java.util.ArrayList;

public class ConfigurationManager {
    private int[][] myInitialStates;
    private int myRows;
    private int myColumns;
    private String myTitle;
    private String mySimulationName;
    private String myDescription;
    private String myAuthor;
    private String[] myColors;
    private String myShape;
    private String myEdgeType;
    private int[][] myNeighbors;

    ConfigurationManager(int[][] initialStates, int rows, int columns, String title, String name, String author, String[] colors, String shape, String edgeType, int[][] neighbors, String description){
        myInitialStates = initialStates;
        myRows = rows;
        myColumns = columns;
        myTitle = title;
        mySimulationName = name;
        myAuthor = author;
        myColors = colors;
        myShape = shape;
        myEdgeType = edgeType;
        myNeighbors = neighbors;
        myDescription = description;
    }

    public int[][] getInitialStates(){
        return myInitialStates;
    }

    public int getRows(){
        return myRows;
    }

    public int getColumns(){
        return myColumns;
    }

    public String getDescription(){
        return myDescription;
    }

    public String getTitle(){
        return myTitle;
    }

    public String getSimulationName(){
        return mySimulationName;
    }

    public String getAuthor(){
        return myAuthor;
    }

    public String[] getColors(){
        return myColors;
    }

    public String getShape(){
        return myShape;
    }

    public String getEdgeType(){
        return myEdgeType;
    }

    public int[][] getNeighbors(){
        return myNeighbors;
    }

    public static Rule findSimulationType(String name) {
        Rule rule;
        if (name.compareToIgnoreCase("Game of Life") == 0) {
            rule = new GameOfLifeRule();
        } else if (name.compareToIgnoreCase("Predator Prey") == 0) {
            rule = new PredatorPreyRule();
        } else if (name.compareToIgnoreCase("Fire") == 0) {
            rule = new FireRule();
        } else if (name.compareToIgnoreCase("Rock Paper Scissors") == 0) {
            rule = new RPSRule();
        } else if (name.compareToIgnoreCase("Foraging Ants") == 0) {
            rule = new ForagingAntsRule();
        } else {
            rule = new SegregationRule();
        }
        return rule;
    }

    public ArrayList<Slider> getSliders(Rule myRule){
        ArrayList<Slider> mySliders = new ArrayList<>();
        if(mySimulationName.compareToIgnoreCase("Fire") == 0){
            Slider fireProb = new Slider(0, 1, ((FireRule)myRule).getProbability());
            fireProb.setShowTickLabels(true);
            fireProb.setShowTickMarks(true);
            fireProb.setBlockIncrement(.1);
            fireProb.valueProperty().addListener((observable, oldValue, newValue) -> ((FireRule) myRule).setProbability(newValue.doubleValue()));
            mySliders.add(fireProb);
        }
        else if(mySimulationName.compareToIgnoreCase("Segregation") == 0){
            Slider tolerance = new Slider(0, 1, ((SegregationRule)myRule).getTolerance());
            tolerance.setShowTickLabels(true);
            tolerance.setShowTickMarks(true);
            tolerance.setBlockIncrement(.1);
            tolerance.valueProperty().addListener((observable, oldValue, newValue) -> ((SegregationRule) myRule).setTolerance(newValue.doubleValue()));
            mySliders.add(tolerance);
        }
        else if(mySimulationName.compareToIgnoreCase("Predator Prey") == 0){
            Slider fishTime = new Slider(1, 30, ((PredatorPreyRule)myRule).getFishReproductionTime());
            Slider sharkTime = new Slider(1, 30, ((PredatorPreyRule)myRule).getSharkReproductionTime());
            fishTime.setShowTickLabels(true);
            sharkTime.setShowTickLabels(true);
            fishTime.setShowTickMarks(true);
            sharkTime.setShowTickLabels(true);
            sharkTime.setBlockIncrement(1);
            fishTime.setBlockIncrement(1);
            fishTime.valueProperty().addListener((observable, oldValue, newValue) -> ((PredatorPreyRule) myRule).setFishReproductionTime(newValue.intValue()));
            sharkTime.valueProperty().addListener((observable, oldValue, newValue) -> ((PredatorPreyRule)myRule).setSharkReproductionTime(newValue.intValue()));
            mySliders.add(sharkTime);
            mySliders.add(fishTime);
        }
        return mySliders;
    }

    public GridUI createGridUI(Rule myRule){
        if(myShape.compareToIgnoreCase("square") == 0){
            return new SquareGridUI(myInitialStates, myRows, myColumns, myColors, myRule, myNeighbors, myEdgeType);
        }
        return new TriangleGridUI(myInitialStates, myRows, myColumns, myColors, myRule, myNeighbors, myEdgeType);
    }
}
