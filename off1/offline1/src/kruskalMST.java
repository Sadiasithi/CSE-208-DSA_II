import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class kruskalMST {
    int V, E;
    List<Edge>adj;

    public kruskalMST(int V, int E){
        adj = new ArrayList<>();
        this.V = V;
        this.E = E;
    }

    private void addEdge(int u, int v, float d) {
        adj.add(new Edge(u, v, d));
    }

    //find operation disjoint set
    public int find(int V, int []par){
        if(par[V] != V)
            par[V] = find(par[V] ,par);

        return par[V];
    }

    //union operation of disjoint set
    public void union(int []par, int x, int y){
        int xSet = find(x, par);
        int ySet = find(y, par);

        par[ySet] = xSet;
    }

    public void kruskal(float q){
        List<Edge>mst = new ArrayList<>();

        float total = q;
        int[] parent = new int[V];
        for(int i = 0 ; i < V ; i++){
            parent[i] = i;
        }

        Collections.sort(adj);
        for(Edge e : adj) {
            int set1 = find(e.start, parent);
            int set2 = find(e.end, parent);

            if (set1 != set2) {
                mst.add(e);
                union(parent, set1, set2);
                total += e.wt;
            }
        }

        System.out.println("Total weight = " + total);

        for(Edge e : mst){
            System.out.println(e.start + " " + e.end);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));
        System.setOut(new PrintStream("kruskal.txt"));

        int n = sc.nextInt();
        int m = sc.nextInt();
        float q = sc.nextFloat();

        kruskalMST mst = new kruskalMST(n, m);

        for(int i = 0; i < m ; i++){
            int u = sc.nextInt();
            int v = sc.nextInt();
            float d = sc.nextFloat();

            mst.addEdge(u, v, d);
        }

        System.out.println("Kruskal's Algorithm : ");
        mst.kruskal(q);
    }
}

