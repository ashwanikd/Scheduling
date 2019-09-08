package RandomGraphGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;

public class FFT {
    double communication;
    LinkedList<Node> nodes;
    int num_of_points;
    double edge_weight;
    int num_of_nodes;

    void generateGraph(int num_of_points,double edge_weight){
        nodes = new LinkedList<>();
        nodes.add(new Node(0));
        this.num_of_points = num_of_points;
        this.edge_weight = edge_weight;
        this.num_of_nodes=0;
        fft(nodes.get(0),num_of_points);
    }

    double[][] matrix;

    void generateMatrix(){
        matrix = new double[nodes.size()][nodes.size()];
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix.length;j++){
                matrix[i][j] = -1;
            }
        }
        Iterator it1 = nodes.iterator();
        Node temp;
        Connection temp2;
        Iterator it2;
        for(int i=0;i<nodes.size();i++){
            temp = (Node)it1.next();
            it2 = temp.connections.iterator();
            while(it2.hasNext()){
                temp2 = (Connection) it2.next();
                matrix[temp.name][temp2.node.name] = temp2.dist;
            }
        }

        printmatrix(matrix);
    }

    public static void printmatrix(@NotNull double[][] matrix){
        for(int i=0;i<matrix.length;i++){
            System.out.print(i+1+ " => ");
            for(int j=0;j<matrix[i].length;j++){
                if(matrix[i][j] != -1 )
                    System.out.print(j+1+" ");
            }
            System.out.println();
        }
    }

    LinkedList<Node> fft(Node node, int size){

        if(size == 1){
            LinkedList<Node> list = new LinkedList<>();
            //Node n = new Node(++num_of_nodes);
            //node.connections.add(new Connection(n,edge_weight));
            list.add(node);
            //nodes.add(n);
            return list;
        }else {

            // IOV operation
            Node left = new Node(++num_of_nodes);
            Node right = new Node(++num_of_nodes);

            nodes.add(left);
            nodes.add(right);

            node.connections.add(new Connection(left,edge_weight));
            node.connections.add(new Connection(right,edge_weight));

            LinkedList<Node> leftchilds = fft(left,size/2);
            LinkedList<Node> rightchilds = fft(right,size-(size/2));

            // butterfly operation
            LinkedList<Node> list = new LinkedList<>();
            Node temp;
            for(int i=0;i<size;i++){
                temp = new Node(++num_of_nodes);
                list.add(temp);
                nodes.add(temp);
            }
            int step = size/2;
            for(int i=0;i<leftchilds.size();i++){
                temp = leftchilds.get(i);
                temp.connections.add(new Connection(list.get(i),edge_weight));
                temp.connections.add(new Connection(list.get(i+step),edge_weight));
            }
            //System.out.println(size+" "+leftchilds.size()+" "+rightchilds.size()+" "+step);
            for(int i=0;i<rightchilds.size();i++){
                temp = rightchilds.get(i);
                temp.connections.add(new Connection(list.get(i+(size/2)),edge_weight));
                temp.connections.add(new Connection(list.get(i+(size/2)-step),edge_weight));
            }
            return list;
        }
    }

    int powerof2(int x){
        int count=0;
        while(x!=1){
            x/=2;
            count++;
        }
        return count;
    }

    public static void main(String args[]){
        FFT fft = new FFT();
        fft.generateGraph(5,80);
        fft.generateMatrix();
    }
}
