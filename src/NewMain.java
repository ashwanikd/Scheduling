import DAG.TaskGraph;
import SufferageAlgorithm.Algorithm;

import java.io.File;

public class NewMain {
    public static void main(String args[]){
        File file = new File("Data Sets/Cybershake200.txt");
        TaskGraph graph = new TaskGraph(file,false);
        //graph.printdata();
        System.out.println(graph.start_tasks);
        Algorithm SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());
    }
}
