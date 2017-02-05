/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Project1;

import Interfaces.IStack; //add my interfaces
import java.util.Iterator;
import java.util.NoSuchElementException;
    
/**
 *
 * @author Jeezy
 * @param <T>
 */
public class StackUMUC<T> implements IStack, Iterable<T> {
    private static int DEFAULT_SIZE = 5;
    private T[] a; //array of some type
    private int n; //counter
    
    public StackUMUC() {
        this(DEFAULT_SIZE);
    }
    
    @SuppressWarnings({"unchecked", "deprecated"})
    public StackUMUC(int size) {
        a = (T[])(new Object[size]);
        n = 0;
    }

    @Override
    public boolean isFull() {
        return n == a.length;
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
    public boolean isEmpty() {
        return n == 0;
    }
    
    @Override
    public void push(Object item) {
        if(isFull()) { resize(); }
        a[n++] = (T)item; //push item to slot n, then increase n
    }

    @Override
    public T peek() {
        //if(isEmpty()) { throw new StackException("Stack is Empty"); }
        if(isEmpty()) { return null; }
        
        return a[n-1];
    }

    @Override
    public T pop() {
        //if(isEmpty()) { throw new StackException("Stack is Empty"); }
        if(isEmpty()) { return null; }
        T x = a[--n]; //decrement number of elements, then assign to x
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
        
        theStackString += String.format ("Number of elements: %5d\tSize of array: %5d", n, a.length);
        theStackString += "\n";
        return theStackString;
    }
    
    public T[] getArray() {
        return this.a;
    }
    
    public T find(T object) {
        for(int i = 0; i < n; i++) {
            if(a[i].equals(object))
                return a[i];
        }
        return null;
    }
    
    public boolean contains(T x) {
        for(int i = 0; i < n; i++) {
            if(a[i] == x) {
                return true;
            }
        }
        return false;
    }
    
    public int getN() {
        return this.n;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator() {
            private int cursor = 0;
            
            @Override
            public boolean hasNext() {
                return this.cursor < n;
            }

            @Override
            public T next() {
                if(this.hasNext()) {
                    int current = cursor;
                    cursor++;
                    return a[current];
                }
                throw new NoSuchElementException();
            }
            
            @Override
            public void remove() {
                for(int j = cursor; j < n-1; j++)
                    a[j] = a[j+1];
                n--;
            }
        };
    }

}
