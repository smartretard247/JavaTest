/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2;

import Interfaces.IBinaryTree;
import Interfaces.IBinaryTreeNode;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeezy
 * @param <T>
 */
public class TreeBinUMUC<T> implements IBinaryTree<T> {
    private String nameOfTree;
    NodeTreeBin theRoot = new NodeTreeBin();
    
    public TreeBinUMUC() {
        nameOfTree = "Default";
        setRoot(theRoot); //initialize the root
    }
    
    public TreeBinUMUC(String str) {
        this(new Scanner(str),false);
    }
    
    public TreeBinUMUC(Scanner theScanner, boolean ignoreSpaces) {
        nameOfTree = "Default";
        read(theScanner, ignoreSpaces);
    }
    
    public final void read(Scanner theScanner, boolean ignoreSpaces) {
        if(!ignoreSpaces) {
            while(theScanner.hasNext()) {
                insertNode(theRoot, new NodeTreeBin(), (T)theScanner.next());
            }
        } else {
            while(theScanner.hasNext()) {
                insertNode(theRoot, new NodeTreeBin(), (T)theScanner.nextLine());
            }
        }
    }
    
    public void setName(String name) {
        System.out.println("Changing name of tree to: " + name);
        nameOfTree = name;
    }
    
    public String getName() {
        return nameOfTree;
    }
    
    @Override
    public NodeTreeBin getRoot() {
        return this.theRoot;
    }
    
    @Override
    public final NodeTreeBin setRoot(IBinaryTreeNode node) {
        NodeTreeBin newRoot = (NodeTreeBin)node;
        return theRoot = newRoot;
    }

    @Override
    public boolean isFull() {
        return false; //never full
    }

    @Override
    public boolean isEmpty() {
        return theRoot.a == null;
    }

