import java.util.Random;
public class Test {
  public static void main(String[] args) {
    double[][] xor_training_data = new double[][] {{0,0},{0,1},{1,0},{1,1}};
    double[][] xor_expected_results = new double[][]{{0},{1},{1},{0}};

    double[][] and_training_data = new double[][] {{0,0},{0,1},{1,0},{1,1}};
    double[][] and_expected_results = new double[][]{{0},{0},{0},{1}};

    NeuralNetwork nn = new NeuralNetwork(2,4,1);

    System.out.println("XORTest");
    test(nn,xor_training_data,xor_expected_results);

    System.out.println("ANDTest");
    test(nn,and_training_data,and_expected_results);
  }

  public static void test(NeuralNetwork nn, double[][] training_data, double[][] expected_results) {
    nn.reset();
    Random r = new Random();
    for(int i=0; i<100000; i++) {
      int r2 = Math.abs(r.nextInt())%(training_data.length);
      nn.backPropagation(training_data[r2],expected_results[r2]);
    }
    Matrix m = nn.feedForward(training_data[0]);
    Matrix m1 = nn.feedForward(training_data[1]);
    Matrix m2 = nn.feedForward(training_data[2]);
    Matrix m3 = nn.feedForward(training_data[3]);
    System.out.println("{0,0}:"+m+
                       "{0,1}:"+m1+
                       "{1,0}:"+m2+
                       "{1,1}:"+m3);
  }
}
