import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml.Simulation;
import xml.XMLParser;

import java.io.File;
import java.util.ResourceBundle;


public class UIManager extends Application {
    //public static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 300;

    private static final String DEFAULT_RESOURCE_PACKAGE = "English";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
    private File chosen;
    private CellManager myCellManager = new CellManager();
    private GridPane simulatorGridPane;
    private int rows;
    private int columns;
    private String[] colors;
    private Timeline animation = new Timeline();
    private Simulation configs;
    RuleInterface myRule;

    public void start(Stage stage){
        //create text for XML label
        Text label1 = new Text(myResources.getString("ChooseFileLabel"));

        Text label2 = new Text(myResources.getString("NoFile"));

        //setup load button
        Button load = new Button(myResources.getString("SelectButton"));
        load.setOnAction(event -> {
            //load file chooser
            FileChooser myFileChooser = new FileChooser();
            myFileChooser.setTitle(myResources.getString("ChooserWindowTitle"));
            FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            myFileChooser.getExtensionFilters().add(xmlFilter);
            myFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            chosen = myFileChooser.showOpenDialog(stage);
            if(chosen != null){
                label2.setText(chosen.getName());
            }
        });

        Button starter = new Button(myResources.getString("StartButton"));
        starter.setOnAction(event -> createSimulator(stage));

        GridPane myGridPane = new GridPane();
        myGridPane.setMinSize(600, 600);
        myGridPane.setPadding(new Insets(10, 10, 10, 10));
        myGridPane.setVgap(5);
        myGridPane.setHgap(5);
        myGridPane.setAlignment(Pos.CENTER);

        myGridPane.add(label1, 0,0);
        myGridPane.add(label2, 0, 1);
        myGridPane.add(load, 1, 1);
        myGridPane.add(starter, 0, 2);

        //create scene
        Scene myScene = new Scene(myGridPane);

        stage.setTitle(myResources.getString("WindowTitle"));

        stage.setScene(myScene);

        stage.show();
    }

    private void createSimulator(Stage stage){

        initializeWindow(stage);

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.playFromStart();
        System.out.println("createSim done");

    }

    public void cleanUp(Stage stage){
        animation.stop();
        System.out.println("cleanup done");
        //stage.close();
        createSimulator(stage);
    }

    private void initializeWindow(Stage stage){
        configs = new XMLParser("media").getSimulation(chosen);
        rows = configs.getRows();
        columns = configs.getCols();
        int[][] initialStates = configs.getConfigs();
        colors = configs.getColors().split(",");

        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(10, 10, 10, 10));
        rootPane.setMinSize(600, 600);
        rootPane.setAlignment(Pos.CENTER);

        Label title = new Label(configs.getTitle());
        Label author = new Label(configs.getAuthor());
        Label simulationName = new Label(configs.getSimulationName());

        GridPane displayInfo = new GridPane();
        displayInfo.setAlignment(Pos.CENTER);
        displayInfo.setPadding(new Insets(10, 10, 10, 10));
        displayInfo.add(title, 0, 0);
        GridPane.setHalignment(title, HPos.CENTER);
        displayInfo.add(author, 0, 1);
        GridPane.setHalignment(author, HPos.CENTER);
        displayInfo.add(simulationName, 0, 2);
        GridPane.setHalignment(simulationName, HPos.CENTER);

        simulatorGridPane = new GridPane();
        simulatorGridPane.setAlignment(Pos.CENTER);
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                simulatorGridPane.add(createCell(colors[initialStates[i][j]], rows, columns), i, j);
            }
        }
        FlowPane controls = new FlowPane();
        controls.setPadding(new Insets(10, 10, 10, 10));
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
        newSimulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser myFileChooser = new FileChooser();
                myFileChooser.setTitle(myResources.getString("ChooserWindowTitle"));
                FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                myFileChooser.getExtensionFilters().add(xmlFilter);
                myFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                chosen = myFileChooser.showOpenDialog(stage);

                //now reinitialize
                cleanUp(stage);
            }
        });
        //todo: figure out reset animation logistics
        controls.getChildren().add(play);
        controls.getChildren().add(pause);
        controls.getChildren().add(step);
        controls.getChildren().add(halfSpeed);
        controls.getChildren().add(normalSpeed);
        controls.getChildren().add(doubleSpeed);
        controls.getChildren().add(newSimulation);

        rootPane.add(displayInfo, 0, 0);
        rootPane.add(simulatorGridPane, 0, 1);
        rootPane.add(controls, 0, 2);

        if(configs.getSimulationName().compareToIgnoreCase("Game of Life") == 0){
            myRule = new GameOfLifeRule();
        }
        else if(configs.getSimulationName().compareToIgnoreCase("Predator Prey") == 0){
            myRule = new PredatorPreyRule();
        }
        else if(configs.getSimulationName().compareToIgnoreCase("Fire") == 0){
            myRule = new FireRule();
        }
        else {
            myRule = new SegregationRule();
        }
        myCellManager.initializeGrid(configs.getRows(), configs.getCols(), initialStates, myRule);

        stage.setScene(new Scene(rootPane));
    }

    private Node getNodeFromGridPane(int col, int row) {
        for (Node node : simulatorGridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void step(){
        myCellManager.nextGeneration();
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                BorderPane thisCellPane = (BorderPane) getNodeFromGridPane(i, j);
                Cell thisCell = myCellManager.getCell(i, j);
                if (thisCellPane != null) {
                    updateCellAppearance(colors[thisCell.getCurrentState()], thisCellPane);
                }
                else {
                    System.out.println("null cell");
                }
            }
        }
        System.out.println("Step done");
    }

    private void updateCellAppearance(String color, BorderPane myCell){
        myCell.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #" + color + ";" +
                "-fx-border-width: 1;");
    }

    private BorderPane createCell(String color, int rows, int columns){
        BorderPane cell = new BorderPane();
        int cellSize = 500/Math.max(rows, columns);
        cell.setMinSize(cellSize, cellSize);
        cell.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #" + color + ";" +
                "-fx-border-width: 1;");
        return cell;
    }

    public static void main(String args[]){
        launch(args);
    }
}
