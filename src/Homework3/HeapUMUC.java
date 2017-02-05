/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Homework3;

import static java.lang.Integer.compare;

/**
 *
 * @author Jeezy
 */
public class HeapUMUC {
    static java.util.Random rn = new java.util.Random ();
    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    char[] theCharacters = new char[10]; //the selected random characters
    
    //for tracking the heap
    int n = 0;
    char[] a = new char[10];
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public HeapUMUC() {
        for(int i = 0; i < a.length; i++) {
            theCharacters[i] = '.'; //use period as default value
            a[i] = '.';
        }
    }
    
    public void randomizeCharacters() {
        for(int i = 0; i < theCharacters.length; i++) {
            theCharacters[i] = alphabet.charAt(rn.nextInt(alphabet.length()));
        }
    }
    
    private void resize() {
        if(n == a.length) {
            char[] x = a;
            a = new char[a.length*2];
            System.arraycopy(x, 0, a, 0, x.length);
        }
        if(n < a.length/3) {
            char[] x = a;
            a = new char[a.length/2];
            System.arraycopy(x, 0, a, 0, a.length);
        }
    }
    
    private void swap(int i, int p) { //swap x with x of parent
        char temp = a[i];
        a[i] = a[p];
        a[p] = temp;
    }
    
    public static int getParent(int i) { //get the parent index of i
        return (i-1)/2;
    }
    
    public boolean add(char x) { //add x to the heap
        if (n + 1 > a.length) resize();
        a[n++] = x; //start at index 0
        bubbleUp(n-1);
        return true;
    }
    
    void bubbleUp(int i) { //maintain the heap property
        int p = getParent(i);
        while (i > 0 && compare(a[i], a[p]) < 0) {
            swap(i,p);
            i = p;
            p = getParent(i);
        }
    }
    
    public String theCharacters() { //display the original order of random characters
        String concatenated = "";
        for(int i = 0; i < theCharacters.length; i++) {
            if(theCharacters[i] != '.') concatenated += theCharacters[i];
        }
        return concatenated;
    }
    
    @Override
    public String toString() {
        String concatenated = "";
        for(int i = 0; i < a.length; i++) {
            if(a[i] != '.') concatenated += a[i];
        }
        return concatenated;
    }
    
    public static void main (String args []) {
        HeapUMUC test = new HeapUMUC();
        test.randomizeCharacters(); //create ten random characters
        System.out.println("--Insertion of Random Letters Into a Heap Data Structure--");
        System.out.println("\tTen random letters: \t" + test.theCharacters());
        
        //insert the characters into the heap
        for(int i = 0; i < test.theCharacters.length; i++) {
            test.add(test.theCharacters[i]);
        }
        System.out.println("\tThe heap: \t\t" + test);
    }

}
