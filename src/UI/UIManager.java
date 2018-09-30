package UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.rule.Rule;
import model.rule.fire.FireRule;
import model.rule.foragingants.ForagingAntsRule;
import model.rule.gameoflife.GameOfLifeRule;
import model.rule.predatorprey.PredatorPreyRule;
import model.rule.rps.RPSRule;
import model.rule.segregation.SegregationRule;
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
    private static final int PADDING_SIZE = 10;
    private static final int HORIZONTAL_GUI_GAP = 5;
    private static final int VERTICAL_GUI_GAP = 5;
    private static final String DEFAULT_RESOURCE_PACKAGE = "English";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
    private File chosen;
    private GridPane simulatorGridPane;
    private int rows;
    private int columns;
    private String title;
    private String author;
    private String simulationName;
    //private String description;
    private int[][] neighbors;
    private String[] colors;
    private String shape;
    private String edgeType;
    private Timeline animation = new Timeline();
    private Stage myStage;
    private GraphManager myGraph;
    private GridUI myGridUI;
    private Rule myRule;

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
        myGridPane.setVgap(VERTICAL_GUI_GAP);
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
            myRule = findSimulationType(simulationName);

            myGraph = new GraphManager(colors.length, colors);

            if(shape.compareToIgnoreCase("square") == 0){
                myGridUI = new SquareGridUI(initialStates, rows, columns, colors, myRule, neighbors, edgeType);
            }
            else {
                myGridUI = new TriangleGridUI(initialStates, rows, columns, colors, myRule, neighbors, edgeType);
            }

            GridPane rootPane = new GridPane();
            rootPane.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
            rootPane.setMinSize(WINDOW_SIZE, WINDOW_SIZE);
            rootPane.setAlignment(Pos.CENTER);

            rootPane.add(createTitleBlock(), 0, 0);
            rootPane.add(myGridUI.getGridPane(), 0, 1);
            rootPane.add(createControlsBlock(), 0, 2);

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
        chosen = null;
        while(chosen == null){
            chosen = myFileChooser.showOpenDialog(myStage);
        }
    }

    private int[][] readConfiguration() {
        Simulation configs = new XMLParser("media").getSimulation(chosen);
        rows = configs.getRows();
        columns = configs.getCols();
        title = configs.getTitle();
        author = configs.getAuthor();
        simulationName = configs.getSimulationName();
        int[][] initialStates = configs.getConfigs();
        neighbors = configs.getNeighborCoordinates();
        colors = configs.getColors().split(",");
        shape = configs.getShape();
        edgeType = configs.getEdgeType();
        //description = configs.getDescription();
        return initialStates;
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

    private GridPane createTitleBlock() {
        Label myTitle = new Label(title);
        Label myAuthor = new Label(author);
        Label mySimulationName = new Label(simulationName);
        //Label myDescription = new Label(description);

        GridPane displayInfo = new GridPane();
        displayInfo.setAlignment(Pos.CENTER);
        displayInfo.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        displayInfo.add(myTitle, 0, 0);
        GridPane.setHalignment(myTitle, HPos.CENTER);
        displayInfo.add(myAuthor, 0, 1);
        GridPane.setHalignment(myAuthor, HPos.CENTER);
        displayInfo.add(mySimulationName, 0, 2);
        GridPane.setHalignment(mySimulationName, HPos.CENTER);
        //displayInfo.add(myDescription, 0, 3);
        //GridPane.setHalignment(myDescription, HPos.CENTER);

        return displayInfo;
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
        newSimulation.setOnAction(event -> newSimulation());

        Button toggleChart = new Button(myResources.getString("ToggleChart"));
        toggleChart.setOnAction(event -> myGraph.toggleChart());

        Button reset = new Button(myResources.getString("Reset"));
        reset.setOnAction(event -> createSimulator());

        controls.getChildren().addAll(play, pause, step, halfSpeed, normalSpeed, doubleSpeed, newSimulation, toggleChart, reset);

        if(simulationName.compareToIgnoreCase("Fire") == 0){
            Slider fireProb = new Slider();
            fireProb.setMin(0);
            fireProb.setMax(1);
            fireProb.setValue(((FireRule)myRule).getProbability());
            fireProb.setShowTickLabels(true);
            fireProb.setShowTickMarks(true);
            fireProb.setBlockIncrement(.1);
            fireProb.valueProperty().addListener((observable, oldValue, newValue) -> ((FireRule) myRule).setProbability(newValue.doubleValue()));
            controls.getChildren().add(fireProb);
        }
        else if(simulationName.compareToIgnoreCase("Segregation") == 0){
            Slider tolerance = new Slider();
            tolerance.setMin(0);
            tolerance.setMax(1);
            tolerance.setValue(((SegregationRule)myRule).getTolerance());
            tolerance.setShowTickLabels(true);
            tolerance.setShowTickMarks(true);
            tolerance.setBlockIncrement(.1);
            tolerance.valueProperty().addListener((observable, oldValue, newValue) -> ((SegregationRule) myRule).setTolerance(newValue.doubleValue()));
            controls.getChildren().add(tolerance);
        }
         else if(simulationName.compareToIgnoreCase("Predator Prey") == 0){
            Slider fishTime = new Slider();
            Slider sharkTime = new Slider();
            fishTime.setMin(1);
            sharkTime.setMin(1);
            fishTime.setMax(30);
            sharkTime.setMax(30);
            fishTime.setValue(((PredatorPreyRule)myRule).getFishReproductionTime());
            sharkTime.setValue(((PredatorPreyRule)myRule).getSharkReproductionTime());
            fishTime.setShowTickLabels(true);
            sharkTime.setShowTickLabels(true);
            fishTime.setShowTickMarks(true);
            sharkTime.setShowTickLabels(true);
            sharkTime.setBlockIncrement(1);
            fishTime.setBlockIncrement(1);
            fishTime.valueProperty().addListener((observable, oldValue, newValue) -> ((PredatorPreyRule) myRule).setFishReproductionTime(newValue.intValue()));
            sharkTime.valueProperty().addListener((observable, oldValue, newValue) -> ((PredatorPreyRule)myRule).setSharkReproductionTime(newValue.intValue()));
            controls.getChildren().addAll(fishTime, sharkTime);
        }


        return controls;
    }

    private void newSimulation(){
        chooseFile();
        myGraph.closeChart();
        createSimulator();
    }

    private void step() {
        myGridUI.step();
        simulatorGridPane = myGridUI.getGridPane();
        myGraph.updateGraph(myGridUI.getCellStateList());
    }
}
