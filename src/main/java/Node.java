import Jama.Matrix;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Stream;

/**
 * Created by Eoller on 13-Jan-18.
 */
public class Node {

    private Matrix fMin;
    private int[] vector;
    private Matrix Aprim;
    private Matrix Bprim;
    private Matrix C;
    private int level;
    private int position;

    public Node(int[] vector, int position) {
        this.vector = vector;
        System.out.println("-----------------------------------------------------");
        for (int i = 0 ; i < vector.length; i ++)
            System.out.println(i + ":" + vector[i]);
        System.out.println("-----------------------------------------------------");
        this.position = position + 1;
    }

    public void findAllValues(){
        initializeFun();
    }


    private void initializeFun(){
        int binaryPosCount = 0;
        for (int i = 0 ; i < vector.length; i ++){
            if(vector[i] != -1){
                binaryPosCount ++;
            }
        }
        int [] notBinaryPos = new int[vector.length - binaryPosCount];
        int [] binaryPos = new int[binaryPosCount];
        int indexForBinary = 0;
        int indexForNotBinary = 0;
        for (int i = 0 ; i < vector.length ; i ++) {
            if (vector[i] == -1) {
                notBinaryPos[indexForNotBinary] = i;
                indexForNotBinary++;
            }
            else {
                binaryPos[indexForBinary] = i;
                indexForBinary++;
            }
        }
        System.out.println("---------------------------------------------------------------------------------");
        //Stream.of(notBinaryPos).forEach(x-> System.out.println(x));
        /*for(int i = 0 ; i < binaryPos.length ; i ++){
            System.out.println("Binary position: " + binaryPos[i]);
        }
        for(int i = 0 ; i < notBinaryPos.length ; i ++){
            System.out.println("Not binary position: " + notBinaryPos[i]);
        }*/
        for (int i = 0 ; i < vector.length; i ++){
            System.out.println("[" + i + "]" + vector[i]);
        }
        //Create Aprim for finding f min
        //Optimizator.A.print(1,0);
        //Optimizator.A.getMatrix(notBinaryPos,notBinaryPos).print(1,0);
        this.Aprim = Optimizator.A.getMatrix(notBinaryPos,notBinaryPos);

        double[][] vectorPrim = new double[1][vector.length];
        for(int i = 0 ; i < vector.length; i ++){
            vectorPrim[0][i] = vector[i];
        }

        //double[][] AprimForC = new double[binaryPos.length][binaryPos.length];
        int[] zeroArray = {0};
        Matrix vectorForC = Matrix.constructWithCopy(vectorPrim).getMatrix(zeroArray , binaryPos);
        Matrix AmodForC = Optimizator.A.getMatrix(binaryPos,binaryPos);
        Matrix BmodForC = Optimizator.B.getMatrix(zeroArray, binaryPos);

        this.C = vectorForC.times(AmodForC).times(vectorForC.transpose()).plus(BmodForC.times(vectorForC.transpose()));


        double[][] BprimForCopy = new double[1][notBinaryPos.length];
        for(int i = 0; i < notBinaryPos.length; i++){
            int summ = 0;
            for(int j = 0 ; j < binaryPos.length; j++){
                int res = (int) Optimizator.A.get(notBinaryPos[i],binaryPos[j]);
                int res2 = (int) Optimizator.A.get(binaryPos[j],notBinaryPos[i]);
                summ = summ + (res + res2)*vector[binaryPos[j]];
            }
            BprimForCopy[0][i] = summ + Optimizator.B.get(0,notBinaryPos[i]);
        }
        this.Bprim = Matrix.constructWithCopy(BprimForCopy);
        System.out.println("Bprim: ");
        this.Bprim.print(1,2);
        System.out.println("Aprim: ");
        this.Aprim.print(1,2);
        System.out.println("C: ");
        this.C.print(1,2);


        this.fMin = Bprim.times(Aprim.inverse()).times(Bprim.transpose()).times(-0.5).plus(C);
        System.out.println("F MIN:");
        fMin.print(1,0);
    }





