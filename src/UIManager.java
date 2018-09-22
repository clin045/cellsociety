import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class UIManager extends Application {
    public void start(Stage stage){
        //create text for XML label
        Text label1 = new Text("Choose a XML file:");

        Text label2 = new Text("No file chosen.");

        //setup load button
        Button load = new Button("Select...");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //load file chooser
                FileChooser myFileChooser = new FileChooser();
                myFileChooser.setTitle("Choose XML file");
                FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                myFileChooser.getExtensionFilters().add(xmlFilter);
                File chosen = myFileChooser.showOpenDialog(stage);
                if(chosen != null){
                    label2.setText(chosen.getName());
                }
            }
        });

        Button starter = new Button("Start!");

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

        stage.setTitle("CA Simulator");

        stage.setScene(myScene);

        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
