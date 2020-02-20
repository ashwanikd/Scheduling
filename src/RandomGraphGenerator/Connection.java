package RandomGraphGenerator;

/**
 * @author ashwani kumar dwivedi
 */

public class Connection {
    /**
     * constructor
     * @param node the node of graph
     * @param dist distance value
     */
    Connection(Node node,double dist){
        this.node = node;
        this.dist = dist;
    }
    Node node;
    double dist;
}
