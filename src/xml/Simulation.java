package xml;

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
    public Simulation(String simulationName, String title, String author, int rows, int cols, String configs, String neighbors, String colors) {
        mySimulationName = simulationName;
        myTitle = title;
        myAuthor = author;
        myRows = rows;
        myCols = cols;
        myNeighbors = neighbors;
        myConfigs = configs;
        myColors = colors;
        // NOTE: this is useful so our code does not fail due to a NullPointerException
        myDataValues = new HashMap<>();
    }

    /**
     * Create game data from a data structure of Strings.
     *
     * @param dataValues map of field names to their values
     */
    public Simulation(Map<String, String> dataValues) {
        this(dataValues.get(DATA_FIELDS.get(0)),
                dataValues.get(DATA_FIELDS.get(1)),
                dataValues.get(DATA_FIELDS.get(2)),
                Integer.parseInt(dataValues.get(DATA_FIELDS.get(3))),
                Integer.parseInt(dataValues.get(DATA_FIELDS.get(4))),
                dataValues.get(DATA_FIELDS.get(5)),
                dataValues.get(DATA_FIELDS.get(6)),
                dataValues.get(DATA_FIELDS.get(7)));
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

    private boolean isValidSimName(String name) {
        String[] validSimulationNames = new String[8];
        validSimulationNames[0] = "Game of Life";
        validSimulationNames[1] = "Segregation";
        validSimulationNames[2] = "Predator Prey";
        validSimulationNames[3] = "Fire";
        validSimulationNames[4] = "Rock, Paper, Scissors";
        validSimulationNames[5] = "Foraging Ants";
        validSimulationNames[6] = "Langton's Loop";
        validSimulationNames[7] = "SugarScape";

        for (String validName : validSimulationNames) {
            if (name.equals(validName)) { return true; }
        }
        return false;
    }

    // provide getters, not setters
    public String getSimulationName () throws XMLException {

        if (isValidSimName(mySimulationName)) {
            return mySimulationName;
        } else {
            throw new XMLException("Invalid Simulation Name");
        }
    }

    public String getTitle () {
        return myTitle;
    }

    public String getAuthor () {
        return myAuthor;
    }

    public int getCols () {
        return myCols;
    }

    public int getRows () {
        return myRows;
    }

    public int[][] getConfigs () {
        return stringToIntArray(myConfigs);
    }

    public int[][] getNeighborCoordinates () {
        return stringToIntArray(myNeighbors);
    }

    public String getColors () {
        return myColors;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        var result = new StringBuilder();
        result.append(DATA_TYPE + " {\n");
        for (var e : myDataValues.entrySet()) {
            result.append("  ").append(e.getKey()).append("='").append(e.getValue()).append("',\n");
        }
        result.append("}\n");
        return result.toString();
    }
}