    private void init(){
        int binaryPosCount = 0;
        for (int i = 0 ; i < vector.length; i ++){
            if(vector[i] != -1){
                binaryPosCount ++;
            }
        }
        int [] notBinaryPos = new int[vector.length - binaryPosCount];
        int [] binaryPos = new int[binaryPosCount];
        int indexForBinary = 0;
        int indexForNotBinary = 0;
        for (int i = 0 ; i < vector.length ; i ++) {
            if (vector[i] == -1) {
                notBinaryPos[indexForNotBinary] = i;
                indexForNotBinary++;
            }
            else {
                binaryPos[indexForBinary] = i;
                indexForBinary++;
            }
        }


        //to delete
        double[][] A = {{2,3,2,5},{8,1,7,9},{4,5,7,9},{7,4,6,5}};
        double[][] b = {{8,3,4,5}};
        //Matrix.constructWithCopy(A).print(1,0);
        //Matrix.constructWithCopy(b).print(1,0);
        //to delete

        Matrix AprimForFmin = Matrix.constructWithCopy(A).getMatrix(notBinaryPos,notBinaryPos);
        //FIND C below
        double[][] vectorPrim = new double[1][vector.length];
        for(int i = 0 ; i < vector.length; i ++){
            vectorPrim[0][i] = vector[i];
        }
       // double[][] AprimForC = new double[binaryPos.length][binaryPos.length];
        int[] zeroArray = {0};
        Matrix vectorForC = Matrix.constructWithCopy(vectorPrim).getMatrix(zeroArray , binaryPos);
        Matrix AmodForC = Matrix.constructWithCopy(A).getMatrix(binaryPos,binaryPos);
        Matrix BmodForC = Matrix.constructWithCopy(b).getMatrix(zeroArray, binaryPos);
        System.out.println("AmodForC:");
        AmodForC.print(1,0);
        //System.out.println("vectorForC:");
        //vectorForC.print(1,0);

        Matrix C = vectorForC.times(AmodForC).times(vectorForC.transpose()).plus(BmodForC.times(vectorForC.transpose()));
        System.out.println("C:");
        C.print(1,0);

        double[][] BprimForCopy = new double[1][notBinaryPos.length];
        for(int i = 0; i < notBinaryPos.length; i++){
            int summ = 0;
            for(int j = 0 ; j < binaryPos.length; j++){
                int res = (int) Matrix.constructWithCopy(A).get(notBinaryPos[i],binaryPos[j]);
                int res2 = (int) Matrix.constructWithCopy(A).get(binaryPos[j],notBinaryPos[i]);
                summ = summ + (res + res2)*vector[binaryPos[j]];
            }
            BprimForCopy[0][i] = summ + Matrix.constructWithCopy(b).get(0,notBinaryPos[i]);
        }

        System.out.println("B PRIMM: ");
        Matrix.constructWithCopy(BprimForCopy).print(1,0);

        System.out.println("A PRIM: ");
        AprimForFmin.print(1,0);
        System.out.println("A Prim TANSP:");
        AprimForFmin.inverse().print(1,2);
        Matrix fMin = Matrix.constructWithCopy(BprimForCopy).times(AprimForFmin.inverse());
        System.out.println("F MIN : ");
        fMin.print(1,0);


        //Dopisac nachozdenie B oraz C
    }

    public void findAnotherVar(){
        Matrix vectorNonBinaryValues = Aprim.inverse().times(Bprim.transpose()).times(-1);
        System.out.println("VECTOR OF NON BINARY VALUES: ");
        vectorNonBinaryValues.print(4,2);
    }

    public static void main(String[] args) {
        int[] vector = {1,-1,1,-1};
        Node node = new Node(vector,2);
    }










    public Matrix getfMin() {
        return fMin;
    }

    public void setfMin(Matrix fMin) {
        this.fMin = fMin;
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }

    public Matrix getAprim() {
        return Aprim;
    }

    public void setAprim(Matrix aprim) {
        Aprim = aprim;
    }

    public Matrix getBprim() {
        return Bprim;
    }

    public void setBprim(Matrix bprim) {
        Bprim = bprim;
    }

    public Matrix getC() {
        return C;
    }

    public void setC(Matrix c) {
        C = c;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
