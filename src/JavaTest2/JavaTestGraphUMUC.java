/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTest2;

import FinalProject.GraphUMUC;
import FinalProject.Vector;
import java.util.Iterator;

/**
 *
 * @author Jeezy
 */
class JavaTestGraphUMUC<V extends Vector> extends GraphUMUC {
    @Override
    public String toString() { //override super.toString() to output only ID's, instead of entire object
        String toReturn = "";
        
        Iterator<V> it = theGraph.iterator();
        while(it.hasNext()) {
            V v = it.next();
            JavaTest jt = (JavaTest)v.getObject();
            toReturn += "Vector " + jt.uid + " is connected to : ";

            Iterator<V> edges = v.getEdges().iterator();
            if(edges.hasNext()) {
                while(edges.hasNext()) {
                    V v2 = edges.next();
                    JavaTest jt2 = (JavaTest)v2.getObject();
                    toReturn += jt2.uid;
                    if(edges.hasNext()) toReturn += ", ";
                }
            } else {
                toReturn += "No connections.";
            }

            toReturn += "\n";
        }
        
        return toReturn;
    }
}
