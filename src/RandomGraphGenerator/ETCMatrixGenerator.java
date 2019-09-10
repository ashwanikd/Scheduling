package RandomGraphGenerator;


import org.apache.commons.math3.distribution.GammaDistribution;
import org.jetbrains.annotations.NotNull;

public class ETCMatrixGenerator {

    double machine_heterogeneity,task_heterogeneity;
    double avg_ETC;
    double task_mean,task_var,machine_var;
    int num_of_tasks,num_of_machines;
    GammaDistribution gamma1,gamma2;
    double ETC[][];

    double alpha_task,alpha_machine,beta_task,beta_machine;

    ETCMatrixGenerator(int num_of_tasks,int num_of_machines,double task_mean,double task_var,double machine_var){
        this.num_of_tasks = num_of_tasks;
        this.num_of_machines = num_of_machines;
        this.task_mean = task_mean;
        this.task_var = task_var;
        this.machine_var = machine_var;
    }
    ETCMatrixGenerator(){

    }

    void calculateParameters(){
        alpha_task = 1/Math.pow(task_var,2);
        beta_task = task_mean/alpha_task;
        alpha_machine = 1/Math.pow(machine_var,2);
        beta_machine = task_mean/alpha_machine;
    }

    void analyse(){
        calculateHeterogenity();
        System.out.println("\nmachine heterogeneity = "+machine_heterogeneity);
        System.out.println("task heterogeneity = "+task_heterogeneity);
        System.out.println("average = "+avg_ETC);
    }

    void generate(){
        if(task_var>=machine_var){
            calculateMatrix1();
        }else {
            calculateMatrix2();
        }
    }

    void calculateHeterogenity(){
        machine_heterogeneity=0;
        double mean,var,temp,t1;
        double sum=0;
        int count = num_of_tasks;
        for(int i=0;i<num_of_tasks;i++){
            mean=0;
            t1=0;
            for(int j=0;j<num_of_machines;j++){
                mean+=ETC[i][j];
                t1+=ETC[i][j];
            }
            sum+=t1/num_of_machines;
            mean/=num_of_machines;
            var=0;
            for(int j=0;j<num_of_machines;j++) {
                temp = ETC[i][j] - mean;
                var += temp * temp;
            }
            var/=num_of_machines;

            var = Math.sqrt(var);
            if(mean>0)
                machine_heterogeneity = machine_heterogeneity + (var/mean);
            else count--;
        }
        machine_heterogeneity/=count;

        avg_ETC = sum/num_of_tasks;

        task_heterogeneity = 0;
        for(int i=0;i<num_of_machines;i++){
            mean=0;
            for(int j=0;j<num_of_tasks;j++){
                mean+=ETC[j][i];
            }
            mean/=num_of_tasks;
            var=0;
            for(int j=0;j<num_of_tasks;j++){
                temp = ETC[j][i]-mean;
                var+=temp*temp;
            }
            var/=num_of_tasks;
            var = Math.sqrt(var);
            if(mean>0)
                task_heterogeneity = task_heterogeneity + var/mean;
        }
        task_heterogeneity/=num_of_machines;
    }

    void calculateMatrix1(){
        calculateParameters();
        double s,beta_machine;
        //System.out.println(alpha_task+" "+beta_task);
        gamma1 = new GammaDistribution(alpha_task,beta_task);
        ETC = new double[num_of_tasks][num_of_machines];
        for(int i=0;i<num_of_tasks;i++){
            s = gamma1.sample();
            beta_machine = s/alpha_machine;
            gamma2 = new GammaDistribution(alpha_machine,beta_machine);
            for(int j=0;j<num_of_machines;j++){
                ETC[i][j] = gamma2.sample();
            }
        }
    }

    void calculateMatrix2(){
        calculateParameters();
        double p;
        gamma1 = new GammaDistribution(alpha_machine,beta_machine);
        ETC = new double[num_of_tasks][num_of_machines];
        for(int i=0;i<num_of_machines;i++){
            p = gamma1.sample();
            beta_task = p/alpha_task;
            gamma2 = new GammaDistribution(alpha_task,beta_task);
            for(int j=0;j<num_of_tasks;j++){
                ETC[j][i] = gamma2.sample();
            }
        }
    }

    public static void printmatrix(@NotNull double[][] matrix){
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                //if(matrix[i][j] != -1 )
                System.out.print(Math.round(matrix[i][j])+" ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        ETCMatrixGenerator generator  = new ETCMatrixGenerator(20,3,50,0.5,0.5);
        generator.generate();
        generator.printmatrix(generator.ETC);
        generator.analyse();
    }
}
