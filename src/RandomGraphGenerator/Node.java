package RandomGraphGenerator;

import java.util.LinkedList;

public class Node {
    LinkedList<Connection> connections;
    int name;
    Node(int name){
        connections = new LinkedList<>();
        this.name = name;
    }
}
