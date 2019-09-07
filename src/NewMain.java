import DAG.TaskGraph;
import SufferageAlgorithm.Algorithm;

import java.io.File;

public class NewMain {
    public static void main(String args[]){
        File file = new File("Data Sets/33-3-ff.txt");
        TaskGraph graph = new TaskGraph(file,true);
        graph.printdata();
    }
}
