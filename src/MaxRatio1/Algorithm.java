package MaxRatio1;

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
    double[][] c;
    double[][] d;
    double[] tmax;
    double[] cmax;
    boolean proc_avail[];
    public double load_balance;
    LinkedList<Integer> meta_task;

    public Algorithm(TaskGraph data){
        this.data = data;
        meta_task = new LinkedList<>();
        avail = new double[data.num_of_processors];
        scheduled = new boolean[data.num_of_tasks];
        finishtime = new double[data.num_of_tasks];
        assignment = new int[data.num_of_processors];
        c = new double[data.num_of_tasks][data.num_of_processors];
        d = new double[data.num_of_tasks][data.num_of_processors];
        tmax = new double[data.num_of_tasks];
        cmax = new double[data.num_of_tasks];
        setTmaxCmax();
        proc_avail = new boolean[data.num_of_processors];
        taskassignment = new int[data.num_of_tasks];
        meta_task.add(data.starttask);
    }

    void setTmaxCmax(){
        for(int i=0;i<data.num_of_tasks;i++){
            tmax[i] = maximum(data.cost[i]);
        }
        for(int t=0;t<data.num_of_tasks;t++){
            for(int i=0;i<data.num_of_tasks;i++){
                if(data.adj_mat[i][t]>cmax[t]){
                    cmax[t] = data.adj_mat[i][t];
                }
            }
        }
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
            boolean check;
            Iterator it;
            int t,task=0,proc_of_task=0;
            double min;
            while(meta_task.size()!=0){
                it = meta_task.listIterator();
                // make c matrix
                while(it.hasNext()){
                    t = (int)it.next();
                    for(int i=0;i<data.num_of_processors;i++){
                        c[t][i] = EFT(t,i);
                    }
                }

                while (meta_task.size() != 0){
                    min = Double.MAX_VALUE;
                    it = meta_task.listIterator();
                    while(it.hasNext()){
                        t = (int)it.next();
                        for(int i=0;i<data.num_of_processors;i++) {
                            if(cmax[t]>0) {
                                if (c[t][i] * tmax[t] / cmax[t] < min) {
                                    min = c[t][i] * tmax[t] / cmax[t];
                                    proc_of_task = i;
                                    task = t;
                                }
                            }else {
                                if (c[t][i] < min) {
                                    min = c[t][i];
                                    proc_of_task = i;
                                    task = t;
                                }
                            }
                        }
                    }
                    finishtime[task] = c[task][proc_of_task];
                    taskassignment[task] = proc_of_task;
                    avail[proc_of_task] = c[task][proc_of_task];
                    scheduled[task] = true;
                    assignment[proc_of_task] = task;
                    meta_task.remove(Integer.valueOf(task));

                    // update c matrix
                    it = meta_task.listIterator();
                    while(it.hasNext()){
                        t = (int)it.next();
                        for(int i=0;i<data.num_of_processors;i++){
                            c[t][i] = EFT(t,i);
                        }
                    }
                }

                // adding successors

                for(int i=0;i<data.adj_mat.length;i++){
                    if(scheduled[i])
                        continue;
                    check = true;
                    for(int j=0;j<data.adj_mat[i].length;j++){
                        if(data.adj_mat[j][i]!=-1){
                            if(!scheduled[j]){
                                check = false;
                                break;
                            }
                        }
                    }
                    if(check){
                        meta_task.add(i);
                    }
                }
            }

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

    double maximum(double[] array){
        double max = Double.MIN_VALUE;
        for(int i=0;i<array.length;i++){
            if(array[i]>max){
                max = array[i];
            }
        }
        return max;
    }

    class IllegalAssignmentException extends Exception{
        IllegalAssignmentException(String message){
            super(message);
        }
    }
}