import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("input.txt"));
        System.setOut(new PrintStream("output.txt"));

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] adjMatrix = new int[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == j)
                    adjMatrix[i][j] = 0;
                else
                    adjMatrix[i][j] = Integer.MAX_VALUE / 2;
            }
        }

        for(int i = 0; i < m; i++){
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjMatrix[u][v] = sc.nextInt();
        }

        System.out.println("Shortest distance matrix for Matrix Multiplication :");
        int[][] matDist = apsp.matrixMultiplication(adjMatrix);
        apsp.printDistance(matDist);
        System.out.println();

        System.out.println("Shortest distance matrix for floyd warshall :");
        int[][] floydDist = apsp.floydWarshall(adjMatrix);
        apsp.printDistance(floydDist);


    }
}


