import java.io.*;
import java.util.*;

class BinomialNode{
    int key, degree;
    BinomialNode parent, child, sibling;

    public BinomialNode(int key){
        this.key = key;
        parent = child = sibling = null;
        degree = 0;
    }
}

public class BinomialHeap{
    BinomialNode head;
    public BinomialHeap(){
        head = null;
    }


    //inserting
    public void insert(int key){
        BinomialNode node = new BinomialNode(key);
        BinomialHeap heap = new BinomialHeap();
        heap.head = node;
        union(heap);
    }

    //merging two binomial heaps
    public BinomialNode merge(BinomialHeap h1, BinomialHeap h2){
        if (h1.head == null)
            return h2.head;

        else if (h2.head == null)
            return h1.head;

        else{
            BinomialNode mergedHead;
            BinomialNode h1Next = h1.head;
            BinomialNode h2Next = h2.head;

            if (h1.head.degree <= h2.head.degree){
                mergedHead = h1.head;
                h1Next = h1Next.sibling;
            }
            else{
                mergedHead = h2.head;
                h2Next = h2Next.sibling;
            }

            BinomialNode curr = mergedHead;

            while (h1Next != null && h2Next != null){
                if (h1Next.degree <= h2Next.degree){
                    curr.sibling = h1Next;
                    h1Next = h1Next.sibling;
                }
                else{
                    curr.sibling = h2Next;
                    h2Next = h2Next.sibling;
                }

                curr = curr.sibling;
            }

            if (h1Next != null)
                curr.sibling = h1Next;
            else
                curr.sibling = h2Next;

            return mergedHead;
        }
    }


    public void link(BinomialNode large, BinomialNode small){
        large.parent = small;
        large.sibling = small.child;
        small.child = large;
        small.degree++;
    }

    public void union(BinomialHeap otherHeap){
        head = merge(this, otherHeap);
        BinomialNode prev = null;
        BinomialNode curr = head;
        BinomialNode next = curr.sibling;

        while (next != null){
            if (curr.degree != next.degree || (next.sibling != null && next.sibling.degree == curr.degree)){
                prev = curr;
                curr = next;
            }
            else{
                if (curr.key <= next.key){
                    curr.sibling = next.sibling;
                    link(next, curr);
                }
                else{
                    if (prev == null){
                        head = next;
                    }
                    else{
                        prev.sibling = next;
                    }
                    link(curr, next);
                    curr = next;
                }
            }
            next = curr.sibling;
        }
    }



    //finding minimum element
    public int findMin(){
        if (head == null){
            throw new NoSuchElementException("Heap is empty");
        }

        BinomialNode minNode = head;
        BinomialNode curr = head;

        while (curr != null){
            if (curr.key < minNode.key){
                minNode = curr;
            }
            curr = curr.sibling;
        }

        return minNode.key;
    }



    //extracting minimum element
    public int extractMin(){
        if (head == null){
            throw new NoSuchElementException("Heap is empty");
        }

        BinomialNode minNode = null;
        BinomialNode prevMinNode = null;
        BinomialNode curr = head;
        BinomialNode prev = null;

        while (curr != null){
            if (minNode == null || curr.key < minNode.key){
                minNode = curr;
                prevMinNode = prev;
            }
            prev = curr;
            curr = curr.sibling;
        }

        if (prevMinNode == null)
            head = minNode.sibling;
        else
            prevMinNode.sibling = minNode.sibling;


        BinomialHeap childHeap = new BinomialHeap();
        BinomialNode child = minNode.child;

        while (child != null){
            BinomialNode next = child.sibling;
            child.sibling = null;
            childHeap.head = child;
            union(childHeap);
            child = next;
        }

        return minNode.key;
    }


    //printing
    public void PrintTree(BinomialNode binNode, PrintWriter out, int level){
        if (binNode == null)
            return;

        Queue<BinomialNode> q = new LinkedList<>();
        q.add(binNode);

        while (!q.isEmpty()){
            int nodeCount = q.size();
            out.print("Level " + level + " : ");

            while (nodeCount > 0){
                BinomialNode node = q.poll();
                out.print(node.key + " ");
                BinomialNode curr = node.child;

                while (curr != null){
                    q.add(curr);
                    curr = curr.sibling;
                }

                nodeCount--;
            }

            level++;
            out.println();
        }
    }

    public void print(PrintWriter out){
        //out.println("Printing Binomial Heap...");
        BinomialNode node = head;

       // int treeLevel = 0;
        while (node != null) {
            out.println("Binomial Tree, B" + node.degree);
            PrintTree(node, out, 0);
            node = node.sibling;
            //treeLevel++;
        }
    }
}







