package HEFT;

import DAG.TaskGraph;

/**
 * @author ashwani kumar dwivedi
 */

public class Algorithm {
    TaskGraph data;
    public double up_rank[];
    boolean setrank[];
    public double down_rank[];
    public double avg_work[];
    double avail[];
    double EFT[];
    int assignment[];
    boolean assigned[];
    int[] order;

    public Algorithm(TaskGraph data){
        this.data = data;
        up_rank = new double[data.num_of_tasks];
        down_rank = new double[data.num_of_tasks];
        avg_work = new double[data.num_of_tasks];
        setavgwork();
        setrank = new boolean[data.num_of_tasks];
        setupwardrank(data.starttask);
        setrank = new boolean[data.num_of_tasks];
        setdownwardrank(data.exit_task);
        order = new int[data.num_of_tasks];
        avail = new double[data.num_of_processors];
        EFT = new double[data.num_of_tasks];
        assignment = new int[data.num_of_tasks];
        assigned = new boolean[data.num_of_tasks];
    }

    public void schedule(){
        try{
            schedule1();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public double schedulelength(){
        double max = -1;
        for(int i=0;i<data.num_of_tasks;i++){
            if(max<EFT[i])
                max = EFT[i];
        }
        return max;
    }

    public void schedule1() throws IllegalAssignmentException{
        for(int i=0;i<data.num_of_tasks;i++){
            order[i] = i;
        }
        sort();
        double max;
        int task,proc=0;
        double eft;
        for(int i=0;i<data.num_of_tasks;i++){
            task = order[i];
            eft = Double.MAX_VALUE;
            for(int j=0;j<data.num_of_processors;j++){
                max = 0;
                for(int k=0;k<data.num_of_tasks;k++){
                    if(data.adj_mat[k][task] != -1){
                        if(assigned[k]){
                            max = maximum(max,EFT[k]+data.proc_com[assignment[k]][j]*data.adj_mat[k][task]);
                        }else throw new IllegalAssignmentException("task "+k+" must be assigned before task "+task);
                    }
                }
                max = maximum(avail[j],max);
                max += data.cost[task][j];
                if(eft>max){
                    eft = max;
                    proc = j;
                }
            }
            assignment[task] = proc;
            EFT[task] = eft;
            avail[proc] = eft;
            assigned[task] = true;
        }
    }

    // selection sort
    void sort(){
        int temp;
        for(int i=0;i<data.num_of_tasks;i++){
            for(int j=i+1;j<data.num_of_tasks;j++){
                if(up_rank[order[i]]<up_rank[order[j]]){
                    temp = order[i];
                    order[i] = order[j];
                    order[j] = temp;
                }
            }
        }
    }

    public void printarray(double array[]){
        for(int i=0;i<array.length;i++){
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }
    void setavgwork(){
        for(int i=0;i<data.num_of_tasks;i++){
            avg_work[i] = avg(data.cost[i]);
        }
    }


    double setupwardrank(int task){
        if(setrank[task])
            return up_rank[task];
        if(task == data.exit_task){
            up_rank[task] = avg_work[task];
            setrank[task] = true;
            return up_rank[task];
        }else {
            double max = -1;
            for(int i=0;i<data.num_of_tasks;i++){
                if(data.adj_mat[task][i] != -1){
                    max = maximum(max,(data.adj_mat[task][i]+setupwardrank(i)));
                }
            }
            up_rank[task] = max+avg_work[task];
            setrank[task] = true;
            return up_rank[task];
        }
    }

    double setdownwardrank(int task){
        if(setrank[task])
            return down_rank[task];
        if(task == data.starttask){
            down_rank[task] = 0;
            setrank[task] = true;
            return down_rank[task];
        }else {
            double max = -1;
            for(int i=0;i<data.num_of_tasks;i++){
                if(data.adj_mat[i][task] != -1){
                    max = maximum(max,(setdownwardrank(i)+data.adj_mat[i][task]+avg_work[i]));
                }
            }
            down_rank[task] = max;
            setrank[task] = true;
            return down_rank[task];
        }
    }

    double maximum(double a,double b){
        if(a<b)
            return b;
        else return a;
    }

    double avg(double array[]){
        double sum=0;
        for(int i=0;i<array.length;i++){
            sum+=array[i];
        }
        sum/=array.length;
        return sum;
    }

    class IllegalAssignmentException extends Exception{
        IllegalAssignmentException(String message){
            super(message);
        }
    }
}
