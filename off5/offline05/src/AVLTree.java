import java.io.PrintStream;

class AVLNode{
    int key ,height;
    AVLNode left ,right;
    public AVLNode(int key){
        this.key = key;
        height  = 1;
    }
}

public class AVLTree{
    AVLNode root;

    public  int getHeight(AVLNode node){
        if(node == null)
            return 0;
        return node.height;
    }

    public int getBalance(AVLNode node){
        if(node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    public AVLNode rightRotate(AVLNode y){
        AVLNode x = y.left;
        AVLNode Temp = x.right;
        x.right = y;
        y.left  = Temp;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    public AVLNode leftRotate(AVLNode x){
        AVLNode y = x.right;
        AVLNode temp = y.left;
        y.left = x;
        x.right = temp;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }


    //insert
    public AVLNode insertHelp(AVLNode node, int key){
        if(node == null)
            return new AVLNode(key);

        if(key < node.key)
            node.left = insertHelp(node.left, key);
        else if(key > node.key)
            node.right = insertHelp(node.right, key);
        else
            return node;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        int bal = getBalance(node);

        //LL case
        if(bal > 1 && key < node.left.key)
            return rightRotate(node);

        //RR case
        if(bal < -1 && key > node.right.key)
            return leftRotate(node);

        //LR case
        if(bal > 1 && key > node.left.key){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL case
        if(bal < -1 && key < node.right.key){
            node.right = rightRotate(node.right);
            return  leftRotate(node);
        }

        return node;
    }

    public void insert(int key){
        root = insertHelp(root,key);
    }


    //find
    public boolean findHelp(AVLNode node, int key){
        if(node == null)
            return false;

        if(key < node.key)
            return findHelp(node.left, key);
        else if(key > node.key)
            return findHelp(node.right, key);
        else
            return true;
    }

    public boolean find(int key){
        return findHelp(root, key);
    }


    //traverse
    public void inOrderTraversal(AVLNode node, PrintStream output){
        if(node != null){
            inOrderTraversal(node.left, output);
            output.print(node.key + " ");
            inOrderTraversal(node.right, output);
        }
    }


    //delete
    public AVLNode minNode(AVLNode node){
        AVLNode curr = node;
        while (curr.left != null){
            curr = curr.left;
        }
        return curr;
    }

    public AVLNode deleteHelp(AVLNode root, int key){
        if(root == null)
            return null;

        if(key < root.key)
            root.left = deleteHelp(root.left, key);
        else if(key > root.key)
            root.right = deleteHelp(root.right, key);
        else{
            if(root.left == null || root.right == null){
                AVLNode temp;
                if(root.left == null)
                    temp = root.right;
                else
                    temp = root.left;

                root = temp;
            }

            else{
                AVLNode temp = minNode(root.right);
                root.key = temp.key;
                root.right = deleteHelp(root.right, temp.key);
            }
        }

        if(root == null)
            return null;

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        int bal = getBalance(root);

        //LL case
        if(bal > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        //RR case
        if(bal < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        //LR case
        if(bal > 1 && getBalance(root.left) < 0){
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        //RL case
        if(bal < -1 && getBalance(root.right) > 0){
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public void delete(int key){
        root = deleteHelp(root, key);
    }

    //printing
    public void printAVL(AVLNode node, PrintStream output){
        if(node != null){
            output.print(node.key + "");
            if(node.left != null || node.right != null){
                output.print("(");
                printAVL(node.left, output);
                output.print(",");
                printAVL(node.right, output);
                output.print(")");
            }
        }
    }
}
