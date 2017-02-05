/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Discussion1;

import Interfaces.IStack; //add my classes and interfaces
    
/**
 *
 * @author Jeezy
 * @param <T>
 */
public class MyStack<T> implements IStack {
    private static int DEFAULT_SIZE = 5;
    T[] a; //array of some type
    int n; //counter
    
    MyStack() {
        this(DEFAULT_SIZE);
    }
    
    @SuppressWarnings({"unchecked", "deprecated"})
    MyStack(int size) {
        a = (T[])(new Object[size]);
        n = 0;
    }

    @Override
    public boolean isFull() {
        return n == a.length;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public T peek() throws StackException {
        if(n == 0) { throw new StackException("Stack is Empty"); }
        
        return a[n-1];
    }

    private void resize() {
        if(n == a.length) {
            T[] x = a;
            a = (T[])(new Object[a.length*2]);
            System.arraycopy(x, 0, a, 0, x.length);
        }
        if(n < a.length/3) {
            T[] x = a;
            a = (T[])(new Object[a.length/2]);
            System.arraycopy(x, 0, a, 0, a.length);
        }
    }
    
    @Override
    public void push(Object item) {
        if(n == a.length) { resize(); }
        
        a[n] = (T)item;
        n++;
    }

    @Override
    public T pop() throws StackException {
        if(n == 0) { throw new StackException("Stack is Empty"); }
        
        T x = a[n-1];
        n--;
        
        if(n < a.length/3) { resize(); }
        
        return x;
    }
    
    @Override
    public void empty() {
        n = 0;
    }
    
    @Override
    public String toString() {
        String theStackString = "";
        
        for(int i = 0; i < n; i++) {
            theStackString += a[i].toString() + "\n";
        }
        
        theStackString += String.format ("Number of elements: %5d\n", n);
        return theStackString;
    }
    
    public T[] getArray() {
        return this.a;
    }
}
