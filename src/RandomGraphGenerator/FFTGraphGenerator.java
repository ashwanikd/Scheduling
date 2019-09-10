package RandomGraphGenerator;

import java.io.*;

public class FFTGraphGenerator {

    int num_of_points;
    int num_of_machines;
    int machine_com_matrix[][];
    double comm_var;
    double task_var,machine_var;
    double machine_heterogenity;
    double task_heterogenity;
    double edge_weight;
    double CCR;
    double adj_matrix[][];
    double ETCMatrix[][];
    int count[];
    String filename;
    String path = "DataSet/FFT/";
    int FFTstate;
    FFT fft;
    ETCMatrixGenerator ETCgenerator;

    FFTGraphGenerator(){
        fft = new FFT();
        count = new int[65];
        ETCgenerator = new ETCMatrixGenerator();
        for(int i=0;i<3;i++) {
            /*
            task_var = 0.5;
            machine_var = 0.5;
            num_of_machines = 5;
            task_heterogenity = 0.5;
            machine_heterogenity = 0.5;
            num_of_points = 4;
            FFTstate = 0;
            edge_weight = 80;
            CCR = 0.5;
            */

            for(task_heterogenity = 0.05;task_heterogenity<=1;task_heterogenity+=0.4){ //3
                for(machine_heterogenity = 0.05;machine_heterogenity<=1;machine_heterogenity+=0.4){ //3
                    for(num_of_machines = 4;num_of_machines<=32;num_of_machines+=4){ //8
                        for(num_of_points = 2;num_of_points<=64;num_of_points*=2){ //6
                            for(FFTstate=0;FFTstate<2;FFTstate++){ //2
                                for(edge_weight=1024;edge_weight<=2000;edge_weight*=2){ //1
                                    for(CCR = 0.1;CCR<=4;CCR+=0.3){ //13
                                        for(task_var=0.1;task_var<=1.5;task_var+=0.3){//5
                                            for(machine_var=0.1;machine_var<=1.5;machine_var+=0.3){//5
                                                makeDAG();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            FFTstate = 2;
            for(task_heterogenity = 0.05;task_heterogenity<=2;task_heterogenity+=0.4){//3
                for(machine_heterogenity = 0.05;machine_heterogenity<=2;machine_heterogenity+=0.4){//3
                    for(num_of_machines = 4;num_of_machines<=32;num_of_machines+=4){//8
                        for(num_of_points = 2;num_of_points<=64;num_of_points*=2){//6
                            for(comm_var=0.0;comm_var<=1;comm_var+=0.5){
                                for(edge_weight=1024;edge_weight<=2000;edge_weight*=2){//1
                                    for(CCR = 0.1;CCR<4;CCR+=0.3){//13
                                        for(task_var=0.0;task_var<=1;task_var+=0.3){
                                            for(machine_var=0.0;machine_var<=1;machine_var+=0.3){
                                                makeDAG();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    void makeDAG(){
        machine_com_matrix = new int[num_of_machines][num_of_machines];
        for(int i=0;i<num_of_machines;i++){
            for(int j=0;j<num_of_machines;j++){
                if(i == j)
                    machine_com_matrix[i][j] = 0;
                else machine_com_matrix[i][j] = 1;
            }
        }
        if(FFTstate<2)
            fft.generateGraph(FFTstate,num_of_points,edge_weight);
        else fft.generateGraph(FFTstate,num_of_points,edge_weight,comm_var);
        fft.generateMatrix();
        adj_matrix = fft.matrix;
        double avg_comm = fft.avg_comm;
        double needed_comp = avg_comm/CCR;

        ETCgenerator.num_of_tasks = fft.num_of_nodes;
        ETCgenerator.machine_heterogeneity = machine_heterogenity;
        ETCgenerator.task_heterogeneity = task_heterogenity;
        ETCgenerator.task_var = task_var;
        ETCgenerator.machine_var = machine_var;
        ETCgenerator.num_of_machines = num_of_machines;
        ETCgenerator.task_mean = needed_comp;
        ETCgenerator.generate();

        ETCMatrix = ETCgenerator.ETC;

        filename = path+num_of_points+"/"+(++count[num_of_points])+".txt";
        //System.out.println(filename);
        File file = new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(filename,false);
            writer.append(""+ETCgenerator.num_of_tasks);
            writer.append(" "+num_of_machines);
            writer.append('\n');
            //fft.printmatrix(ETCMatrix);
            for(int i=0;i<ETCMatrix.length;i++){
                for(int j=0;j<ETCMatrix[i].length;j++){
                    writer.append(ETCMatrix[i][j]+" ");
                }
                writer.append('\n');
            }
            for(int i=0;i<num_of_machines;i++){
                for(int j=0;j<num_of_machines;j++){
                    writer.append(machine_com_matrix[i][j]+" ");
                }
                writer.append('\n');
            }
            for(int i=0;i<adj_matrix.length;i++){
                for(int j=0;j<adj_matrix[i].length;j++){
                    writer.append(adj_matrix[i][j]+" ");
                }
                writer.append('\n');
            }
            writer.close();
            System.out.println("Successfully made : "+filename);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
