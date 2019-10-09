import javafx.scene.chart.XYChart;

/**
 * Original ClusterNode Object with properties X, Y and ClusterName values
 */
public class ClusterNode {

    private double x = 0.0;
    private double y = 0.0;
    private String clusterName = "";

    /**
     * ClusterNode constructor with x, y values as argument as well as family cluster name (clusterName)
     *
     * @param x           double location of cluster node in x axis
     * @param y           double location of cluster node in y axis
     * @param clusterName String name of cluster node's cluster
     */
    public ClusterNode(double x, double y, String clusterName) {
        this.x = x;
        this.y = y;
        this.clusterName = clusterName;
    }

    /**
     * Return X, this method is required as x is private. In order for x to be private
     * and read only after it has been set during the construction of the instace of this class.
     *
     * @return double position on the X axis
     */
    public double getX() {
        return x;
    }

    /**
     * Return y, this method is required as y is private. In order for y to be read only
     * after it has been set during the construction of the instance of this class.
     *
     * @return double position on the Y Axis
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the name of the cluster that this ClusterNode belongs to.
     *
     * @return String cluster name
     */
    public String getClusterName() {
        return clusterName;
    }

    /**
     * This method is used to create XYChart.Data objects from this ClusterNode for the
     * ScatterChart. This method makes it easily compatible to add this ClusterNode to the
     * ScatterChart.
     *
     * @return
     */
    public XYChart.Data<Double, Double> getAsXYChartData() {
        return new XYChart.Data<>(this.getX(), this.getY());
    }

    /**
     * Method override toString() to my custom setup. Using the following format for debugging and identification purposes.
     */
    @Override
    public String toString() {
        return "ClusterNode:{\'" + clusterName + "\':(x:" + x + ",y:" + y + ")}";
    }
}