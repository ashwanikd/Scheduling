package RandomGraphGenerator;

/**
 * @author ashwani kumar dwivedi
 */
public class Connection {
    Connection(Node node,double dist){
        this.node = node;
        this.dist = dist;
    }
    Node node;
    double dist;
}
