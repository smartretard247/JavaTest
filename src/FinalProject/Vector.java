/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import Project1.StackUMUC;

/**
 *
 * @author Jeezy
 * @param <T>
 */
public class Vector<T> {
    T a;
    StackUMUC edges;
    boolean visited = false;
    
    public Vector.COLOR color = COLOR.WHITE;
    public enum COLOR {WHITE, GREY, BLACK} //for depth first searching
    
    public Vector() {
        edges = new StackUMUC();
        a = (T)(new Object());
    }
    
    public Vector(T object) {
        edges = new StackUMUC();
        a = object;
    }
    
    public void setVisited(boolean to) {
        visited = to;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public StackUMUC getEdges() {
        return this.edges;
    }
    
    public void setObject(T a) {
        this.a = a;
    }
    
    public T getObject() {
        return a;
    }
    
    public void setColor(Vector.COLOR newColor) {
        this.color = newColor;
    }
    
    public Vector.COLOR getColor() {
        return this.color;
    }
    
    @Override
    public String toString() {
        return a.toString();
    }
}
