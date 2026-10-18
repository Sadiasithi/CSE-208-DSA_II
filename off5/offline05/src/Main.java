import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("in.txt"));
        PrintStream output = new PrintStream("out_avl.txt");
        PrintStream report = new PrintStream("report_avl.txt");

        AVLTree avl =  new AVLTree();
        double insertTime = 0, deleteTime = 0, findTime = 0, traverseTime = 0;

        while (sc.hasNext()){
            String op = sc.next();
            int key;
            double startTime;

            switch(op){
                case "I" :
                    key = sc.nextInt();
                    startTime = System.nanoTime();
                    avl.insert(key);
                    insertTime += (System.nanoTime() - startTime) / 1000000.0;
                    avl.printAVL(avl.root, output);
                    output.println();
                    break;

                case "D" :
                    key = sc.nextInt();
                    startTime = System.nanoTime();
                    avl.delete(key);
                    deleteTime += (System.nanoTime() - startTime) / 1000000.0;
                    avl.printAVL(avl.root, output);
                    output.println();
                    break;

                case "F" :
                    key = sc.nextInt();
                    startTime = System.nanoTime();
                    boolean found = avl.find(key);
                    findTime += (System.nanoTime() - startTime) / 1000000.0;

                    if(found)
                        output.println("found");
                    else
                        output.println("not found");
                    //output.println();
                    break;

                case "T" :
                    startTime = System.nanoTime();
                    avl.inOrderTraversal(avl.root, output);
                    traverseTime += (System.nanoTime() - startTime) / 1000000.0;
                    output.println();
                    break;

                default:
                    break;
            }
        }

        report.println("operation time(ms)");
        report.println("insert " + insertTime);
        report.println("delete " + deleteTime );
        report.println("search " + findTime);
        report.println("trav " + traverseTime);

        double totalTime = insertTime + deleteTime + findTime + traverseTime;
        report.println("total " + totalTime);

    }
}