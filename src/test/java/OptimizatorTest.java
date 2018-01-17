import Jama.Matrix;
import org.junit.*;
import static org.junit.Assert.*;


/**
 * Created by Eoller on 15-Jan-18.
 */
public class OptimizatorTest {

    @Test
    public void optimize() throws Exception {
        double[][] a = {{5,1,7,2},{3,9,4,2},{6,8,5,3},{7,4,1,8}};
        double[][] b = {{2,3,5,8}};
        int [] binaryPos = {0,3};
        int len = 4;
        Matrix A = Matrix.constructWithCopy(a);
        Matrix B = Matrix.constructWithCopy(b);
        Optimizator optimizator = new Optimizator(A,B,binaryPos,len);
        optimizator.optimize();
       // Assert.assertEquals();
    }

    @Test
    public void prepareNode() throws Exception {

    }

}