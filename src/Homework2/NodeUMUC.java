/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Homework2;

import Classes.DoublyLinkedListNode;

/**
 *
 * @author Jeezy
 */
public class NodeUMUC extends DoublyLinkedListNode {
    @Override
    public String toString() {
        String theString = "";
        if(a != null) theString += a.toString() + "\n";
        else theString = "Node has no object.";
        return theString;
    }
}
