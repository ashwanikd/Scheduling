package levelDivision1;


import DAG.TaskGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Algorithm {
    TaskGraph data;
    double avail[];
    boolean scheduled[];
    double finishtime[];
    int assignment[];
    int taskassignment[];
    double[][] c;
    double[] sum;
    int[][] proc_ass;
    double[] tmax,cmax;
    boolean proc_avail[];
    public double load_balance;
    LinkedList<Integer> meta_task;
    ArrayList<Integer> available_Processors;

    public Algorithm(TaskGraph data){
        this.data = data;
        proc_ass = new int[data.num_of_processors][];
        sum = new double[data.num_of_processors];
        meta_task = new LinkedList<>();
        available_Processors = new ArrayList<>();
        avail = new double[data.num_of_processors];
        scheduled = new boolean[data.num_of_tasks];
        finishtime = new double[data.num_of_tasks];
        tmax = new double[data.num_of_tasks];
        cmax = new double[data.num_of_tasks];
        setTmaxCmax();
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

    void setTmaxCmax(){
        for(int i=0;i<data.num_of_tasks;i++){
            tmax[i] = maximum(data.cost[i])+1;
        }
        for(int t=0;t<data.num_of_tasks;t++){
            cmax[t] = 1;
            for(int i=0;i<data.num_of_tasks;i++){
                if(data.adj_mat[i][t]>cmax[t]){
                    cmax[t] = data.adj_mat[i][t];
                }
            }
        }
    }

    public void schedule(){
        try{
            boolean check;
            Iterator it,it1;
            int t,p;
            double[] temp;
            Integer[] tasks;
            int[] argTask;
            int num_of_t,num_of_p,k;
            while(meta_task.size()!=0){

                // making c matrix
                it = meta_task.listIterator();
                while(it.hasNext()){
                    t = (int)it.next();
                    for(int i=0;i<data.num_of_processors;i++){
                        c[t][i] = EFT(t,i);
                    }
                }
                available_Processors = new ArrayList<>();

                // initializing the processors set
                for(int i=0;i<data.num_of_processors;i++){
                    available_Processors.add(i);
                }

                //num_of_p = available_Processors.size();
                //num_of_t = meta_task.size();
                //k = (int)Math.ceil(((double)num_of_t/(double)num_of_p));

                // dividing and scheduling
                while (meta_task.size() != 0){
                    num_of_p = available_Processors.size();
                    num_of_t = meta_task.size();
                    k = (int)Math.ceil(((double)num_of_t/(double)num_of_p));

                    // temprary purpose
                    tasks = Arrays.copyOf(meta_task.toArray(),meta_task.size(),Integer[].class);

                    // calculating sum
                    it = available_Processors.iterator();
                    while(it.hasNext()){
                        p = (int)it.next();
                        temp = new double[meta_task.size()];
                        for(int i=0;i<tasks.length;i++){
                            temp[i] = c[tasks[i].intValue()][p]*(tmax[tasks[i].intValue()]/cmax[tasks[i].intValue()]);
                        }
                        argTask = argSort(temp);

                        sum[p] = 0;
                        for(int i=0;i<k;i++){
                            sum[p] += c[tasks[argTask[i]]][p]*(tmax[tasks[argTask[i]]]/cmax[tasks[argTask[i]]]);
                        }

                        proc_ass[p] = new int[argTask.length];
                        for(int i=0;i<argTask.length;i++){
                            proc_ass[p][i] = tasks[argTask[i]];
                        }
                    }

                    // selecting processor with minimum sum
                    int minp=0;double min = Double.MAX_VALUE;
                    it1 = available_Processors.iterator();
                    while(it1.hasNext()){
                        p = (int)it1.next();
                        if(sum[p] < min){
                            min = sum[p];
                            minp = p;
                        }
                    }
                    double x;
                    for(int i=0;i<k;i++){
                        //System.out.println(proc_ass[minp][i]);
                        x = EFT(proc_ass[minp][i],minp);
                        finishtime[proc_ass[minp][i]] = x;
                        taskassignment[proc_ass[minp][i]] = minp;
                        avail[minp] = x;
                        scheduled[proc_ass[minp][i]] = true;
                        assignment[minp] = proc_ass[minp][i];
                        meta_task.remove(Integer.valueOf(proc_ass[minp][i]));
                    }
                    available_Processors.remove(Integer.valueOf(minp));

                    // update c matrix
                    it = meta_task.listIterator();
                    while(it.hasNext()){
                        t = (int)it.next();
                        for(int i=0;i<data.num_of_processors;i++){
                            c[t][i] = EFT(t,i);
                        }
                    }

                }

                //System.out.println();

                // adding next level

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

    int result[];
    int[] argSort(double[] array){
        result = new int[array.length];
        for(int i=0;i<array.length;i++){
            result[i] = i;
        }
        int temp;
        // selection sort in increasing order
        for(int i=0;i<array.length;i++){
            for(int j=i+1;j<array.length;j++){
                if(array[result[i]]>array[result[j]]) {
                    temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;
                }
            }
        }
        return result;
    }

    int argMin(double[] array){
        double min = Double.MAX_VALUE;
        int argmin = 0;
        for(int i=0;i<array.length;i++){
            if(min>array[i]){
                min = array[i];
                argmin = i;
            }
        }
        return argmin;
    }

    double EFT(int task,int processor) throws IllegalAssignmentException {
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
