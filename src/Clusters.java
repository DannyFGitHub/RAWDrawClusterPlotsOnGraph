import java.util.HashMap;
import java.util.LinkedList;

/**
 * Clusters class extends HashMap of String as key and LinkedList as values. The LinkedList are lists of
 * ClusterNodes that belong in the same Cluster.
 * The private attributes/fields maxXAxis and maxYAxis will be used by the ScatterChart to know how
 * large the y and x axis need to be.
 * The Clusters : addClusterNode(CLusterNode) will automatically update the maxXAxis and maxYAxis as new cluster nodes come in.
 * After the Cluster has been fully built up, with ClusterNodes, the maxXAxis and maxYAxis values will have the positive largest numbers
 * that the ClusterNodes will reach on the graph.
 */
public class Clusters extends HashMap<String, LinkedList<ClusterNode>> {
    //attribute of Clusters to indicate and store the highest X axis value for graph scaling purposes.
    private double maxXAxis;
    //attribute of Clusters to indicate and store the highest Y axis value for graph scaling purposes.
    private double maxYAxis;


    public void addClusterNode(ClusterNode clusterNode) throws NullPointerException {

        //If the ClusterNode passed as an argument is null, then throw exception.
        if (clusterNode == null) {
            throw new NullPointerException();
        }

        //Get the name of the ClusterNode passed to the addClusterNode method.
        String clusterName = clusterNode.getClusterName();

        //If the cluster HashMap does not contain the key (Cluster name) for this cluster node
        if (!this.containsKey(clusterName)) {
            //Create a blank LinkedList to fill, as this is the first time this Cluster is encountered
            this.put(clusterName, new LinkedList<>());
        }
        /*
             At this stage the cluster LinkedList would either already exist or already be created.
             So add the new clusterNode under the Cluster name as key of the HashMap.
        */
        super.get(clusterNode.getClusterName()).add(clusterNode);

        //Update the Clusters' MaxXAxis value only if its bigger in the clusterNode
        updateMaxXAxis(clusterNode.getX());

        //Update the Clusters' MaxYAxis value only if its bigger in the clusterNode.
        updateMaxYAxis(clusterNode.getY());
    }

    /**
     * Method to update the maxXAxis double variable to match the largest xAxis argument given.
     *
     * @param xAxis double correlating to the xAxis value.
     */
    private void updateMaxXAxis(double xAxis) {
        if (xAxis > maxXAxis) {
            maxXAxis = xAxis;
        }
    }

    /**
     * Method to update the maxYAxis double variable to match the largest yAxis argument given.
     *
     * @param yAxis double correlating to the yAxis value.
     */
    private void updateMaxYAxis(double yAxis) {
        if (yAxis > maxYAxis) {
            maxYAxis = yAxis;
        }
    }

    /**
     * Simply return the maxXAxis value.
     *
     * @return
     */
    public double getMaxXAxis() {
        return maxXAxis;
    }

    /**
     * Simply return the maxYAxis value.
     *
     * @return
     */
    public double getMaxYAxis() {
        return maxYAxis;
    }
}
