import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * @Author Danny Falero
 * Display the records of clusters
 * This system allows a user to view PIDC-O graphs based on Cluster information found on cluster.txt text file.
 * This program will display a legend and will automatically grow to accommodate more clusters and more clusterNodes.
 * The program only supports cluster nodes in positive areas of X and Y axis and the limit of each axis is equal to Double.MAX_VALUE
 */
public class DisplayClustersMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Window Title setup
        primaryStage.setTitle("Assignment Task 2, Task 2 Display Cluster Records");

        //Initiate the Clusters custom data structure
        Clusters clusters = null;

        //Attempt to...
        try {
            //...read the file and fill in the clusters data structure with ClusterNode information
            clusters = ClusterFileReader.readClusterFile("cluster.txt");
        } catch (FileNotFoundException fileNotFound) {
            //If cluster.txt file is not found show Alert to user so the file may be placed where required.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setHeaderText("Missing cluster.txt");
            alert.getDialogPane().setContentText("The file cluster.txt could not be found. \n" +
                    "Please make sure to place the file in the current running directory to allow the file to be read." +
                    "\nThe program will now quit, please reopen once the file is put into place." +
                    "\nAlso make sure its named : \"cluster.txt\" as per the assignment task");
            alert.showAndWait();
            //Exit after user closes dialog.
            System.exit(0);
        } catch (Exception ex){
            //If cluster.txt file is not formatted properly or there were issues reading the file show the following
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setHeaderText("Corrupt cluster.txt file");
            alert.getDialogPane().setContentText("The file cluster.txt appears to be corrupt OR \n" +
                    "It is not compliant with the format for this program." +
                    "\nThe program will now quit, please reopen once a compatible cluster.txt file is available." +
                    "\nIf necessary download a fresh copy from Interact." +
                    "\nAlso make sure its named : \"cluster.txt\" as per the assignment task");
            alert.showAndWait();

            ex.printStackTrace();

            //Exit after user closes dialog.
            System.exit(0);
        }


        /*
        X Axis NumberAxis object definition and initiation to use with ScatterChart.
        Use the clusters custom maxXAxis variable plus an offset just so the plotted Cluster nodes don't look cut out of the table.
        Chose 0.5 tick unit, was most visually appealing when trying to make out the coordinates of the cluster nodes.
        */
        NumberAxis xAxis = new NumberAxis(0, clusters.getMaxXAxis() + 1, 0.5);
        //Set a label for the axis.
        xAxis.setLabel("X Axis");

        /*
        Y Axis NumberAxis object definition and initiation to use with ScatterChart.
        Use the clusters custom maxYAxis variable plus an offset just so the plotted Cluster nodes don't look cut out of the table.
        Chose 0.5 tick unit, was most visually appealing when trying to make out the coordinates of the cluster nodes.
        */
        NumberAxis yAxis = new NumberAxis(0, clusters.getMaxYAxis() + 1, 0.5);
        //Set a label for the Y axis.
        yAxis.setLabel("Y Axis");

        //Defining and initiating the ScatterChart object with the NumberAxis inserted in the constructor.
        ScatterChart<Double, Double> clusterRecordsGraph = new ScatterChart(xAxis, yAxis);


        /*
        Populate the XYChart series with cluster data
        For every cluster name, get the cluster LinkedList that was under the key by that name.
        Create a new XYChart.Series with the name of the key, which is the name of the Cluster.
        For every node in the LinkedList under the HashMap's Cluster's name (key), call the custom
        getAsXYChartData() method.
         */
        for (String cluster : clusters.keySet()) {
            //Prepare the series to house the data, which will go in the ScatterChart
            XYChart.Series series = new XYChart.Series();
            //Set a name for the series. This will show in the automatic Legend.
            series.setName(cluster);
            //For every clusterNode in the cluster
            for (ClusterNode clusterNode : clusters.get(cluster)) {
                //call the getAsXYChartData method to get the XYChart.Data object created by the ClusterNode object.
                series.getData().add(clusterNode.getAsXYChartData());
            }
            //Add all the series created to the graph (ScatterChart).
            clusterRecordsGraph.getData().addAll(series);
        }

        //Prepare an AnchorPane parent container to put the graph in.
        AnchorPane anchorPane = new AnchorPane();

        //Add the graph to the container
        anchorPane.getChildren().add(clusterRecordsGraph);

        //Prepare a scene and add the container to the scene
        Scene mainScene = new Scene(anchorPane);

        //Add the scene to the stage
        primaryStage.setScene(mainScene);

        //Make the window appear so the user can interact with it.
        primaryStage.show();
    }
}
