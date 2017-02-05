/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Discussion1;

import Interfaces.IStack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeezy
 */
public class TestMyStack {
    static String[] zodiac = new String[]{ "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    
    public static void main (String args []) {
        MyStack theStack = new MyStack(12);
        
        try {
            for(String x : zodiac) { theStack.push(x); } //push all signs onto the stack
            System.out.println(theStack.toString()); //display full stack
            
            theStack.pop(); //pop the last element off the stack
            System.out.println(theStack.toString()); //display new stack, less one item
            
            for(int i = 0; i < 5; i++) { theStack.pop(); } //pop a few more off...
            System.out.println(theStack.toString()); //display new stack, less a few more...
            
            theStack.push("Libra");
            System.out.println(theStack.toString()); //display new stack, with one new item...
        } catch (IStack.StackException ex) {
            Logger.getLogger(TestMyStack.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // end main
}
