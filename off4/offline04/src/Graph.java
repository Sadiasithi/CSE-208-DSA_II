import java.util.*;

public class Graph{
    int v;
    List<List<Integer>> adj;
    int[][] cf; //residual capacity
    int[] par;
    int gameCount;
    int INF = Integer.MAX_VALUE;

    public void addWeightedEdge(int u, int v, int cap){
        adj.get(u).add(v);
        adj.get(v).add(u);
        cf[u][v] = cap;
    }

    public Graph(int totalTeams, int[][] games, int currTeam, int[] wins, int[] left){
        gameCount = ((totalTeams - 1) * (totalTeams - 2)) / 2;
        v = 2 + gameCount + totalTeams;

        cf = new int[v][v];
        par = new int[v];
        adj = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        int currGame = 1;
        for (int i = 0; i < totalTeams; i++) {
            if (i == currTeam)
                continue;
            for (int j = i + 1; j < totalTeams; j++) {
                if (j == currTeam)
                    continue;

                //connecting s to this match
                addWeightedEdge(0, currGame, games[i][j]);

                //connecting this match to playing teams
                addWeightedEdge(currGame, gameCount + 1 + i, INF);
                addWeightedEdge(currGame, gameCount + 1 + j, INF);

                currGame++;
            }
        }

        if (currGame != gameCount + 1) {
            throw new IllegalArgumentException();
        }

        //adding team nodes to t
        for (int i = 0; i < totalTeams; i++) {
            if (i == currTeam) continue;
            addWeightedEdge(gameCount + 1 + i, v - 1, wins[currTeam] + left[currTeam] - wins[i]);
        }
    }

    //BFS to find augmenting path (O(V + E))
    public int findAugmentingPath(int s, int t){
        Arrays.fill(par, -1);
        Queue<int[]> q = new LinkedList<>(); //(node, flow)
        int[] nodeFlowPair = {s, INF};
        q.add(nodeFlowPair);
        par[s] = -2;

        while (!q.isEmpty()) {
            int[] front = q.poll();
            int u = front[0];
            int flow = front[1];

            for (int v : adj.get(u)) {
                if (par[v] == -1 && cf[u][v] > 0) {
                    int newFlow = Math.min(flow, cf[u][v]); //bottleneck for this path
                    par[v] = u;
                    if (v == t)
                        return newFlow;
                    q.add(new int[]{v, newFlow});
                }
            }
        }
        return 0; //there is no augmenting path to t
    }

    //edmondsKarp (O(VE^2))
    public int edmondsKarp(int s, int t){
        int flow = 0;
        int newFlow;
        while ((newFlow = findAugmentingPath(s, t)) > 0) {
            flow += newFlow;

            //backtrack the path to recalculate the flow in the gF
            int curr = t;
            while (curr != s) {
                int pre = par[curr];
                cf[pre][curr] -= newFlow;
                cf[curr][pre] += newFlow;
                curr = pre;
            }
        }
        return flow;
    }

    //min-cut
    //BFS O(V+E))

    public void FindReachableNodes(int s){
        Queue<Integer> q = new LinkedList<>();
        Arrays.fill(par, -1);
        par[s] = -2;
        q.add(s);

        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v : adj.get(u)) {
                if (par[v] == -1 && cf[u][v] > 0) { //v is reachable from s
                    par[v] = u;
                    q.add(v);
                }
            }
        }
    }

    // check if full in-flow could be given (O(VE^2))
    public boolean checkSaturation(){
        int flow = edmondsKarp(0, v - 1);
        for (int i = 1; i <= gameCount; i++) {
            if (cf[0][i] > 0) {
                return false;
            }
        }
        return true;
    }

    public void findEliminatedTeams(List<Integer> teams, int totalTeams){
        FindReachableNodes(0);
        for (int i = 0; i < totalTeams; i++) {
            if (par[gameCount + 1 + i] != -1)
                teams.add(i);
        }
    }
}