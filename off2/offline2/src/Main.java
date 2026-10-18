import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
            Scanner sc = new Scanner(new File("input.txt"));
            System.setOut(new PrintStream("output.txt"));
            int n = sc.nextInt();
            int m = sc.nextInt();

            BellmanFord bellman = new BellmanFord(n, m);
            Dijkstra dijkstra = new Dijkstra(n, m);

            for(int i = 0 ; i < m ; i++){
                int u = sc.nextInt();
                int v = sc.nextInt();
                int w = sc.nextInt();
                bellman.addEdge(u, v, w);
                dijkstra.addEdge(u, v, w);
            }

            int s = sc.nextInt();
            int d = sc.nextInt();

            //for Bellman Ford
            System.out.println("Bellman Ford Algorithm : ");
            if(bellman.bellmanFord(s)){
                bellman.printShortestPath(s, d);
            }

            else{
                System.out.println("Negative weight cycle present");
            }
            System.out.println();

            //for dijkstra
            System.out.println("Dijkstra Algorithm:");
            //List<Integer> shortestPath = dijkstra.findShortestPath(s, d);
            dijkstra.printShortestDistance(s, d);
    }
}
