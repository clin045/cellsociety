import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;


public class UIManager extends Application {
    private static final String DEFAULT_RESOURCE_PACKAGE = "English";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

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
            File chosen = myFileChooser.showOpenDialog(stage);
            if(chosen != null){
                label2.setText(chosen.getName());
            }
        });

        Button starter = new Button(myResources.getString("StartButton"));
        starter.setOnAction(event -> createSimulator(stage));

        //create a gridpane
        GridPane myGridPane = new GridPane();
        myGridPane.setMinSize(400, 400);
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
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(10, 10, 10, 10));
        rootPane.setMinSize(400, 400);
        rootPane.setAlignment(Pos.CENTER);

        GridPane simulatorGridPane = new GridPane();
        simulatorGridPane.setGridLinesVisible(true);
        simulatorGridPane.setAlignment(Pos.CENTER);
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                simulatorGridPane.add(new Text("Test"), i, j);
            }
        }
        FlowPane controls = new FlowPane();
        controls.setPadding(new Insets(10, 10, 10, 10));
        controls.setAlignment(Pos.CENTER);
        Button play = new Button(myResources.getString("PlayButton"));
        Button pause = new Button(myResources.getString("PauseButton"));
        Button step = new Button(myResources.getString("StepButton"));
        Button halfSpeed = new Button(myResources.getString("HalfSpeedButton"));
        Button normalSpeed = new Button(myResources.getString("NormalSpeedButton"));
        Button doubleSpeed = new Button(myResources.getString("DoubleSpeedButton"));
        Button newSimulation = new Button(myResources.getString("NewSimulation"));
        controls.getChildren().add(play);
        controls.getChildren().add(pause);
        controls.getChildren().add(step);
        controls.getChildren().add(halfSpeed);
        controls.getChildren().add(normalSpeed);
        controls.getChildren().add(doubleSpeed);
        controls.getChildren().add(newSimulation);

        rootPane.add(simulatorGridPane, 0, 0);
        rootPane.add(controls, 0, 1);

        Scene simulatorScene = new Scene(rootPane);
        stage.setScene(simulatorScene);
    }

    public static void main(String args[]){
        launch(args);
    }
}
