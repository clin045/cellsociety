import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GraphManager {
    private static final int WINDOW_SIZE = 300;
    private ArrayList<XYChart.Series> mySeries = new ArrayList<>();
    private LineChart<Number,Number> lineChart;
    private int time = 0;
    private String[] myColors;

    GraphManager(int numSeries, String[] colors) {
        myColors = colors;
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Steps");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        for(int i=0;i<numSeries;i++){
            mySeries.add(new XYChart.Series());
        }
    }

    public void updateGraph(int[] values){
        for(int i=0;i<mySeries.size();i++){
            mySeries.get(i).getData().add(new XYChart.Data(time, values[i]));
        }
        time++;
    }

    public void showChart(){
        Scene scene  = new Scene(lineChart, 600, WINDOW_SIZE);
        for(int i=0;i<mySeries.size();i++){
            XYChart.Series myCurrentSeries = mySeries.get(i);
            lineChart.getData().add(myCurrentSeries);
            myCurrentSeries.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #" + myColors[i] + ";");
        }
        Stage stage = new Stage();
        stage.setTitle("CA Simulator");
        stage.setX(0);
        stage.setY(0);
        stage.setScene(scene);
        stage.show();
    }
}
