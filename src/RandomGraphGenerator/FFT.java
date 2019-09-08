package RandomGraphGenerator;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author ashwani kumar dwivdei
 */
public class FFT {
    double communication;
    LinkedList<Node> nodes;
    int num_of_points;
    double edge_weight;
    double total_computation;
    double total_communication;
    double CCR;
    int num_of_nodes;
    int num_of_links;

    int state;
    /**
     * 0 means same edge
     * 1 means edge/2 in each like fft
     * 2 means random from gamma distribution
     */

    GammaDistribution gamma;

    void generateGraph(int state,int num_of_points,double edge_weight){
        this.state = state;
        nodes = new LinkedList<>();
        nodes.add(new Node(0));
        this.num_of_points = num_of_points;
        this.edge_weight = edge_weight;
        this.num_of_nodes=0;
        fft(nodes.get(0),num_of_points,edge_weight);
        num_of_nodes++;
    }

    void generateGraph(int state,int num_of_points,double edge_weight, double var){
        this.state = 2;
        double m = 1/Math.pow(var,2);
        gamma = new GammaDistribution(m,edge_weight/var);
        nodes = new LinkedList<>();
        nodes.add(new Node(0));
        this.num_of_points = num_of_points;
        this.edge_weight = edge_weight;
        this.num_of_nodes=0;
        fft(nodes.get(0),num_of_points,edge_weight);
        num_of_nodes++;
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
    }

    public static void printmatrix(@NotNull double[][] matrix){
        for(int i=0;i<matrix.length;i++){
            System.out.print(i+1+ " => ");
            for(int j=0;j<matrix[i].length;j++){
                //if(matrix[i][j] != -1 )
                    System.out.print(Math.round(matrix[i][j])+" ");
            }
            System.out.println();
        }
    }

    LinkedList<Node> fft(Node node, int size, double s){
        int c = 60;
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
            double next;
            if(state == 0) {
                next = s;
            }else if(state == 1){
                next = s/2;
            }else {
                next = gamma.sample();
            }
                node.connections.add(new Connection(left, next));
                node.connections.add(new Connection(right, next));

                num_of_links += 2;

                LinkedList<Node> leftchilds = fft(left, size / 2, next);
                LinkedList<Node> rightchilds = fft(right, size - (size / 2), next);

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
                temp.connections.add(new Connection(list.get(i),next));
                temp.connections.add(new Connection(list.get(i+step),next));
                num_of_links+=2;
            }
            //System.out.println(size+" "+leftchilds.size()+" "+rightchilds.size()+" "+step);
            for(int i=0;i<rightchilds.size();i++){
                temp = rightchilds.get(i);
                temp.connections.add(new Connection(list.get(i+(size/2)),next));
                temp.connections.add(new Connection(list.get(i+(size/2)-step),next));
                num_of_links+=2;
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
        for(int i=1;i<5;i++) {
            fft.generateGraph(2,(int)Math.pow(2,i), 800,0.5);
            fft.generateMatrix();
            printmatrix(fft.matrix);
            System.out.println();
        }
    }
}
