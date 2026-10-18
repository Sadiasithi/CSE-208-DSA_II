import java.util.ArrayList;
import java.util.List;

public class BellmanFord{
    int V, E;
    List<Edge>adj;
    int[] dist;
    int[] prev;

    public BellmanFord(int V, int E){
        this.V = V;
        this.E = E;
        adj = new ArrayList<>();
        prev = new int[V];
        dist = new int[V];

        for(int i = 0 ; i < V ; i++){
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
    }

    public void addEdge(int start, int end, int wt){
        Edge edge = new Edge(start, end, wt);
        adj.add(edge);
    }

    public boolean bellmanFord(int start){
        dist[start] = 0;

        for(int i = 1; i <= V - 1; i++){
            for(Edge e : adj){
                int u = e.start;
                int v = e.end;
                int w = e.wt;

                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]){
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                }
            }
        }

        //checking for negative cycle
        for(Edge e : adj){
            int u = e.start;
            int v = e.end;
            int w = e.wt;

            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]){
                return false;
            }
        }

        return true;
    }

    public List<Integer> findShortestPath(int start, int end){
        List<Integer>path = new ArrayList<>();

        if (dist[end] == Integer.MAX_VALUE) {
            return path; //no path
        }

        int curr = end;
        while (curr != -1){
            path.add(0, curr);
            curr = prev[curr];
        }

        return path;
    }

    public void printShortestPath(int start, int end){
        List<Integer>path = findShortestPath(start, end);

        if (!path.isEmpty()){
            for (int i = 0 ; i < path.size() ; i++){
                System.out.print(path.get(i));
                if (i != path.size() - 1){
                    System.out.print(" -> ");
                }
            }
            System.out.println();

            int shortestDist = dist[end];
            System.out.println(shortestDist);
        }
    }
}
