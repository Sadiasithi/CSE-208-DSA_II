import java.util.Random;
import java.util.Set;

public class Report {
    public static String generateRandomWord(){
        int length = 5 + new Random().nextInt(6);
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char ch = (char) ('a' + new Random().nextInt(26));
            word.append(ch);
        }

        return word.toString();
    }


    // Helper function to check if a word is already generated
    public static boolean isWordGenerated(String word, Set<String> generatedWords){
        return generatedWords.contains(word);
    }

    public static void generateAndPrintReport(HashTable hashTable, HashTable.CollisionResolutionMethod method, int numSelectedWords) {
        int totalCollisionsHash1 = 0;
        int totalProbesHash1 = 0;
        int totalCollisionsHash2 = 0;
        int totalProbesHash2 = 0;

        for (int i = 0; i < numSelectedWords; i++) {
            String word = generateRandomWord();

            int probesHash1 = hashTable.findHash1(word);
            totalProbesHash1 += probesHash1;
            if (probesHash1 > 1)
                totalCollisionsHash1 += probesHash1 - 1;
            else
                totalCollisionsHash1 += 0;


            int probesHash2 = hashTable.findHash2(word);
            totalProbesHash2 += probesHash2;
            if (probesHash2 > 1)
                totalCollisionsHash2 += probesHash2 - 1;
            else
                totalCollisionsHash2 += 0;
        }


        double averageProbesHash1 = (double) totalProbesHash1 / numSelectedWords;
        double averageProbesHash2 = (double) totalProbesHash2 / numSelectedWords;

        System.out.println("Collision Method: " + method);
        System.out.println("Average Probes (Hash1): " + averageProbesHash1);
        System.out.println("Total Collisions (Hash1): " + totalCollisionsHash1);
        System.out.println("Average Probes (Hash2): " + averageProbesHash2);
        System.out.println("Total Collisions (Hash2): " + totalCollisionsHash2);
        System.out.println();
    }
}
