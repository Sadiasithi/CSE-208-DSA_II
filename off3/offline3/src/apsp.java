public class apsp{
    public static int[][] matrixMultiplication(int[][] adjMatrix){
        int n = adjMatrix.length;
        int[][] dist = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                dist[i][j] = adjMatrix[i][j];
            }
        }

        //matrix multiplication
        for(int m = 1; ; m *= 2){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    for(int k = 0; k < n; k++){
                        if(dist[j][i] != Integer.MAX_VALUE / 2 && dist[i][k] != Integer.MAX_VALUE / 2){
                            dist[j][k] = Math.min(dist[j][k], dist[j][i] + dist[i][k]);
                        }
                    }
                }
            }
            if(m >= n) break;
        }
        return dist;
    }


    public static int[][] floydWarshall(int[][] adjMatrix){
        int n = adjMatrix.length;
        int[][] dist = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                dist[i][j] = adjMatrix[i][j];
            }
        }

        //floyd warshall
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < n; k++){
                    if((dist[j][i] != Integer.MAX_VALUE / 2) && (dist[i][k] != Integer.MAX_VALUE / 2)){
                        dist[j][k] = Math.min(dist[j][k], (dist[j][i] + dist[i][k]));
                    }
                }
            }
        }
        return dist;
    }


    public static void printDistance(int[][] dist) {
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
                if (dist[i][j] != Integer.MAX_VALUE / 2) {
                    System.out.print(dist[i][j] + " ");
                }
                else
                    System.out.print("INF ");
            }
            System.out.println();
        }
    }
}
