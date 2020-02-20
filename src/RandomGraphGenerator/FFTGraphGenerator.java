package RandomGraphGenerator;

import java.io.*;

/**
 * @author ashwani kumar dwivedi
 */

public class FFTGraphGenerator {

    int num_of_points;
    int num_of_machines;
    int machine_com_matrix[][];
    double comm_var;
    double machine_heterogenity;
    double task_heterogenity;
    double edge_weight;
    double CCR;
    double adj_matrix[][];
    double ETCMatrix[][];
    static int count[];
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

            for(task_heterogenity = 0.1;task_heterogenity<=1.5;task_heterogenity+=0.3){ //5
                for(machine_heterogenity = 0.1;machine_heterogenity<=1.5;machine_heterogenity+=0.3){ //5
                    for(num_of_machines = 4;num_of_machines<=32;num_of_machines+=4){ //8
                        for(num_of_points = 2;num_of_points<=32;num_of_points*=2){ //5
                            for(FFTstate=0;FFTstate<2;FFTstate++){ //2
                                for(edge_weight=2048;edge_weight<=2048;edge_weight*=2){ //1
                                    for(CCR = 0.1;CCR<=3;CCR+=0.3){ //13
                                        makeDAG();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            FFTstate = 2;
            for(task_heterogenity = 0.1;task_heterogenity<=1.5;task_heterogenity+=0.3){//5
                for(machine_heterogenity = 0.1;machine_heterogenity<=1.5;machine_heterogenity+=0.3){//5
                    for(num_of_machines = 4;num_of_machines<=32;num_of_machines+=4){//8
                        for(num_of_points = 2;num_of_points<=32;num_of_points*=2){//5
                            for(comm_var=0.5;comm_var<=0.5;comm_var+=0.5){
                                for(edge_weight=2048;edge_weight<=2048;edge_weight*=2){//1
                                    for(CCR = 0.1;CCR<3;CCR+=0.3){//13
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
        if(needed_comp>0.2) {
            ETCgenerator.num_of_tasks = fft.num_of_nodes;
            ETCgenerator.task_var = task_heterogenity;
            ETCgenerator.machine_var = machine_heterogenity;
            ETCgenerator.num_of_machines = num_of_machines;
            ETCgenerator.task_mean = needed_comp;
            ETCgenerator.generate();

            ETCMatrix = ETCgenerator.ETC;

            filename = path + num_of_points + "/" + (++count[num_of_points]) + ".txt";
            //System.out.println(filename);
            File file = new File(filename);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(filename, false);
                writer.append("" + ETCgenerator.num_of_tasks);
                writer.append(" " + num_of_machines);
                writer.append('\n');
                //fft.printmatrix(ETCMatrix);
                for (int i = 0; i < ETCMatrix.length; i++) {
                    for (int j = 0; j < ETCMatrix[i].length; j++) {
                        writer.append(ETCMatrix[i][j] + " ");
                    }
                    writer.append('\n');
                }
                for (int i = 0; i < num_of_machines; i++) {
                    for (int j = 0; j < num_of_machines; j++) {
                        writer.append(machine_com_matrix[i][j] + " ");
                    }
                    writer.append('\n');
                }
                for (int i = 0; i < adj_matrix.length; i++) {
                    for (int j = 0; j < adj_matrix[i].length; j++) {
                        writer.append(adj_matrix[i][j] + " ");
                    }
                    writer.append('\n');
                }
                writer.close();
                System.out.println("Successfully made : " + filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
