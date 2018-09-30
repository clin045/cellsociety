package xml;

import model.rule.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


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
            "shape",
            "edgeType",
            "gridLines",
            "cols",
            "rows",
            "configs",
            "neighbors",
            "colors"
    );
    static private final int SIM_NAME = 0;
    static private final int SIM_TITLE = 1;
    static private final int SIM_AUTHOR = 2;
    static private final int SHAPE = 3;
    static private final int EDGE_TYPE = 4;
    static private final int GRIDLINES = 5;
    static private final int COLS = 6;
    static private final int ROWS = 7;
    static private final int CONFIGS = 8;
    static private final int NEIGHBORS = 9;
    static private final int COLORS = 10;
    static private final int NEIGHBOR_COORDINATES_SIZE = 3;
    // specific data values for this instance
    private String mySimulationName;
    private String myTitle;
    private String myAuthor;
    private String myShape;
    private String myEdgeType;
    private int myGridLines;
    private int myRows;
    private int myCols;
    private String myConfigs;
    private String myNeighbors;
    private String myColors;
    private Rule myRule;
    // NOTE: keep just as an example for converting toString(), otherwise not used
    private Map<String, String> myDataValues;


    /**
     * Create game data from given data.
     */
    public Simulation(String simulationName, String title, String author, String shape, String edgeType, int gridLines, GeneralConfigurations configs) {
        mySimulationName = simulationName;
        myTitle = title;
        myAuthor = author;
        myShape = shape;
        myEdgeType = edgeType;
        myGridLines = gridLines;
        myRows = configs.getRows();
        myCols = configs.getCols();
        myNeighbors = configs.getNeighbors();
        myConfigs = configs.getConfigs();
        myColors = configs.getColors();
        myRule = UI.UIManager.findSimulationType(mySimulationName);
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
                dataValues.get(DATA_FIELDS.get(SHAPE)),
                dataValues.get(DATA_FIELDS.get(EDGE_TYPE)),
                Integer.parseInt(dataValues.get(DATA_FIELDS.get(GRIDLINES))),
                new GeneralConfigurations(Integer.parseInt(dataValues.get(DATA_FIELDS.get(COLS))),
                Integer.parseInt(dataValues.get(DATA_FIELDS.get(ROWS))),
                dataValues.get(DATA_FIELDS.get(CONFIGS)),
                dataValues.get(DATA_FIELDS.get(NEIGHBORS)),
                dataValues.get(DATA_FIELDS.get(COLORS))));
        myDataValues = dataValues;
    }

    private int[][] stringToIntArray(String arrayString, int xSize, int ySize) {
        String[] integerStringArray = arrayString.split(",");
        int[] oneDimArray = new int[integerStringArray.length];
        int counter = 0;
        for (String s : integerStringArray) {
            oneDimArray[counter] = Integer.parseInt(s);
            counter++;
        }
        counter = 0;
        int[][] resultArray = new int[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                resultArray[i][j] = oneDimArray[counter];
                counter++;
            }
        }
        return resultArray;
    }

    private int[][] generateRandomStates() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < (getCols() * getRows()); i++) {
            s.append(ThreadLocalRandom.current().nextInt(0, myRule.getNumStates())).append(",");
        }
        String resultString = s.toString();
        return stringToIntArray(resultString, myRows, myCols);
    }


    private boolean isValidSimName(String name) {
        String[] validSimulationNames = new String[]{"Game of Life", "Segregation", "Predator Prey", "Fire", "Rock Paper Scissors", "Foraging Ants", "Langton's Loop", "SugarScape"};

        for (String validName : validSimulationNames) {
            if (name.compareToIgnoreCase(validName) == 0) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasValidStates(int[][] cellStates) {
        for (int[] i : cellStates) {
            for (int j : i) {
                if (j < 0 || j > myRule.getNumStates()) {
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

    public String getShape() {
        if (myShape.compareToIgnoreCase("square") == 0 || myShape.compareToIgnoreCase("triangle") == 0) {
            if (myShape.compareToIgnoreCase("triangle") == 0 && ((getRows() % 2) == 0) && ((getCols() % 2) == 0)) {
                return myShape;
            } else {
                throw new XMLException("You have chosen triangle shape but inputted odd rows/cols values. Please enter even rows/cols value");
            }
        } else {
            throw new XMLException("Shape type is invalid. Please choose square or triangle");
        }
    }

    public String getEdgeType() {
        if (myEdgeType.compareToIgnoreCase("finite") == 0 || myEdgeType.compareToIgnoreCase("toroidal") == 0) {
            return myEdgeType;
        } else {
            throw new XMLException("Edge type is invalid. Please choose finite or toroidal");
        }
    }

    public boolean getGridLines() {
        if (myGridLines == 0 || myGridLines == 1) {
            return (myGridLines == 1);
        } else {
            throw new XMLException("Gridlines input must be either 0 or 1");
        }
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
        if (myConfigs.length() == 0) {
            return generateRandomStates();
        } else if (myConfigs.length() + 1 == 2 * getCols() * getRows()) {
            int[][] result = stringToIntArray(myConfigs, myRows, myCols);
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
        return stringToIntArray(myNeighbors, NEIGHBOR_COORDINATES_SIZE, NEIGHBOR_COORDINATES_SIZE);
    }

    public String getColors() {
        if (myColors.split(",").length == myRule.getNumStates()) {
                return myColors;
            } else if (myColors.split(",").length > myRule.getNumStates()){
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
