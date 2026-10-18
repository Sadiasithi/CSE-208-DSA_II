import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setOut(new PrintStream("output.txt"));
        int[] tableSizes = { 5000, 10000, 20000 };
        int numWords = 10000;
        int numSelectedWords = 1000;

        for (int size : tableSizes) {
            System.out.println("Table Size: " + size);
            System.out.println("-------------------");

            HashTable hashTable = new HashTable(size);
            Set<String> generatedWords = new HashSet<>();

            // Generate and insert unique words into the hash table
            for (int i = 0; i < numWords; i++) {
                String word;
                do {
                    word = Report.generateRandomWord();
                }
                while (Report.isWordGenerated(word, generatedWords));

                generatedWords.add(word);

                int value = i + 1;
                hashTable.insert(word, value, HashTable.CollisionResolutionMethod.SEPARATE_CHAINING);
                hashTable.insert(word, value, HashTable.CollisionResolutionMethod.DOUBLE_HASHING);
                hashTable.insert(word, value, HashTable.CollisionResolutionMethod.CUSTOM_PROBING);
                hashTable.insert(word, value, HashTable.CollisionResolutionMethod.SEPARATE_CHAINING2);
                hashTable.insert(word, value, HashTable.CollisionResolutionMethod.DOUBLE_HASHING2);
                hashTable.insert(word, value, HashTable.CollisionResolutionMethod.CUSTOM_PROBING2);
            }


            //System.out.println("Collision Method: Separate Chaining");
            Report.generateAndPrintReport(hashTable, HashTable.CollisionResolutionMethod.SEPARATE_CHAINING, numSelectedWords);

            //System.out.println("Collision Method: Double Hashing");
            Report.generateAndPrintReport(hashTable, HashTable.CollisionResolutionMethod.DOUBLE_HASHING, numSelectedWords);

            //System.out.println("Collision Method: Custom Probing");
            Report.generateAndPrintReport(hashTable, HashTable.CollisionResolutionMethod.CUSTOM_PROBING, numSelectedWords);

        }
    }

}
