package xml;

import java.util.Date;
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
            "configs"
    );

    // specific data values for this instance
    private String mySimulationName;
    private String myTitle;
    private String myAuthor;
    private int myRows;
    private int myCols;
    private String myConfigs;
    // NOTE: keep just as an example for converting toString(), otherwise not used
    private Map<String, String> myDataValues;


    /**
     * Create game data from given data.
     */
    public Simulation(String simulationName, String title, String author, int rows, int cols, String configs) {
        mySimulationName = simulationName;
        myTitle = title;
        myAuthor = author;
        myRows = rows;
        myCols = cols;
        myConfigs = configs;
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
                dataValues.get(DATA_FIELDS.get(5)));
        myDataValues = dataValues;
    }

    private int[] StringToIntArray(String arrayString) {
        String[] integerString = arrayString.split(",");
        int [] result = new int[integerString.length];
        int counter = 0;
        for (String s : integerString) {
            result[counter] = Integer.parseInt(s);
            counter++;
        }
        return result;
    }

    // provide getters, not setters
    public String getSimulationName () {
        return mySimulationName;
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
        int[] configs = StringToIntArray(myConfigs);
        int counter = 0;
        int[][] resultConfigs = new int[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                resultConfigs[i][j] = configs[counter];
                counter++;
            }
        }
        return resultConfigs;
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
