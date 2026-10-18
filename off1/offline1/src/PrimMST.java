import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrimMST {
    List<List<Edge>>adj;
    //int V = adj.size();
    int rootNode;

    public PrimMST(int V) {
        adj = new ArrayList<>(V);

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, float wt) {
        adj.get(u).add(new Edge(u, v, wt));
        //undirected graph
        adj.get(v).add(new Edge(v, u, wt));
    }

    public void prim(float q) {
        int V = adj.size();
        boolean[] visited = new boolean[V];

        int[] parent = new int[V]; //keeping track of the parent of each vertex
        float[] key = new float[V];

        for (int i = 0 ; i < V ; i++) {
            key[i] = Float.MAX_VALUE;
        }

        PriorityQueue<Node>pq = new PriorityQueue<>();
        int start = 0;
        pq.add(new Node(start, 0));
        key[start] = 0;

        while (!pq.isEmpty()) {
            Node min = pq.poll();
            int u = min.ver;
            visited[u] = true;

            for (Edge e : adj.get(u)) {
                int v = e.end;
                float wt = e.wt;

                if (visited[v] == false && key[v] > wt) {
                    pq.remove(new Node(v, key[v]));
                    key[v] = wt;
                    parent[v] = u;
                    pq.add(new Node(v, key[v]));
                }
            }
        }

        rootNode = parent[0];
        System.out.println("Root Node = " + rootNode);

        printMST(parent, V, q);
    }

    public void printMST(int[] parent, int V, float q) {
        float total = 0;
        int u = 0, v = 0;

        for (int i = 1; i < V; i++) {
            u = parent[i];
            v = i;
            float wt = getWeight(u, v);
            //printing the edges of the mst
            System.out.println(u + "  " + v);
            if(wt > q) {
                wt = q;
            }
            total += wt;
        }

        System.out.println("Total Weight = " + total);
    }

    public float getWeight(int u, int v) {
        for (Edge e : adj.get(u)) {
            if (e.end == v)
                return e.wt;
        }
        throw new IllegalStateException("Edge not found");
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));
        System.setOut(new PrintStream("prim.txt"));

        int n = sc.nextInt();
        int m = sc.nextInt();
        float q =  sc.nextFloat();

        PrimMST mst = new PrimMST(n);

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            float wt = sc.nextFloat();

            mst.addEdge(u, v, wt);
        }

        System.out.println("Prim - jarnik's Algorithm:");
        mst.prim(q);
    }
}
