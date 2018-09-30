package UI;

import model.rule.Rule;
import model.rule.fire.FireRule;
import model.rule.foragingants.ForagingAntsRule;
import model.rule.gameoflife.GameOfLifeRule;
import model.rule.predatorprey.PredatorPreyRule;
import model.rule.rps.RPSRule;
import model.rule.segregation.SegregationRule;

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

    ConfigurationManager(int[][] initialStates, int rows, int columns, String title, String name, String author, String[] colors, String shape, String edgeType, int[][] neighbors){
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
}
