import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import xml.Simulation;
import xml.XMLException;
import xml.XMLParser;

import java.io.File;
import java.util.ResourceBundle;

/**
 * This class manages the UI and interacts with the XML parser and the cell manager.
 *
 * @author Allen Qiu (asq3)
 */
public class UIManager extends Application {
    private static final double MILLISECOND_DELAY = 300;
    private static final int WINDOW_SIZE = 600;
    private static final int USABLE_WINDOW_SIZE = 500;
    private static final int PADDING_SIZE = 10;
    private static final int HORIZONTAL_GUI_GAP = 5;
    private static final int VERTICAAL_GUI_GAP = 5;
    private static final String DEFAULT_RESOURCE_PACKAGE = "English";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
    private File chosen;
    private CellManager myCellManager;
    private GridPane simulatorGridPane;
    private int rows;
    private int columns;
    private String title;
    private String author;
    private String simulationName;
    private String[] colors;
    private Timeline animation = new Timeline();
    private Stage myStage;
    private GraphManager myGraph;

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage stage) {
        myStage = stage;

        Text chooseFileLabel = new Text(myResources.getString("ChooseFileLabel"));
        Text fileName = new Text(myResources.getString("NoFile"));

        Button loadButton = new Button(myResources.getString("SelectButton"));
        loadButton.setOnAction(event -> {
            chooseFile();
            if (chosen != null) {
                fileName.setText(chosen.getName());
            }
        });

        Button startButton = new Button(myResources.getString("StartButton"));
        startButton.setOnAction(event -> createSimulator());

        GridPane myGridPane = new GridPane();
        myGridPane.setMinSize(WINDOW_SIZE, WINDOW_SIZE);
        myGridPane.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        myGridPane.setVgap(VERTICAAL_GUI_GAP);
        myGridPane.setHgap(HORIZONTAL_GUI_GAP);
        myGridPane.setAlignment(Pos.CENTER);

        myGridPane.add(chooseFileLabel, 0, 0);
        myGridPane.add(fileName, 0, 1);
        myGridPane.add(loadButton, 1, 1);
        myGridPane.add(startButton, 0, 2);

        Scene myScene = new Scene(myGridPane);

        myStage.setTitle(myResources.getString("WindowTitle"));
        myStage.setScene(myScene);
        myStage.show();
    }

    private void createSimulator() {
        initializeWindow();

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.playFromStart();

    }

    private void initializeWindow() {
        try {
            int[][] initialStates = readConfiguration();

            myGraph = new GraphManager(colors.length, colors);
            myGraph.showChart();

            GridPane rootPane = new GridPane();
            rootPane.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
            rootPane.setMinSize(WINDOW_SIZE, WINDOW_SIZE);
            rootPane.setAlignment(Pos.CENTER);

            createAnimationBlock(initialStates);

            rootPane.add(createTitleBlock(), 0, 0);
            rootPane.add(simulatorGridPane, 0, 1);
            rootPane.add(createControlsBlock(), 0, 2);

            Rule myRule = findSimulationType(simulationName);
            myCellManager = new CellManager(rows, columns, initialStates, myRule, CellManager.SQUARE_GRID);

            myStage.setScene(new Scene(rootPane));
        } catch (XMLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("XML File Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            chooseFile();
            initializeWindow();
        }
    }

    private void chooseFile() {
        FileChooser myFileChooser = new FileChooser();
        myFileChooser.setTitle(myResources.getString("ChooserWindowTitle"));
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        myFileChooser.getExtensionFilters().add(xmlFilter);
        myFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chosen = myFileChooser.showOpenDialog(myStage);
    }

    private int[][] readConfiguration() {
        Simulation configs = new XMLParser("media").getSimulation(chosen);
        rows = configs.getRows();
        columns = configs.getCols();
        title = configs.getTitle();
        author = configs.getAuthor();
        simulationName = configs.getSimulationName();
        int[][] initialStates = configs.getConfigs();
        colors = configs.getColors().split(",");
        return initialStates;
    }

    private Rule findSimulationType(String name) {
        Rule myRule;
        if (name.compareToIgnoreCase("Game of Life") == 0) {
            myRule = new GameOfLifeRule();
        } else if (name.compareToIgnoreCase("Predator Prey") == 0) {
            myRule = new PredatorPreyRule();
        } else if (name.compareToIgnoreCase("Fire") == 0) {
            myRule = new FireRule();
        } else {
            myRule = new SegregationRule();
        }
        return myRule;
    }

    private GridPane createTitleBlock() {
        Label myTitle = new Label(title);
        Label myAuthor = new Label(author);
        Label mySimulationName = new Label(simulationName);

        GridPane displayInfo = new GridPane();
        displayInfo.setAlignment(Pos.CENTER);
        displayInfo.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        displayInfo.add(myTitle, 0, 0);
        GridPane.setHalignment(myTitle, HPos.CENTER);
        displayInfo.add(myAuthor, 0, 1);
        GridPane.setHalignment(myAuthor, HPos.CENTER);
        displayInfo.add(mySimulationName, 0, 2);
        GridPane.setHalignment(mySimulationName, HPos.CENTER);

        return displayInfo;
    }

    private void createAnimationBlock(int[][] initialStates) {
        simulatorGridPane = new GridPane();
        simulatorGridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                simulatorGridPane.add(createCell(colors[initialStates[i][j]], rows, columns), i, j);
            }
        }
    }

    private FlowPane createControlsBlock() {
        FlowPane controls = new FlowPane();
        controls.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        controls.setAlignment(Pos.CENTER);

        Button play = new Button(myResources.getString("PlayButton"));
        play.setOnAction(event -> animation.play());

        Button pause = new Button(myResources.getString("PauseButton"));
        pause.setOnAction(event -> animation.pause());

        Button step = new Button(myResources.getString("StepButton"));
        step.setOnAction(event -> step());

        Button halfSpeed = new Button(myResources.getString("HalfSpeedButton"));
        halfSpeed.setOnAction(event -> animation.setRate(.5));

        Button normalSpeed = new Button(myResources.getString("NormalSpeedButton"));
        normalSpeed.setOnAction(event -> animation.setRate(1));

        Button doubleSpeed = new Button(myResources.getString("DoubleSpeedButton"));
        doubleSpeed.setOnAction(event -> animation.setRate(2));

        Button newSimulation = new Button(myResources.getString("NewSimulation"));
        newSimulation.setOnAction(event -> {
            chooseFile();
            createSimulator();
        });

        controls.getChildren().addAll(play, pause, step, halfSpeed, normalSpeed, doubleSpeed, newSimulation);

        return controls;
    }

    private Node getNodeFromGridPane(int col, int row) {
        for (Node node : simulatorGridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void step() {
        myCellManager.nextGeneration();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                BorderPane thisCellPane = (BorderPane) getNodeFromGridPane(i, j);
                Cell thisCell = myCellManager.getGrid().getCell(i, j);
                if (thisCellPane != null) {
                    updateCellAppearance(colors[thisCell.getCurrentState()], thisCellPane);
                }
            }
        }
        myGraph.updateGraph(myCellManager.getGrid().getStateList());
    }

    private void updateCellAppearance(String color, BorderPane myCell) {
        myCell.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #" + color + ";" +
                "-fx-border-width: 1;");
    }

    private BorderPane createCell(String color, int rows, int columns) {
        BorderPane cell = new BorderPane();
        int cellSize = USABLE_WINDOW_SIZE / Math.max(rows, columns);
        cell.setMinSize(cellSize, cellSize);
        cell.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #" + color + ";" +
                "-fx-border-width: 1;");
        cell.setOnMouseClicked(event -> toggleNextState(cell));
        return cell;
    }

    private void toggleNextState(BorderPane cell) {
        int numStates = colors.length;
        Cell thisCell = myCellManager.getGrid().getCell(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
        if (thisCell.getCurrentState() == numStates - 1) {
            thisCell.setCurrentState(0);
            thisCell.setNextState(0);
        } else {
            thisCell.setNextState(thisCell.getCurrentState() + 1);
            thisCell.setCurrentState(thisCell.getCurrentState() + 1);
        }
        updateCellAppearance(colors[thisCell.getNextState()], cell);
    }
}
