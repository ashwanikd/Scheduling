package DAG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class TaskGraph {

    public int num_of_tasks,num_of_processors;
    public double cost[][];
    public double proc_com[][];
    public double adj_mat[][];
    boolean x= true;
    public int starttask,exit_task;
    public LinkedList<Integer> start_tasks;
    public LinkedList<Integer> exit_tasks;
    Scanner scan;

    public TaskGraph(File file,boolean normal){
        try {
            x=normal;
            this.scan = new Scanner(file);
            start_tasks = new LinkedList<>();
            exit_tasks = new LinkedList<>();
            if(x)
                readinput();
            else readinput1();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    void readinput(){
        try{
            num_of_tasks = scan.nextInt();
            num_of_processors = scan.nextInt();
            cost = new double[num_of_tasks][num_of_processors];
            for(int i=0;i<num_of_tasks;i++){
                for(int j=0;j<num_of_processors;j++){
                    cost[i][j] = scan.nextDouble();
                }
            }
            proc_com = new double[num_of_processors][num_of_processors];
            for(int i=0;i<num_of_processors;i++){
                for(int j=0;j<num_of_processors;j++){
                    proc_com[i][j] = scan.nextDouble();
                }
            }
            adj_mat = new double[num_of_tasks][num_of_tasks];
            for(int i=0;i<num_of_tasks;i++){
                for(int j=0;j<num_of_tasks;j++){
                    adj_mat[i][j] = scan.nextDouble();
                }
            }
            //System.out.println("Successfully read data ...");
            set_start_tasks();
            set_exit_tasks();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void readinput1(){
        try{
            num_of_tasks = scan.nextInt();
            num_of_processors = scan.nextInt();
            cost = new double[num_of_tasks][num_of_processors];
            for(int i=0;i<num_of_tasks;i++){
                cost[i][0] = scan.nextDouble();
                for(int j=1;j<num_of_processors;j++){
                    cost[i][j] = cost[i][0];
                }
            }
            proc_com = new double[num_of_processors][num_of_processors];
            for(int i=0;i<num_of_processors;i++){
                for(int j=0;j<num_of_processors;j++){
                    if(i!=j)
                        proc_com[i][j] = 1;
                    else proc_com[i][j] = 0;
                }
            }
            double temp;
            adj_mat = new double[num_of_tasks][num_of_tasks];
            for(int i=0;i<num_of_tasks;i++){
                for(int j=0;j<num_of_tasks;j++){
                    temp = scan.nextDouble();
                    if(temp==0)
                        adj_mat[i][j] = -1;
                    else
                        adj_mat[i][j] = 1;
                }
            }
            set_start_tasks();
            set_exit_tasks();
            //System.out.println("Successfully read data ...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void set_start_tasks(){
        boolean check;
        for(int i=0;i<num_of_tasks;i++){
            check = true;
            for(int j=0;j<num_of_tasks;j++){
                if(adj_mat[j][i]!=-1) {
                    check = false;
                    break;
                }
            }
            if(check)
                start_tasks.add(i);
        }
        if(start_tasks.size()>1){
            double adj[][] = adj_mat;
            adj_mat= new double[num_of_tasks+1][num_of_tasks+1];
            for(int i=0;i<num_of_tasks+1;i++){
                if(start_tasks.contains(i-1)){
                    adj_mat[0][i] = 0;
                }else adj_mat[0][i] = -1;
                adj_mat[i][0] = -1;
            }
            for(int i=0;i<num_of_tasks;i++){
                for(int j=0;j<num_of_tasks;j++){
                    adj_mat[i+1][j+1]=adj[i][j];
                }
            }
            double cost1[][] = cost;
            cost = new double[num_of_tasks+1][num_of_processors];
            for(int i=0;i<num_of_processors;i++){
                cost[0][i] = 0;
            }
            for(int i=0;i<num_of_tasks;i++){
                for(int j=0;j<num_of_processors;j++){
                    cost[i+1][j]=cost1[i][j];
                }
            }
            num_of_tasks++;
            starttask = 0;
            //printdata();
        }else starttask = start_tasks.get(0);
    }

    void set_exit_tasks(){
        boolean check;
        for(int i=0;i<num_of_tasks;i++){
            check = true;
            for(int j=0;j<num_of_tasks;j++){
                if(adj_mat[i][j]!=-1) {
                    check = false;
                    break;
                }
            }
            if(check)
                exit_tasks.add(i);
        }
        if(exit_tasks.size()>1){
            double adj[][] = adj_mat;
            adj_mat= new double[num_of_tasks+1][num_of_tasks+1];
            for(int i=0;i<num_of_tasks+1;i++){
                if(exit_tasks.contains(i)){
                    adj_mat[i][num_of_tasks] = 0;
                }else adj_mat[i][num_of_tasks] = -1;
                adj_mat[num_of_tasks][i] = -1;
            }
            for(int i=0;i<num_of_tasks;i++){
                for(int j=0;j<num_of_tasks;j++){
                    adj_mat[i][j]=adj[i][j];
                }
            }
            double cost1[][] = cost;
            cost = new double[num_of_tasks+1][num_of_processors];
            for(int i=0;i<num_of_processors;i++){
                cost[num_of_tasks][i] = 0;
            }
            for(int i=0;i<num_of_tasks;i++){
                for(int j=0;j<num_of_processors;j++){
                    cost[i][j]=cost1[i][j];
                }
            }
            num_of_tasks++;
            exit_task = num_of_tasks-1;
        }else exit_task = exit_tasks.get(0);
    }

    public void printdata(){
        System.out.println();
        System.out.println("number of tasks ...");
        System.out.println(num_of_tasks);
        System.out.println();
        System.out.println("number of machines ...");
        System.out.println(num_of_processors);
        System.out.println();
        System.out.println("Cost matrix ...");
        printmatrix(cost);
        System.out.println();
        System.out.println("Communication matrix ...");
        printmatrix(proc_com);
        System.out.println();
        System.out.println("Adjacency matrix ...");
        printmatrix(adj_mat);
        System.out.println();
    }

    public static void printmatrix(double[][] matrix){
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }
}
