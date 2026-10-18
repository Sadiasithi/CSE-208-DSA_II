public class Edge {
    int start, end, wt;

    Edge(int start, int end, int wt){
        this.start = start;
        this.end = end;
        this.wt = wt;
    }
}
class Node implements Comparable<Node>{
    int ver;
    int wt;

    public Node(int ver, int wt){
        this.ver = ver;
        this.wt = wt;
    }

    @Override
    public int compareTo(Node other){
        return Integer.compare(wt, other.wt);
    }
}