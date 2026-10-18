 public class Edge implements Comparable<Edge> {
    int start, end;
    float wt;

    public Edge(int start, int end, float wt) {
        this.start = start;
        this.end = end;
        this.wt = wt;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.wt, other.wt);
    }
}

 class Node implements Comparable<Node> {
     int ver;
     float wt;

     public Node(int ver, float wt) {
         this.ver = ver;
         this.wt = wt;
     }

     @Override
     public int compareTo(Node other) {
         return Float.compare(wt, other.wt);
     }
 }
