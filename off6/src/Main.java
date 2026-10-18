import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class Main{
    public static void main(String[] args){
        try{
            Scanner sc = new Scanner(new File("input.txt"));
            PrintWriter out = new PrintWriter(("output.txt"));

            BinomialHeap binomialHeap = new BinomialHeap();

            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String[] tokens = line.split(" ");
                String op = tokens[0];

                switch (op){
                    case "I":
                        int key = Integer.parseInt(tokens[1]);
                        binomialHeap.insert(key);
                        break;
                    case "F":
                        int min = binomialHeap.findMin();
                        out.println("Find-Min returned " + min);
                        break;
                    case "E":
                        int extracted = binomialHeap.extractMin();
                        out.println("Extract-Min returned " + extracted);
                        break;
                    case "U":
                        BinomialHeap newHeap = new BinomialHeap();
                        for (int i = 1; i < tokens.length; i++) {
                            int keyToAdd = Integer.parseInt(tokens[i]);
                            newHeap.insert(keyToAdd);
                        }
                        binomialHeap.union(newHeap);
                        break;
                    case "P":
                        out.println("Printing Binomial Heap...");
                        binomialHeap.print(out);
                        break;
                    default:
                        break;
                }
            }
            sc.close();
            out.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
