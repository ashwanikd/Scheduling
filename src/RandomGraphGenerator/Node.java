package RandomGraphGenerator;

import java.util.LinkedList;

/**
 * @author ashwani kumar dwivedi
 */
public class Node {
    LinkedList<Connection> connections;
    int name;
    double computation;
    Node(int name){
        connections = new LinkedList<>();
        this.name = name;
    }
}
