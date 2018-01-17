import Jama.Matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;

/**
 * Created by Eoller on 14-Jan-18.
 */
public class Optimizator {

    public static Matrix A;
    public static Matrix B;
    public int[] binaryPositions;
    private int inputVectorLength;

    public Optimizator(Matrix a, Matrix b, int[] binaryPositions, int inputVectorLength) {
        A = a;
        B = b;
        this.binaryPositions = binaryPositions;
        this.inputVectorLength = inputVectorLength;
    }

    public Node optimize(){

        int [] vector = new int[inputVectorLength];
        for (int i = 0; i < vector.length; i++)
            vector[i] = -1;
        LinkedList<Node> nodeList = new LinkedList<>();
        Node firstNode = new Node(vector, -1);
        nodeList.add(prepareNode(firstNode,1));
        nodeList.add(prepareNode(firstNode,0));

        while (true){
            double fMin = Double.POSITIVE_INFINITY;
            Node answer = null;
            for (Node elem: nodeList) {
                elem.findAllValues();
                if(elem.getfMin().get(0,0) < fMin){
                    answer = elem;
                    fMin = elem.getfMin().get(0,0);
                }
            }
            if(answer.getPosition() >= binaryPositions.length) {
                System.out.println("I found answer: ");
                int [] answerVector = answer.getVector();
                answer.findAnotherVar();
                for(int i = 0; i < answer.getVector().length; i++)
                    System.out.println("[" + i + "]" + answerVector[i]);
                System.out.println("F MIN NA KONCU: " + answer.getfMin().get(0,0));
                return answer;
            }
            nodeList.add(prepareNode(answer,1));
            nodeList.add(prepareNode(answer,0));
            nodeList.remove(answer);
            for (int i = 0 ; i < nodeList.size() ; i ++){
                System.out.print(" [");
                    for (int j = 0 ; j < nodeList.get(i).getVector().length; j ++){
                        System.out.print(nodeList.get(i).getVector()[j] + ",");
                    }
                System.out.print("] ");
            }
        }

    }

    public Node prepareNode(Node node, int value){
        int [] temp = Arrays.copyOf(node.getVector(), node.getVector().length);
        temp[binaryPositions[node.getPosition()]] = value;
        return new Node(temp, node.getPosition());
    }

}
