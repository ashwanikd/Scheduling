import DAG.TaskGraph;
import SufferageAlgorithm.Algorithm;

import java.io.File;
import java.util.Scanner;

public class SAMain {
    public static void main(String args[]){

        File file = new File("Data Sets/dag.txt");
        TaskGraph graph = new TaskGraph(file,true);
        Algorithm SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/test.txt");
        graph = new TaskGraph(file,true);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/8t3.txt");
        graph = new TaskGraph(file,true);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/10t3.txt");
        graph = new TaskGraph(file,true);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/12t3.txt");
        graph = new TaskGraph(file,true);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/16-3-ff.txt");
        graph = new TaskGraph(file,true);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/17-3-ff.txt");
        graph = new TaskGraph(file,true);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/33-3-ff.txt");
        graph = new TaskGraph(file,true);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Cybershake100.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Cybershake200.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Cybershake400.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Epigenomic100.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Epigenomic200.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Epigenomic400.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Inspiral101.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Inspiral202.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Inspiral402.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Montage100.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Montage202.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Montage398.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Sipht100.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Sipht200.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());

        file = new File("Data Sets/Sipht400.txt");
        graph = new TaskGraph(file,false);
        SA = new Algorithm(graph);
        SA.schedule();
        System.out.println("===========================================================================================");
        System.out.println("Data Set : "+ file.getName());
        System.out.println("Number of tasks : "+graph.num_of_tasks);
        System.out.println("Number of processors : "+graph.num_of_processors);
        System.out.println("Schedule length : "+SA.schedulelength());
    }
}
