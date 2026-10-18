import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void checkIfAlreadyEliminated(List<Integer> teams, int[] wins, int gamesNum, int totalTeams){
        int index = 0;
        for (int i = 0; i < totalTeams; i++) {
            if (wins[i] > wins[index]) {
                index = i;
            }
        }
        if (wins[index] > gamesNum) {
            teams.add(index);
        }
    }

    public static void printDetails(List<Integer> teams, int[] wins, int[][] games, List<String> names){
        int n = teams.size();

        //printing total winning games
        int won = 0;
        for (Integer team : teams) {
            won += wins[team];
        }

        System.out.print(names.get(teams.get(0)));

        for (int i = 1; i < n - 1; i++) {
            System.out.print(", " + names.get(teams.get(i)));
        }
        if (n > 1) {
            System.out.print(" and " + names.get(teams.get(n - 1)));
        }

        System.out.println(" have won a total of " + won + " games.");


        //printing how many times they play each other
        int matchNum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                matchNum += games[teams.get(i)][teams.get(j)];
            }
        }

        System.out.println("They play each other " + matchNum + " times.");


        //printing average winning games
        int num = won + matchNum;
        double avg = (double) num / n;

        System.out.println("So on average, each of the teams in this group wins " + num + "/" + n + " = " + avg + " games.");
    }

    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("input.txt"));
        System.setOut(new PrintStream("output.txt"));

        int n = sc.nextInt();
        while (sc.hasNextLine()) {
            List<String> names = new ArrayList<>();
            int[] wins = new int[n];
            int[] left = new int[n];
            int[][] games = new int[n][n];

            for (int i = 0; i < n; i++) {
                names.add(sc.next());
                wins[i] = sc.nextInt();
                int losses = sc.nextInt();
                //System.out.println(losses);
                left[i] = sc.nextInt();
                for (int j = 0; j < n; j++) {
                    games[i][j] = sc.nextInt();
                }
            }

            //System.out.println(names);
            //System.out.println(wins);
            //System.out.println(games);

            for (int i = 0; i < n; i++) {
                List<Integer> teams = new ArrayList<>();
                checkIfAlreadyEliminated(teams, wins, wins[i] + left[i], n);

                if (teams.isEmpty()) {
                    Graph graph = new Graph(n, games, i, wins, left);
                    boolean saturation = graph.checkSaturation();

                    if (!saturation) {
                        graph.findEliminatedTeams(teams, n);
                    }
                }

                if (!teams.isEmpty()) {
                    System.out.println(names.get(i) + " is eliminated.");
                    System.out.println("They can win at most " + wins[i] + " + " + left[i] + " = " + (wins[i] + left[i]) + " games.");

                    printDetails(teams, wins, games, names);
                    System.out.println();
                }
            }
        }
    }
}

