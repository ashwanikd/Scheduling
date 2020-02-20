package BruteForce;

import DAG.TaskGraph;

import java.io.File;

public class noDAG {

    TaskGraph data;

    double avail[];

    int mapping[];
    int sequence[];

    int resultSequence[];
    int resultMapping[];

    double schedule;

    void produceResult(){
        mapping = new int[data.num_of_tasks];
        sequence = new int[data.num_of_tasks];

        for(int i=0;i<data.num_of_tasks;i++){
            mapping[i] = i;
            sequence[i] = i;
        }

        avail = new double[data.num_of_processors];

        resultMapping = new int[data.num_of_tasks];
        resultSequence = new int[data.num_of_tasks];

        schedule = Double.MAX_VALUE;

        permuteSchedule(0);

        for(int i=0;i<resultSequence.length;i++){
            System.out.println((resultSequence[i]+1)+"  "+(resultMapping[i]+1));
        }
        System.out.println("optimal makespan = "+schedule);
    }

    int temp;

    void permuteSchedule(int x){
        if(x == sequence.length-1){
            permuteMapping(0);
        }else {
            for(int k=x;k<sequence.length;k++){
                temp = sequence[k];
                sequence[k] = sequence[x];
                sequence[x] = temp;
                permuteSchedule(x+1);
                temp = sequence[k];
                sequence[k] = sequence[x];
                sequence[x] = temp;
            }
        }
    }

    void permuteMapping(int x){
        if(x == mapping.length){
            double mks = makespan();
            if(mks<=schedule){
                schedule = mks;
                resultSequence = sequence.clone();
                resultMapping = mapping.clone();
                System.gc();
            }
        }else {
            for(int k=0;k<data.num_of_processors;k++){
                mapping[x] = k;
                permuteMapping(x+1);
            }
        }
    }

    double makespan(){
        /*
        for(int i=0;i<sequence.length;i++){
            System.out.println((sequence[i])+"  "+(mapping[i]));
        }

         */

        for(int i=0;i<avail.length;i++){
            avail[i] = 0;
        }

        double mks=0;
        for(int i=0;i<sequence.length;i++){
            avail[mapping[i]] += data.cost[sequence[i]][mapping[i]];
        }

        double max=0;
        for(int i=0;i<avail.length;i++){
            if(avail[i]>max){
                max = avail[i];
            }
        }
        mks = max;
        return mks;
    }


    public static void main(String args[]){
        File file = new File("Data Sets/test.txt");
        TaskGraph graph = new TaskGraph(file,true);
        //graph.printdata();
        noDAG test = new noDAG();
        test.data = graph;
        test.produceResult();
    }
}
