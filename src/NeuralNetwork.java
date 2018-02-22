public class NeuralNetwork {

  //weights from input to hidden
  private Matrix weights_itoh;
  //weights from hidden to input
  private Matrix weights_htoo;
  //biases on hidden layer
  private Matrix bias_hidden;
  //biases on output layer
  private Matrix bias_output;
  //learning_rate
  private double learning_rate=0.1;

  public NeuralNetwork(int i, int h, int o) {
      weights_itoh = new Matrix(h,i);
      weights_htoo = new Matrix(o,h);
      bias_hidden = new Matrix(h,1);
      bias_output = new Matrix(o,1);
      bias_hidden.randomize();
      bias_output.randomize();
      weights_itoh.randomize();
      weights_htoo.randomize();
  }

  public void reset() {
    bias_hidden.randomize();
    bias_output.randomize();
    weights_itoh.randomize();
    weights_htoo.randomize();
  }

  public Matrix feedForward(double[] f) {
    Matrix inputs = new Matrix(f).transpose();

    Matrix hidden = weights_itoh.multiply(inputs);
    hidden=hidden.add(bias_hidden);
    hidden=hidden.map((x)->sigmoid(x));

    Matrix output = weights_htoo.multiply(hidden);
    output=output.add(bias_output);
    output=output.map((x)->sigmoid(x));

    return output;
  }

  public void backPropagation(double[] in, double[] target) {
    Matrix inputs = new Matrix(in).transpose();
    Matrix targets = new Matrix(target).transpose();

    //FeedForward once to get the matrices
    Matrix hidden = weights_itoh.multiply(inputs);
    hidden=hidden.add(bias_hidden);
    hidden=hidden.map((x)->sigmoid(x));

    Matrix outputs = weights_htoo.multiply(hidden);
    outputs=outputs.add(bias_output);
    outputs=outputs.map((x)->sigmoid(x));

    //BackPropagation

    Matrix error = targets.subtract(outputs);

    //Implementing Gradient Descent on hidden layer
    Matrix gradients=outputs.map((x)->derivative_sigmoid(x));
    gradients=outputs.multiply2(error);
    gradients=gradients.multiply(this.learning_rate);

    Matrix hidden_transpose = hidden.transpose();
    Matrix weights_htoo_deltas = gradients.multiply(hidden_transpose);
    this.weights_htoo=this.weights_htoo.add(weights_htoo_deltas);
    this.bias_output=bias_output.add(gradients);


    Matrix weights_htoo_tranpose = weights_htoo.transpose();
    Matrix hidden_errors = weights_htoo_tranpose.multiply(error);

    //Implementing Gradient Descent on input layer
    Matrix hidden_gradient = hidden.map((x)->derivative_sigmoid(x));
    hidden_gradient=hidden_gradient.multiply2(hidden_errors);
    hidden_gradient=hidden_gradient.multiply(this.learning_rate);

    Matrix inputs_transpose = inputs.transpose();
    Matrix weights_itoh_deltas = hidden_gradient.multiply(inputs_transpose);
    this.weights_itoh=this.weights_itoh.add(weights_itoh_deltas);
    this.bias_hidden=bias_hidden.add(hidden_gradient);
  }

  private double sigmoid(double f) {
    return (1/(1+Math.exp(-f)));
  }

  private double derivative_sigmoid(double f) {
    return f*(1-f);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.weights_itoh);
    sb.append("\n");
    sb.append(this.weights_htoo);
    sb.append("\n");
    return sb.toString();
  }

}
