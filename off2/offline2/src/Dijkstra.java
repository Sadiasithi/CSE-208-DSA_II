import java.util.*;

public class Dijkstra{
    int V, E;
    List<List<Node>>adj;

    public Dijkstra(int V, int E){
        this.V = V;
        this.E = E;
        adj = new ArrayList<>();

        for(int i = 0; i < V; i++){
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int start, int end, int wt){
        adj.get(start).add(new Node(end, Math.abs(wt)));
    }

    public List<Integer> findShortestPath(int start, int end){
        int[] distance = new int[V];
        int[] prev = new int[V];

        for (int i = 0; i < V; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < V; i++){
            prev[i] = -1;
        }
        distance[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node srcNode = new Node(start, 0);
        pq.add(srcNode);

        while (!pq.isEmpty()){
            Node currNode = pq.poll();
            int currVertex = currNode.ver;

            if(currVertex == end) break;
            if(currNode.wt > distance[currVertex]) continue;

            for(Node node : adj.get(currVertex)){
                int newDist = distance[currVertex] + node.wt;

                if(newDist < distance[node.ver]){
                    distance[node.ver] = newDist;
                    prev[node.ver] = currVertex;
                    pq.add(new Node(node.ver, newDist));
                }

            }
        }

        List<Integer> shortestPath = new ArrayList<>();
        //backtrack to find the shortest path
        int curr = end;

        while(curr != -1){
            shortestPath.add(curr);
            curr = prev[curr];
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }

    public void printShortestDistance(int start, int end){
        List<Integer>path = findShortestPath(start, end);
        //printing path
        if(!path.isEmpty()){
            for(int i = 0; i < path.size(); i++){
                System.out.print(path.get(i));
                if(i < path.size() - 1){
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }

        if(path.isEmpty()){
            //return -1;
            System.out.println("No Path");
        }

        //printing distance
        int shortestDist = 0;

        for(int i = 0; i < path.size() - 1; i++){
            int srcVertex = path.get(i);
            int destVertex = path.get(i + 1);

            for(Node node : adj.get(srcVertex)){
                if(node.ver == destVertex) {
                    shortestDist += node.wt;
                    break;
                }
            }
        }
        //return shortestDistance;
        System.out.println(shortestDist);
    }

}

