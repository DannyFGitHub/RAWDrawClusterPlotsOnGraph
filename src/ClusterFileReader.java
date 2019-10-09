import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ClusterFileReader converts a given cluster.txt file to a LinkedList of cluster node objects of the ClusterNode class.
 */
public class ClusterFileReader {

    //Pattern to capture the worded heading in the title/header of the file
    public static final String wordedHeaderRegex = "[^0-9]*";
    //Pattern to capture the cluster number it captures 000,000.0 style as well as simple 000.00
    private static final String axisValueRegex = "(\\d{1,3}[,.]?)+";
    //Pattern to capture the cluster name (caps or lowercase for most compatibility) and numeral
    private static final String clusterNameRegex = "[a-zA-Z]*(\\d*)";
    //Pattern to capture the space between numbers in the table
    private static final String spacePattern = "(\\s)+";

    /**
     * Overloading readClusterFile() to allow providing a full path name of where the cluster.txt is located.
     * Method to read the cluster.txt file using regex patterns to find in lines and create a ClusterNode for each
     * entry identified. This method allows the conversion from text file to ClusterNode objects organised in a HashMap by Cluster Name as key.
     *
     * @param pathName String path to file (current working directory or absolute) that contains the cluster information the file is hardcoded
     *                 to be called cluster.txt
     * @return Clusters which extends HashMap containing Cluster names and list of ClusterNode objects containing the information corresponding to the
     * cluster node's axis values as well as ClusterNode's Cluster. The String object holds the name of the Cluster. HashMap is the most concrete class available for key and value kind of pairing.
     * LinkedList is the best option for the Cluster Nodes although order is not important in this case.
     * @throws FileNotFoundException
     */
    public static Clusters readClusterFile(String pathName) throws FileNotFoundException {

        //Initiating the Clusters data structure for adding data later on.
        Clusters clusters = new Clusters();

        //Create File object
        File clusterTextFile = new File(pathName);
        //Prepare a fileInputStream object to stream the file to the scanner.
        FileInputStream fileInputStream = new FileInputStream(clusterTextFile.getAbsolutePath());
        //Create a scanner to iterate through lines with and read the file
        Scanner scanner = new Scanner(fileInputStream);

        //Repeat while there are still lines in the text file to read, as each line represents a cluster node.
        while (scanner.hasNextLine()) {

            //If line is not blank (no non-whitespace characters in line) (the following negative lookahead does not consume)
            if (scanner.findInLine("(?=\\S)") != null) {

                //If the line has a worded pattern
                if (scanner.hasNext(wordedHeaderRegex)) {
                    //Skip first line if it includes letters as it is the heading line for the table
                    scanner.nextLine();
                }

                //Check and capture the X value using the axisValueRegex pattern string constant (final)
                String xAxisString = scanner.findInLine(axisValueRegex);
                //Convert it to double value.
                double xAxisValue = Double.valueOf(xAxisString);
                //Consume spaces to skip
                scanner.findInLine(spacePattern);

                //Check and capture the Y value using the axisValueRegex pattern string constant (final)
                String yAxisString = scanner.findInLine(axisValueRegex);
                //Convert it to double value.
                double yAxisValue = Double.valueOf(yAxisString);
                //Consume spaces to skip
                scanner.findInLine(spacePattern);

                //Check and capture the name of the cluster the cluster node belongs to using the clusterNameRegex
                String clusterNameString = scanner.findInLine(clusterNameRegex);

                //Save the cluster node information found in file to a cluster object for internal-program usage.
                clusters.addClusterNode(new ClusterNode(xAxisValue, yAxisValue, clusterNameString));

                // After processing this line, advance to the next line
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                } else {
                    //If it is end of file break loop.
                    break;
                }

            } else {
                //Skip line because the line is just whitespace type of characters.
                scanner.nextLine();
            }
        }

        //Return the clusters object.
        return clusters;
    }

}