    @Override
    public NodeTreeBin findElement(T item) {
        try {
            return this.findElement(theRoot, item);
        } catch (BinaryTreeException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    @Override
    public NodeTreeBin findElement(IBinaryTreeNode root, T s) throws BinaryTreeException {
        NodeTreeBin currentNode = (NodeTreeBin)root;
        NodeTreeBin toFind = new NodeTreeBin(s);
        
        if(currentNode.a == null) throw new BinaryTreeException("Node Not Found");
        if(currentNode.compareTo(toFind.a) == 0) return currentNode; //found match at root
        
        boolean leftSide = currentNode.compareTo(toFind.a) > 0; //should we look in left or right side of tree
        if(leftSide) {
            if(currentNode.left == null) throw new BinaryTreeException("Node Not Found"); //base case, left object is null
            if(currentNode.left.compareTo(toFind.a) == 0) return currentNode.left; //found match on left side
            return findElement(currentNode.left, s); //recur down the left side of tree
        } else { //search ride side of tree
            if(currentNode.right == null) throw new BinaryTreeException("Node Not Found"); //base case, right object is null
            if(currentNode.right.compareTo(toFind.a) == 0) return currentNode.right; //found match on right side
            return findElement(currentNode.right, s); //recur down the right side of tree
        }
    }
    
    @Override
    public NodeTreeBin insertNode(IBinaryTreeNode node, T item) {
        node = (NodeTreeBin)node;
        return insertNode(theRoot, node, item);
    }

    @Override
    public NodeTreeBin insertNode(IBinaryTreeNode root, IBinaryTreeNode node, T item) {
        NodeTreeBin currentNode = (NodeTreeBin)root;
        NodeTreeBin toInsert = (NodeTreeBin)node;
        toInsert.a = item;
        
        if(theRoot.a == null) { //then this should be the root
            System.out.print("Inserting Node to " + nameOfTree + "..." + toInsert.toString() + " (as root)\n"); //log the insertion
            setRoot(toInsert); 
            return toInsert;
        }
        
        boolean leftSide = toInsert.compareTo(currentNode.a) < 0; //what side to check
        if(leftSide) {
            if(currentNode.left == null) { //check for empty left side node
                System.out.print("Inserting Node to " + nameOfTree + "..." + toInsert.toString() + " (as left)\n"); //log the insertion
                return currentNode.insertLeftChild(toInsert, toInsert.a); //so insert one...
            } else return insertNode(currentNode.left, toInsert, (T)toInsert.a); //else recur down the left side
        } else {
            if(currentNode.right == null) {
                System.out.print("Inserting Node to " + nameOfTree + "..." + toInsert.toString() + " (as right)\n"); //log the insertion
                return currentNode.insertRightChild(toInsert, toInsert.a);
            } else return insertNode(currentNode.right, toInsert, (T)toInsert.a);
        }
    }
    
    @Override
    public void empty() {
        System.out.println("Tree Emptied...");
        theRoot.a = null;
        theRoot.left = null;
        theRoot.right = null;
    }
    
    public static int countNodes(NodeTreeBin root) {
        if(root.a == null) return 0; //empty tree
        int count = 1;
        if(root.left != null) count += countNodes(root.left);
        if(root.right != null) count += countNodes(root.right);
        return count;
    }
    
    public static String toPreOrderString(NodeTreeBin root) {
        String preOrder = "";
        if(root != null) {
            preOrder += root.toString() + "\n";
            preOrder += toPreOrderString(root.left);
            preOrder += toPreOrderString(root.right);
        }
        return preOrder;
    }
    
    public static String toPostOrderString(NodeTreeBin root) {
        String postOrder = "";
        if(root != null) {
            postOrder += toPostOrderString(root.left);
            postOrder += toPostOrderString(root.right);
            postOrder += root.toString() + "\n";
        }
        return postOrder;
    }
    
    public static String toInOrderString(NodeTreeBin root) {
        String inOrder = "";
        if(root != null) {
            inOrder += toInOrderString(root.left);
            inOrder += root.toString() + "\n";
            inOrder += toInOrderString(root.right);
        }
        return inOrder;
    }
    
    @Override
    public String toString() {
        String toReturn = "Name of Tree: " + nameOfTree + "\n";
        
        //in order print the tree
        toReturn += "In Order:\n";
        toReturn += TreeBinUMUC.toInOrderString(getRoot());
        toReturn += "\n";
        
        //pre order print the tree
        toReturn += "Pre Order:\n";
        toReturn += TreeBinUMUC.toPreOrderString(getRoot());
        toReturn += "\n";
        
        //post order print the tree
        toReturn += "Post Order:\n";
        toReturn += TreeBinUMUC.toPostOrderString(getRoot());
        toReturn += "\n";
        
        //count the nodes
        toReturn += "Node count: ";
        toReturn += String.format("%3d", countNodes(getRoot()));
        toReturn += "\n";
        
        return toReturn;
    }
    
    public static void main (String args []) {
        //create and run a test case
        TreeBinUMUC<Integer> test = new TreeBinUMUC(); //empty tree
        test.insertNode(new NodeTreeBin(), 7); //insert node
        test.insertNode(new NodeTreeBin(), 2); //insert node
        test.insertNode(new NodeTreeBin(), 1); //insert node
        test.setName("Test 1"); //set the name for output
        System.out.print(test);
        
        //test inserting nodes
        test.insertNode(new NodeTreeBin(), 6);
        test.insertNode(new NodeTreeBin(), 3);
        test.insertNode(new NodeTreeBin(), 8);
        test.insertNode(new NodeTreeBin(), 5);
        test.insertNode(new NodeTreeBin(), 9);
        test.insertNode(new NodeTreeBin(), 4);
        System.out.print(test); //debug again
        
        //find an existing element, then non-existing element
        NodeTreeBin foundNode;
        System.out.print("Finding an existing Node: ");
        foundNode = (NodeTreeBin)test.findElement(2); //search for an existing node
        System.out.print(foundNode);
        System.out.println();
        System.out.print("Finding a non-existing Node: ");
        foundNode = (NodeTreeBin)test.findElement(0); //0 should not exist in the tree
        System.out.print(foundNode);
        System.out.println();
        
        //next test, create using string of characters
        TreeBinUMUC<String> test2 = new TreeBinUMUC("B I N A R Y T R E E"); //use the scanner to parse the string
        test2.setName("My Binary Tree");
        System.out.print(test2);
        System.out.print("Finding all elements: ");
        //all following element should return successfully
        System.out.print(test2.findElement("B").toString());
        System.out.print(test2.findElement("I").toString());
        System.out.print(test2.findElement("N").toString());
        System.out.print(test2.findElement("A").toString());
        System.out.print(test2.findElement("R").toString());
        System.out.print(test2.findElement("Y").toString());
        System.out.print(test2.findElement("T").toString());
        System.out.print(test2.findElement("R").toString());
        System.out.print(test2.findElement("E").toString());
        System.out.print(test2.findElement("E").toString());
        System.out.println();
        
        //next test, use input from a file
        try {
            TreeBinUMUC<String> test3 = new TreeBinUMUC(new Scanner(new java.io.File("BinaryTreeData2.txt")),true);
            test3.setName("Tree From File");
            System.out.print(test3);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TreeBinUMUC.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // end main
}
