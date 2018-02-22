public class Matrix implements Mat{
  private double[][] data;
  private int rows;
  private int cols;

  public Matrix(int r, int c) {
    this.data = new double[r][c];
    this.rows = r;
    this.cols = c;
  }

  public Matrix(double[] d) {
    this.data = new double[1][d.length];
    this.rows = 1;
    this.cols = d.length;
    this.data[0] = d;
  }

  public Matrix(double[][] d) {
    if(d==null) {
      throw new RuntimeException("Data cannot be null!!");
    }
    this.rows = d.length;
    this.cols = d[0].length;
    this.data = d;
  }

  public Matrix multiply(Matrix m) {
    if(this.cols!=m.rows) {
      System.out.println(this.rows+"-"+this.cols+"\n"+m.rows+"-"+m.cols);
      throw new RuntimeException("this.cols should be equal to m.rows!!");
    }
    Matrix matrix = new Matrix(this.rows, m.cols);
    for(int i=0; i<matrix.rows; i++) {
      for(int j=0; j<matrix.cols; j++) {
        matrix.data[i][j]=0;
        for(int k=0; k<this.cols; k++) {
          matrix.data[i][j]+=this.data[i][k]*m.data[k][j];
        }
      }
    } return matrix;
  }

  public Matrix multiply(double d) {
    Matrix matrix = new Matrix(this.rows,this.cols);
    for(int i=0; i<matrix.rows; i++) {
      for(int j=0; j<matrix.cols; j++) {
        matrix.data[i][j]=this.data[i][j]*d;
      }
    } return matrix;
  }

  public Matrix multiply2(Matrix m) {
    if(this.rows!=m.rows || this.cols!=m.cols) {
      throw new RuntimeException("dimensions are not convenient!");
    }
    Matrix matrix = new Matrix(this.rows,this.cols);
    for(int i=0; i<matrix.rows; i++) {
      for(int j=0; j<matrix.cols; j++) {
        matrix.data[i][j]=(this.data[i][j]*m.data[i][j]);
      }
    } return matrix;
  }

  public Matrix add(Matrix m) {
    return add(m,1);
  }
  public Matrix subtract(Matrix m) {
    return add(m,-1);
  }

  public Matrix add(Matrix m, int op) {
    if(this.rows!=m.rows || this.cols!=m.cols) {
      System.out.println(this.rows+"-"+this.cols+"\n"+m.rows+"-"+m.cols);
      throw new RuntimeException("this.rows==m.rows && this.cols!=m.cols");
    }
    Matrix matrix = new Matrix(this.rows,this.cols);
    for(int i=0; i<matrix.rows; i++) {
      for(int j=0; j<matrix.cols; j++) {
        matrix.data[i][j]=(this.data[i][j]+m.data[i][j]*op);
      }
    }
    return matrix;
  }

  public Matrix add(double f) {
    Matrix matrix = new Matrix(this.rows,this.cols);
    for(int i=0; i<this.rows; i++) {
      for(int j=0; j<this.cols; j++) {
        matrix.data[i][j]+=f;
      }
    } return matrix;
  }

  public Matrix map(Map cal) {
    Matrix matrix = new Matrix(this.rows, this.cols);
    for(int i=0; i<this.rows; i++) {
      for(int j=0; j<this.cols; j++) {
        matrix.data[i][j]=cal.map(this.data[i][j]);
      }
    } return matrix;
  }

  public Matrix transpose() {
    Matrix answer = new Matrix(this.cols,this.rows);
    for(int i=0; i<this.rows; i++) {
      for(int j=0; j<this.cols; j++) {
        answer.data[j][i] = this.data[i][j];
      }
    } return answer;
  }

  public void randomize() {
    for(int i=0; i<this.rows; i++) {
      for(int j=0; j<this.cols; j++)
        data[i][j]=Math.random()*2-1;
    }
  }

  public int rows() { return rows; }
  public int cols() { return cols; }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    // sb.append(this.rows+"-"+this.cols+"\n");
    for(int i=0; i<this.rows; i++) {
      for(int j=0; j<this.cols; j++) {
        sb.append(data[i][j]+"\t");
      }
      sb.append("\n");
    }return sb.toString();
  }
}
