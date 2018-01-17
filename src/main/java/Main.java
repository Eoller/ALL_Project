import Jama.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Eoller on 13-Jan-18.
 */
public class Main {

    public static void main(String[] args) {
        double[][] a = {{2,1,7,2,8,7},{3,9,7,4,2,4},{6,2,8,5,3,2},{7,6,4,1,8,1},{9,6,5,2,1,6},{6,2,8,5,3,9}};
        double[][] b = {{2,3,5,8,7,8}};
        int [] binaryPos = {1,2,5};
        int len = 6;
        Matrix A = Matrix.constructWithCopy(a);
        Matrix B = Matrix.constructWithCopy(b);
        for (int i = 0 ; i < 1000 ; i ++){
            System.out.println("WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING WARMING");
            Optimizator optimizator = new Optimizator(A,B,binaryPos,len);
            optimizator.optimize();
        }
        System.out.println("===========================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================");
        Optimizator optimizator = new Optimizator(A,B,binaryPos,len);
        long start = System.nanoTime();
        optimizator.optimize();
        long finish = System.nanoTime();
        long timeConsumedMillis = finish - start;
        System.out.println("TIME: " + String.format("%,12d", timeConsumedMillis) + "ms.");
    }
}
