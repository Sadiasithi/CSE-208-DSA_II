import java.util.*;

class TSP {
    static class Graph {
        int V;
        List<Edge> edges;

        static class Edge {
            int src, dest, weight;

            Edge(int src, int dest, int weight) {
                this.src = src;
                this.dest = dest;
                this.weight = weight;
            }
        }

        Graph(int V) {
            this.V = V;
            edges = new ArrayList<>();
        }

        void addEdge(int src, int dest, int weight) {
            edges.add(new Edge(src, dest, weight));
        }
    }

    public static int Exact_TSP(Graph G) {
        int n = G.V;
        int[][] dp = new int[1 << n][n];

        // Initialize dp table
        for (int mask = 0; mask < (1 << n); mask++) {
            Arrays.fill(dp[mask], -1);
        }
        dp[1][0] = 0;

        return tspDP((1 << n) - 1, n - 1, G, dp);
    }

    public static int tspDP(int mask, int u, Graph G, int[][] dp) {
        if (mask == 0) {
            return G.edges.get(u).weight;
        }

        if (dp[mask][u] != -1) {
            return dp[mask][u];
        }

        int minDist = Integer.MAX_VALUE;
        for (Graph.Edge edge : G.edges) {
            int v = edge.dest;
            if ((mask & (1 << v)) != 0) {
                int newMask = mask ^ (1 << v);
                int dist = tspDP(newMask, v, G, dp) + edge.weight;
                minDist = Math.min(minDist, dist);
            }
        }

        dp[mask][u] = minDist;
        return minDist;
    }



    public static List<Integer> Metric_Approximation_TSP(Graph G) {
        // Create a Minimum Spanning Tree (MST) using Prim's algorithm
        List<Integer> mst = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Graph.Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        visited.add(0); // Start from vertex 0

        while (visited.size() < G.V) {
            Graph.Edge minEdge = null;
            for (Graph.Edge edge : G.edges) {
                if (visited.contains(edge.src) && !visited.contains(edge.dest)) {
                    if (minEdge == null || edge.weight < minEdge.weight) {
                        minEdge = edge;
                    }
                }
            }

            if (minEdge == null) {
                // The graph is not connected; handle this case as needed
                // You can break out of the loop or return an appropriate value
                return new ArrayList<>();
            }

            visited.add(minEdge.dest);
            mst.add(minEdge.src);
            mst.add(minEdge.dest);
        }

        // Convert MST to a tour by traversing it
        List<Integer> tour = new ArrayList<>();
        tour.addAll(mst);

        return tour;
    }


    public static Graph Create_Random_Graph(int V) {
        Graph graph = new Graph(V);
        Random rand = new Random();

        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                int weight = rand.nextInt(151) + 50; // Random weight between 50 and 200
                graph.addEdge(i, j, weight);
                graph.addEdge(j, i, weight); // Ensure symmetry
            }
        }

        return graph;
    }

    public static int Calculate_Tour_Length(List<Integer> tour, Graph G) {
        int tourLength = 0;
        int n = tour.size();

        for (int i = 0; i < n - 1; i++) {
            int u = tour.get(i);
            int v = tour.get(i + 1);

            // Find the edge weight between vertices u and v
            for (Graph.Edge edge : G.edges) {
                if ((edge.src == u && edge.dest == v) || (edge.src == v && edge.dest == u)) {
                    tourLength += edge.weight;
                    break;
                }
            }
        }

        return tourLength;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int X = scanner.nextInt(); // Number of test cases
        int V = 20; // Number of vertices, as specified

        for (int x = 1; x <= X; x++) {
            Graph graph = Create_Random_Graph(V);

            // Find the tour using the 2-approximation algorithm
            List<Integer> approximationTour = Metric_Approximation_TSP(graph);

            // Calculate the length of the tour
            int approximationTourLength = Calculate_Tour_Length(approximationTour, graph);

            //int exacttsp = Exact_TSP(graph);

            // Print the result
            System.out.println("(" + x + ", " + approximationTourLength + ")");
            //System.out.println("(" + x + ", " + exacttsp + ")");
        }
    }
}
