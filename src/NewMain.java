import DAG.TaskGraph;
import HEFT.Algorithm;

import java.io.File;

public class NewMain {
    public static void main(String args[]){
        File file = new File("DataSet/FFT/1.txt");
        TaskGraph graph = new TaskGraph(file,true);
        graph.analyse();
        graph.printdata();
        System.out.println(graph.CCR);
    }
}
