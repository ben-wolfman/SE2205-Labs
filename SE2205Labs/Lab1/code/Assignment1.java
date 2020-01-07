import java.io.*;
import java.util.Scanner;

public class Assignment1{

  //Matrix multiplication function using recursion
  public int[][] denseMatrixMult(int[][] A, int[][] B, int size)
  {
      //Declare all matrices
      int[][] m0;
      int[][] m1;
      int[][] m2;
      int[][] m3;
      int[][] m4;
      int[][] m5;
      int[][] m6;
      int[][] temp1;
      int[][] temp2;
      int[][] C00 = initMatrix(size/2);
      int[][] C01 = initMatrix(size/2);
      int[][] C10 = initMatrix(size/2);
      int[][] C11 = initMatrix(size/2);
      int[][] C = initMatrix(size);
      int[][] z = initMatrix(size);
      int[][] resultMatrix = initMatrix(size);

      //If the size is less than or equal to 1, multiply two elements
      if (size <= 1) {
        C[0][0] = A[0][0] * B[0][0];
        resultMatrix = C;
      }
      //Else, apply recursion for submatrix
      else {

        temp1 = sum(A,A,0,0,size/2,size/2,size/2);
        temp2 = sum(B,B,0,0,size/2,size/2,size/2);
        m0 = denseMatrixMult(temp1, temp2, size/2);

        temp1 = sum(A,A,size/2,0,size/2,size/2,size/2);
        temp2 = sum(B,z,0,0,0,0,size/2);
        m1 = denseMatrixMult(temp1, temp2, size/2);

        temp1 = sum(A,z,0,0,0,0,size/2);
        temp2 = sub(B,B,0,size/2,size/2,size/2,size/2);
        m2 = denseMatrixMult(temp1, temp2, size/2);

        temp1 = sum(A,z,size/2,size/2,0,0,size/2);
        temp2 = sub(B,B,size/2,0,0,0,size/2);
        m3 = denseMatrixMult(temp1, temp2, size/2);

        temp1 = sum(A,A,0,0,0,size/2,size/2);
        temp2 = sum(B,z,size/2,size/2,0,0,size/2);
        m4 = denseMatrixMult(temp1, temp2, size/2);

        temp1 = sub(A,A,size/2,0,0,0,size/2);
        temp2 = sum(B,B,0,0,0,size/2,size/2);
        m5 = denseMatrixMult(temp1, temp2, size/2);

        temp1 = sub(A,A,0,size/2,size/2,size/2,size/2);
        temp2 = sum(B,B,size/2,0,size/2,size/2,size/2);
        m6 = denseMatrixMult(temp1, temp2, size/2);

        //Store sums in each quadrant of matrix C
        for (int i = 0; i < size/2; i++) {
          for (int j = 0; j < size/2; j++) {
            C00[i][j] = (m0[i][j] + m3[i][j] - m4[i][j] + m6[i][j]);
            C01[i][j] = (m2[i][j] + m4[i][j]);
            C10[i][j] = (m1[i][j] + m3[i][j]);
            C11[i][j] = (m0[i][j] - m1[i][j] + m2[i][j] + m5[i][j]);
          }
        }

        //Populates matrix C with each new quadrant
        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
            if (i < size/2 && j < size/2) {
              C[i][j] = C00[i][j];
            }
            else if (i < size/2 && j >= size/2) {
              C[i][j] = C01[i][j - size/2];
            }
            else if (i >= size/2 && j < size/2) {
              C[i][j] = C10[i - size/2][j];
            }
            else {
              C[i][j] = C11[i - size/2][j - size/2];
            }
          }
        }
        resultMatrix = C;
      }
      return resultMatrix;
  }

  //Declares function to add sub matrices
  public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n)
  {
      //Declares matrices
      int[][] subA = new int[n][n];
      int[][] subB = new int[n][n];
      int[][] sum = new int[n][n];
      int a = 0;
      int b = 0;

      //Populates sub matrix A
      for (int i = x1; i < n+x1; i++) {
        for (int j = y1; j < n+y1; j++) {
          subA[a][b] = A[i][j];
            b++;
        }
        a++;
        b = 0;
      }

      a = 0;
      b = 0;

      //Populates sub matrix B
      for (int i = x2; i < n+x2; i++) {
        for (int j = y2; j < n+y2; j++) {
          subB[a][b] = B[i][j];
          b++;
        }
        a++;
        b = 0;
      }

      //Finds the sum of sub matrices A and B
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          sum[i][j] = subA[i][j] + subB[i][j];
        }
      }
      return sum;
  }

  //Declares function to subtract sub matrices
  public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n)
  {
    //Declares matrices
    int[][] subA = new int[n][n];
    int[][] subB = new int[n][n];
    int[][] sub = new int[n][n];
    int a = 0;
    int b = 0;

    //Finds sub matrix A
    for (int i = x1; i < n+x1; i++) {
      for (int j = y1; j < n+y1; j++) {
        subA[a][b] = A[i][j];
          b++;
      }
      a++;
      b = 0;
    }

    a = 0;
    b = 0;

    //Finds sub matrix B
    for (int i = x2; i < n+x2; i++) {
      for (int j = y2; j < n+y2; j++) {
        subB[a][b] = B[i][j];
        b++;
      }
      a++;
      b = 0;
    }

    //Finds the difference between the two sub matrices A and B
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sub[i][j] = subA[i][j] - subB[i][j];
      }
    }
    return sub;
  }

  //Declares a function to initialize matrix
  public int[][] initMatrix(int n)
  {
      return new int[n][n];
  }

  //Declares a funtion to print matrix
  public void printMatrix(int n, int[][] A)
  {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          System.out.print(A[i][j] + " ");
        }
        System.out.println();
      }
  }

  //Declares a function to read a matrix from a file
  public int[][] readMatrix(String filename, int n) throws Exception
  {
      //Declares new array
      int[][] array = new int[n][n];

      //Declares new input scanner
      Scanner inputscanner = new Scanner(new File(filename));

      //Gets array by going through file with input scanner
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          array[i][j] = inputscanner.nextInt();
        }
      }
      return array;
  }

}
