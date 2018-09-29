package xml;

import model.rule.Rule;
import UIManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Simple immutable value object representing game product data.
 *
 * @author Robert C. Duvall
 * @author Scott McConnell
 */
public class Simulation {
    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "Simulation";
    // field names expected to appear in data file holding values for this object
    // NOTE: simple way to create an immutable list
    public static final List<String> DATA_FIELDS = List.of(
            "simulationName",
            "title",
            "author",
            "cols",
            "rows",
            "configs",
            "neighbors",
            "colors"
    );
    static private final int SIM_NAME = 0;
    static private final int SIM_TITLE = 1;
    static private final int SIM_AUTHOR = 2;
    static private final int COLS = 3;
    static private final int ROWS = 4;
    static private final int CONFIGS = 5;
    static private final int NEIGHBORS = 6;
    static private final int COLORS = 7;
    // specific data values for this instance
    private String mySimulationName;
    private String myTitle;
    private String myAuthor;
    private int myRows;
    private int myCols;
    private String myConfigs;
    private String myNeighbors;
    private String myColors;
    // NOTE: keep just as an example for converting toString(), otherwise not used
    private Map<String, String> myDataValues;


    /**
     * Create game data from given data.
     */
    public Simulation(String simulationName, String title, String author, GeneralConfigurations configs) {
        mySimulationName = simulationName;
        myTitle = title;
        myAuthor = author;
        myRows = configs.getRows();
        myCols = configs.getCols();
        myNeighbors = configs.getNeighbors();
        myConfigs = configs.getConfigs();
        myColors = configs.getColors();
        // NOTE: this is useful so our code does not fail due to a NullPointerException
        myDataValues = new HashMap<>();
    }

    /**
     * Create game data from a data structure of Strings.
     *
     * @param dataValues map of field names to their values
     */
    public Simulation(Map<String, String> dataValues) {
        this(dataValues.get(DATA_FIELDS.get(SIM_NAME)),
                dataValues.get(DATA_FIELDS.get(SIM_TITLE)),
                dataValues.get(DATA_FIELDS.get(SIM_AUTHOR)),
                new GeneralConfigurations(Integer.parseInt(dataValues.get(DATA_FIELDS.get(COLS))),
                Integer.parseInt(dataValues.get(DATA_FIELDS.get(ROWS))),
                dataValues.get(DATA_FIELDS.get(CONFIGS)),
                dataValues.get(DATA_FIELDS.get(NEIGHBORS)),
                dataValues.get(DATA_FIELDS.get(COLORS))));
        myDataValues = dataValues;
    }

    private int[][] stringToIntArray(String arrayString) {
        String[] integerStringArray = arrayString.split(",");
        int[] oneDimArray = new int[integerStringArray.length];
        int counter = 0;
        for (String s : integerStringArray) {
            oneDimArray[counter] = Integer.parseInt(s);
            counter++;
        }
        counter = 0;
        int[][] resultArray = new int[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                resultArray[i][j] = oneDimArray[counter];
                counter++;
            }
        }
        return resultArray;
    }

    private int getNumStates() {
        Rule currentRule = UIManager.findSimulationType(mySimulationName);
        return currentRule.getNumStates();
    }

    private boolean isValidSimName(String name) {
        String[] validSimulationNames = new String[]{"Game of Life", "Segregation", "Predator Prey", "Fire", "Rock, Paper, Scissors", "Foraging Ants", "Langton's Loop", "SugarScape"};

        for (String validName : validSimulationNames) {
            if (name.equals(validName)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasValidStates(int[][] cellStates) {
        int maxStateAllowed = 3;
        for (int[] i : cellStates) {
            for (int j : i) {
                if (j < 0 || j > maxStateAllowed) {
                    return false;
                }
            }
        }
        return true;
    }

    // provide getters, not setters
    public String getSimulationName() throws XMLException {
        if (isValidSimName(mySimulationName)) {
            return mySimulationName;
        } else {
            throw new XMLException("Invalid Simulation Name");
        }
    }

    public String getTitle() {
        return myTitle;
    }

    public String getAuthor() {
        return myAuthor;
    }

    public int getCols() throws XMLException {
        if (myCols != 0) {
            return Math.abs(myCols);
        } else {
            throw new XMLException("Number of Columns must be nonzero");
        }
    }

    public int getRows() throws XMLException {
        if (myRows != 0) {
            return Math.abs(myRows);
        } else {
            throw new XMLException("Number of Rows must be nonzero");
        }
    }

    public int[][] getConfigs() throws XMLException {
        if (myConfigs.length() + 1 == 2 * getCols() * getRows()) {
            int[][] result = stringToIntArray(myConfigs);
            if (hasValidStates(result)) {
                return result;
            } else {
                throw new XMLException("Cell configuration contains states that are not allowed for this rule");
            }
        } else {
            throw new XMLException("Cell configuration contains cell locations that are outside the bounds of the grid size");
        }
    }

    public int[][] getNeighborCoordinates() {
        return stringToIntArray(myNeighbors);
    }

    public String getColors() {
        int maxStateAllowed = 3;
        if (myColors.split(",").length == maxStateAllowed) {
                return myColors;
            } else if (myColors.split(",").length > maxStateAllowed){
                throw new XMLException("Too many colors provided in XML file: Number of colors must equal number of states");
            } else {
            throw new XMLException("Not enough colors provided in XML file: Number of colors must equal number of states");
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        var result = new StringBuilder();
        result.append(DATA_TYPE + " {\n");
        for (var e : myDataValues.entrySet()) {
            result.append("  ").append(e.getKey()).append("='").append(e.getValue()).append("',\n");
        }
        result.append("}\n");
        return result.toString();
    }
}
