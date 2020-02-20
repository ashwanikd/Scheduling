package Testing;

import DAG.TaskGraph;
import SufferageAlgorithm.Algorithm;

import java.io.File;
import java.io.FileWriter;

public class TestHeftAndSuffrage {
    String outputfilename = "/home/balaji/PROJECTDATA/DataSet/Output.csv";
    FileWriter opwriter,rwriter;
    File inputfile;
    String datapath = "/home/balaji/PROJECTDATA/DataSet/FFT/";

    // values
    double machine_heterogeneity,task_heterogeneity,CCR;
    String filename;
    double heftSchedule,SAschedule;

    long h=0,s=0,e=0;

    SufferageAlgorithm.Algorithm SA;
    HEFT.Algorithm heft;
    TaskGraph graph;

    TestHeftAndSuffrage(){
        try {
            opwriter = new FileWriter(outputfilename);
            opwriter.write("file name,no of task,no of machines,machine hetero,task hetero,CCR,SA,HEFT,Compare,difference");
            rwriter = new FileWriter("/home/balaji/PROJECTDATA/DataSet/result.csv");
            rwriter.write("Data set,heft,SA,equal");
            test();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void test(){
        try {
            String temp, filename;
            for (int i = 2; i <= 32; i *= 2) {
                temp = datapath + i + "/";
                System.out.println(temp);
                for (int j = 1; j <= 18000; j++) {
                    filename = temp + j + ".txt";
                    this.filename = "" + i + "/" + j + ".txt";
                    inputfile = new File(filename);
                    //System.out.println(filename);
                    graph = new TaskGraph(inputfile, true);
                    graph.analyse();
                    machine_heterogeneity = graph.machine_heterogenity;
                    task_heterogeneity = graph.task_heterogenity;
                    CCR = graph.CCR;
                    SA = new SufferageAlgorithm.Algorithm(graph);
                    SA.schedule();
                    heft = new HEFT.Algorithm(graph);
                    heft.schedule();
                    heftSchedule = heft.schedulelength();
                    SAschedule = SA.schedulelength();
                    write();
                    System.gc();
                }
                rwriter.write('\n');
                rwriter.write(temp+","+h+","+s+","+e);
            }
            opwriter.close();


            rwriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void write(){
        try {
            opwriter.write('\n');
            opwriter.write(filename+","+graph.num_of_tasks+","+graph.num_of_processors+","+machine_heterogeneity+","+task_heterogeneity+","+CCR+","+SAschedule+","+heftSchedule);
            if(heftSchedule<SAschedule){
                opwriter.write(","+"HEFT");
                h++;
                System.out.println("HEFT");
            }else if (heftSchedule>SAschedule){
                opwriter.write(","+"SA");
                s++;
                System.out.println("SA");
            }else {
                opwriter.write(","+"Equal");
                e++;
                System.out.println("Equal");
            }
            opwriter.write(","+Math.abs(heftSchedule-SAschedule));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        new TestHeftAndSuffrage();
    }

}
