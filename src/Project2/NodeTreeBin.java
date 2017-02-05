/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Project2;

import Interfaces.IBinaryTreeNode;

/**
 *
 * @author Jeezy
 * @param <T>
 */
public class NodeTreeBin<T> implements IBinaryTreeNode<T> {
    protected T a;
    NodeTreeBin left, right;
    
    public NodeTreeBin() {
    }
    
    public NodeTreeBin(T s) {
        a = (T)s;
    }
    
    @Override
    public String toString() {
        String theString = "";
        if(a != null) theString += a.toString();
        return theString;
    }

    @Override
    public NodeTreeBin getLeftChild() {
        return this.left;
    }

    @Override
    public NodeTreeBin getRightChild() {
        return this.right;
    }

    @Override
    public NodeTreeBin insertLeftChild(IBinaryTreeNode node, T item) {
        left = (NodeTreeBin)node;
        left.a = item;
        return left;
    }

    @Override
    public NodeTreeBin insertRightChild(IBinaryTreeNode node, T item) {
        right = (NodeTreeBin)node;
        right.a = item;
        return right;
    }

    @Override
    public int compareTo(T t) {
        return a.toString().compareTo ((t.toString()));
    }
    
    public static String toPreOrderString(NodeTreeBin root) {
        String preOrder = "";
        if(root != null) {
            preOrder += root.toString() + " ";
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
            postOrder += root.toString() + " ";
        }
        return postOrder;
    }
    
    public static String toInOrderString(NodeTreeBin root) {
        String inOrder = "";
        if(root != null) {
            inOrder += toInOrderString(root.left);
            inOrder += root.toString() + " ";
            inOrder += toInOrderString(root.right);
        }
        return inOrder;
    }

    @Override
    public T getItem() {
        return this.a;
    }
    
    @Override
    public void setItem(T item) {
        this.a = item;
    }
}
