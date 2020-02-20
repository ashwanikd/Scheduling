import DAG.TaskGraph;
import SufferageAlgorithm.Algorithm;

import java.io.File;

public class Testmain {
    public static void main(String args[]) {

        File file = new File("Data Sets/test.txt");
        TaskGraph graph = new TaskGraph(file, true);
        SufferageAlgorithm.Algorithm SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("Schedule length of SA : " + SA.schedulelength());

        file = new File("Data Sets/test.txt");
        graph = new TaskGraph(file, true);
        HEFT.Algorithm h = new HEFT.Algorithm(graph);
        h.schedule();
        System.out.println("Schedule length of HEFT : " + h.schedulelength());


    }
}
