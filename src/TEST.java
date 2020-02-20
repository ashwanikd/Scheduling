import java.util.Arrays;
import java.util.LinkedList;

public class TEST {
    public static void main(String args[]){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i=1;i<11;i++){
            list.add((int) (Math.random()*(double)i*100.0));
        }
        Integer[] t = Arrays.copyOf(list.toArray(),list.size(),Integer[].class);
        System.out.println(list);
        for(int i=0;i<10;i++){
            System.out.print(" "+t[i].intValue());
        }

    }
}
