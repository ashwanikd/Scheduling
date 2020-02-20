package MinMin;


import DAG.TaskGraph;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author ashwani kumar dwivedi
 */

public class Algorithm {
    TaskGraph data;
    double avail[];
    boolean scheduled[];
    double finishtime[];
    int assignment[];
    int taskassignment[];
    LinkedList<Integer> meta_task;
    double[][] c;
    boolean proc_avail[];
    public double load_balance;

    public Algorithm(TaskGraph data){
        this.data = data;
        avail = new double[data.num_of_processors];
        meta_task = new LinkedList<>();
        scheduled = new boolean[data.num_of_tasks];
        finishtime = new double[data.num_of_tasks];
        assignment = new int[data.num_of_processors];
        c = new double[data.num_of_tasks][data.num_of_processors];
        proc_avail = new boolean[data.num_of_processors];
        taskassignment = new int[data.num_of_tasks];
        meta_task.add(data.starttask);
    }

    public double schedulelength(){
        double max=0;
        for(int i=0;i<data.num_of_tasks;i++){
            if(max<finishtime[i]){
                max = finishtime[i];
            }
        }
        return max;
    }

    public double resource_utilization;

    public void calculate_load_balance(){
        load_balance = 0;
        double comp[] = new double[data.num_of_processors];
        for(int i=0;i<taskassignment.length;i++){
            double x = data.cost[i][taskassignment[i]];
            comp[taskassignment[i]] += x;
        }
        double avg=0;
        for(int i=0;i<comp.length;i++){
            avg+=comp[i];
        }
        avg = avg/comp.length;
        resource_utilization = avg/schedulelength();

        for(int i=0;i<comp.length;i++){
            load_balance+=Math.pow(avg-comp[i],2);
        }
        load_balance = Math.sqrt(load_balance/comp.length);
    }

    public void schedule(){
        try{


            calculate_load_balance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    double EFT(int task,int processor) throws IllegalAssignmentException{
        double max = -1;
        double temp;
        boolean check = true;
        for(int i=0;i<data.num_of_tasks;i++){
            if(data.adj_mat[i][task] != -1){
                if(scheduled[i]){
                    temp = maximum(finishtime[i]+(data.proc_com[taskassignment[i]][processor]*data.adj_mat[i][task]),avail[processor])+data.cost[task][processor];
                    max = maximum(max,temp);
                }else {
                    throw new IllegalAssignmentException("Illegal Assignment "+"task "+ i +" must be assigned before task "+ task);
                }
                check = false;
            }
        }
        if(check)
            return (avail[processor]+data.cost[task][processor]);
        return max;
    }

    double maximum(double a,double b){
        if(a<b)
            return b;
        else
            return a;
    }

    class IllegalAssignmentException extends Exception{
        IllegalAssignmentException(String message){
            super(message);
        }
    }
}

