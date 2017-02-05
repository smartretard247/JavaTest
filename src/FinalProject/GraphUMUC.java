/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import Project1.QueueUMUC;
import Project1.StackUMUC;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeezy
 * @param <T>
 * @param <V>
 */
public class GraphUMUC<T,V extends Vector> {
    protected StackUMUC theGraph;
    private boolean outputDebugInfo = false;
    
    public GraphUMUC(String str, boolean showLog) {
        this(new Scanner(str), showLog);
    }
    
    public GraphUMUC(Scanner theScanner, boolean showLog) {
        this.outputDebugInfo = showLog;
        theGraph = new StackUMUC();
        read(theScanner);
    }
    
    public GraphUMUC() {
        //initialize the graph using a stack, for endless vectors!
        theGraph = new StackUMUC();
    }
    
    public StackUMUC getGraph() {
        return this.theGraph;
    }
    
    public V findVector(T toFind) {
        Iterator<V> it = theGraph.iterator();
        while(it.hasNext()) {
            V v = it.next();
            if(v.getObject().equals(toFind)) { //object exists
                return v;
            }
        }
        
        return null;
    }
    
    //push vector without checking for uniqueness
    public V pushVector(V vector) {
        this.theGraph.push(vector);
        return vector;
    }
    
    //ensure vector is unique before adding to graph
    public V pushUniqueVector(T toFind, V toPush) {
        toPush = this.findVector(toFind);
        if(toPush == null) {
            theGraph.push((V)(new Vector(toFind)));
            return (V)theGraph.peek();
        } else {
            return toPush;
        }
    }
    
    private void read(Scanner theScanner) {
        //process each line and add first words for new vectors
        //each additional word on the line will be added as an edge later
        System.out.println("--READING FROM FILE--");
        while(theScanner.hasNext()) {
            String toProcess = theScanner.nextLine();
            String mainObject, edgeObject;
            V mainVector = null, edgeVector = null;
            
            if(toProcess.contains(" ")) { //has edges
                mainObject = toProcess.substring(0, toProcess.indexOf(" "));
                mainVector = this.pushUniqueVector((T)mainObject, mainVector); //ensure object doesn't already exist before push, save handle to pushed vector
            } else { //no edges, push vector onto graph and continue
                mainObject = toProcess.substring(0, toProcess.length());
                this.pushUniqueVector((T)mainObject, mainVector);
                continue;
            }
            
            //remove first word, if any words remainaing store them in a stack removing one word at a time
            while(!(toProcess = toProcess.substring(toProcess.indexOf(" ")+1, toProcess.length())).equals("")) {
                if(toProcess.contains(" ")) { //has more edges
                    //moreToFollow = true;
                    edgeObject = toProcess.substring(0, toProcess.indexOf(" "));
                    edgeVector = this.pushUniqueVector((T)edgeObject, edgeVector);
                    this.addEdge(mainVector, edgeVector); //add edge between the last vector added, and the new (or found) vector
                } else { //this is last edge
                    edgeObject = toProcess.substring(0, toProcess.length());
                    edgeVector = this.pushUniqueVector((T)edgeObject, edgeVector);
                    this.addEdge(mainVector, edgeVector); //add edge between the last vector added, and the new (or found) vector
                    break;
                }
            }
        }
        System.out.println("--FINISHED--");
    }
    
    @Override
    public String toString() {
        String toReturn = "";
        
        Iterator<V> it = theGraph.iterator();
        while(it.hasNext()) {
            V v = it.next();
            toReturn += "Vector " + v + " is connected to : ";

            Iterator<V> edges = v.getEdges().iterator();
            if(edges.hasNext()) {
                while(edges.hasNext()) {
                    V v2 = edges.next();
                    toReturn += v2.getObject().toString();
                    if(edges.hasNext()) toReturn += ", ";
                }
            } else {
                toReturn += "No connections.";
            }

            toReturn += "\n";
        }
        
        return toReturn;
    }
    
    public boolean debugging() {
        return this.outputDebugInfo;
    }
    
    public void setDebugging(boolean to) {
        this.outputDebugInfo = to;
    }
    
    public void addEdge(V i, V j) {
        i.getEdges().push(j);
        if(debugging()) System.out.println("Added edge: " + i + " to " + j);
    }
    
    public void removeEdge(V i, V j) {
        Iterator<V> it = i.getEdges().iterator();
        while(it.hasNext()) {
            if(debugging()) System.out.println("Checking edges...");
            if(it.next() == j) {
                it.remove();
                if(debugging()) System.out.println("Removed edge: " + i + " to " + j);
                return;
            }
        }
    }
    
    public boolean hasEdge(V i, V j) {
        return i.getEdges().contains(j);
    }
    
    public StackUMUC outEdges(V i) {
        return i.getEdges();
    }
    
    public StackUMUC inEdges(V i) {
        StackUMUC edges = new StackUMUC();
        Iterator<V> it = theGraph.iterator();
        while(it.hasNext()) {
            V v = (V)it.next();
            if(v.getEdges().contains(i)) edges.push(v);
        }
        return edges;
    }
    
    public int bfs(V toStart, V toFind) {
        String strConnection = "", strObject = "";
        int numConnections = 0;
        QueueUMUC q = new QueueUMUC();
        q.put(toStart);
        while(!q.isEmpty()) {
            V i = (V)q.get();
            i.setVisited(true); //then visit the node
            
            if(this.debugging()) { //only track path while debugging
                //if any edges are still unvisited, add them to the connection string
                Iterator<V> iterator = i.getEdges().iterator();
                while(iterator.hasNext()) {
                    V v = iterator.next();
                    if(!v.getVisited()) {
                        strObject = i.getObject().toString() + " -> ";
                        if(numConnections < 1) strConnection += strObject;
                        break;
                    }
                }
            }
            
            if(i.getObject() == toFind.getObject()) { //check for match
                if(numConnections < 1 && debugging()) {
                    strConnection += i.getObject().toString();
                    strConnection = strConnection.substring(0, strConnection.length()) + "\n"; //remove trailing arrow
                }
                numConnections++; //add to solutions
            }
            
            Iterator<V> it = outEdges(i).iterator();
            while(it.hasNext()) {
                V  j = it.next();
                if (!j.getVisited()) {
                    if(debugging()) System.out.println("\tPushing connection to " + j);
                    q.put(j);
                    j.setVisited(true);
                }
            }
        }
        
        if(debugging()) System.out.print(strConnection);
        resetVisited(); //return all vectors to not visited
        return numConnections;
    }
    
    private void resetVisited() {
        Iterator<V> it = theGraph.iterator();
        while(it.hasNext()) {
            V v = it.next();
            v.setVisited(false);
        }
    }
    
    public int dfs(V toStart, V toFind) {
        String strConnection = "", strObject = "";
        int numConnections = 0;
        StackUMUC s = new StackUMUC();
        s.push(toStart);
        while(!s.isEmpty()) {
            V i = (V)s.pop();
            
            if(this.debugging()) { //only track path while debugging
                //if node has not been visited yet, add it to the connection string
                if((i.getColor() == Vector.COLOR.WHITE)&&(i.getObject() != toFind.getObject())) {
                    strObject = i.getObject().toString() + " -> ";
                    if(numConnections < 1) strConnection += strObject; //only track first connection
                }
            }
            
            if(i.getObject() == toFind.getObject()) { //check for match
                //only track first connection
                if(numConnections < 1 && debugging()) {
                    strConnection += i.getObject().toString();
                    strConnection = strConnection.substring(0, strConnection.length()) + "\n"; //remove trailing arrow
                } 
                numConnections++; //add to solutions
            }
            
            if (i.getColor() == Vector.COLOR.WHITE) {
                i.setColor(Vector.COLOR.GREY);
                
                Iterator<V> it = outEdges(i).iterator();
                while(it.hasNext()) {
                    V  j = it.next();
                    if(debugging()) System.out.println("\tPushing connection to " + j);
                    s.push(j);
                }
            }
        }
        
        if(debugging()) System.out.print(strConnection);
        //return all vectors to white color
        resetColor();
        return numConnections;
    }
    
    private void resetColor() {
        Iterator<V> it = theGraph.iterator();
        while(it.hasNext()) {
            V v = it.next();
            v.setColor(Vector.COLOR.WHITE);
        }
    }
    
    public static void main (String args []) {
        GraphUMUC testGraph = new GraphUMUC();
        testGraph.setDebugging(true); //enable debugging output
        
        //give vectors some kind of object
        testGraph.theGraph.push(new Vector("Hello"));
        testGraph.theGraph.push(new Vector("See"));
        testGraph.theGraph.push(new Vector("It"));
        testGraph.theGraph.push(new Vector("Is"));
        testGraph.theGraph.push(new Vector("Very"));
        testGraph.theGraph.push(new Vector("Nice"));
        testGraph.theGraph.push(new Vector("To"));
        testGraph.theGraph.push(new Vector("World"));
        testGraph.theGraph.push(new Vector("You"));
        testGraph.theGraph.push(new Vector("Again"));
        
        //add edges for testing
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[2], (Vector)testGraph.theGraph.getArray()[5]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[1], (Vector)testGraph.theGraph.getArray()[5]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[1], (Vector)testGraph.theGraph.getArray()[4]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[2], (Vector)testGraph.theGraph.getArray()[3]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[4], (Vector)testGraph.theGraph.getArray()[1]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[9]);
        
        //try to remove both existing and non-existing edges
        testGraph.removeEdge((Vector)testGraph.theGraph.getArray()[2], (Vector)testGraph.theGraph.getArray()[5]); //edge exists
        testGraph.removeEdge((Vector)testGraph.theGraph.getArray()[4], (Vector)testGraph.theGraph.getArray()[5]); //edge does not exist
        testGraph.removeEdge((Vector)testGraph.theGraph.getArray()[2], (Vector)testGraph.theGraph.getArray()[3]); //edge does not exist
        testGraph.removeEdge((Vector)testGraph.theGraph.getArray()[4], (Vector)testGraph.theGraph.getArray()[1]); //edge exists
        
        //test outedges
        System.out.print("\nOut edges (1):\n");
        System.out.print(testGraph.outEdges((Vector)testGraph.theGraph.getArray()[1]));
        
        //test inedges
        System.out.print("\nIn edges (4):\n");
        System.out.print(testGraph.inEdges((Vector)testGraph.theGraph.getArray()[4]));
        
        //test bfs, should not find any connection
        testBFS(testGraph, (Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[7]);
        
        //add egdes to make connection and try again
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[9], (Vector)testGraph.theGraph.getArray()[6]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[6], (Vector)testGraph.theGraph.getArray()[4]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[4], (Vector)testGraph.theGraph.getArray()[7]);
        testBFS(testGraph, (Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[7]); //should find connection, 0->9, 9->6, 6->4, 4->7
        
        //remove necessary edge to destination and run search again
        testGraph.removeEdge((Vector)testGraph.theGraph.getArray()[6], (Vector)testGraph.theGraph.getArray()[4]);
        testBFS(testGraph, (Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[7]);
        
        //add a secondary edge to the destination to see if BFS finds it
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[6], (Vector)testGraph.theGraph.getArray()[1]);
        testBFS(testGraph, (Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[7]); //should find connection, 0->9, 9->6, 6->1, 1->4, 4->7
        
        //test dfs, should find same connection
        testDFS(testGraph, (Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[7]);
        
        //now add a shorter depth connection to see if dfs finds it
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[8]);
        testGraph.addEdge((Vector)testGraph.theGraph.getArray()[8], (Vector)testGraph.theGraph.getArray()[7]);
        testDFS(testGraph, (Vector)testGraph.theGraph.getArray()[0], (Vector)testGraph.theGraph.getArray()[7]);
        
        //test final graph with toString()
        System.out.println("Final graph:");
        System.out.println(testGraph);
        
        //test creation of graph using an input file, all inputs have ONLY one edge
        try {
            System.out.println("Creating a graph using an input file (single edges)...");
            GraphUMUC testGraph2 = new GraphUMUC(new Scanner(new java.io.File("GraphData2.txt")), true);
            
            //display the graph that was generated
            System.out.println(testGraph2);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphUMUC.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        //test creation of graph using an input file with MULTIPLE edges
        try {
            System.out.println("Creating a graph using an input file (multiple edges)...");
            GraphUMUC testGraph3 = new GraphUMUC(new Scanner(new java.io.File("GraphData3.txt")), true);
            
            //display the graph that was generated
            System.out.println(testGraph3);
            
            //will try to find a relation between people
            Vector[] people = new Vector[3];
            people[0] = testGraph3.findVector("Ron");
            people[1] = testGraph3.findVector("Glenn");
            people[2] = testGraph3.findVector("Patrick");
            
            testBFS(testGraph3, people[0], people[1]); //find edge connecting people
            testDFS(testGraph3, people[0], people[1]); //find edge connecting people
            testBFS(testGraph3, people[2], people[0]); //find edge connecting people
            testDFS(testGraph3, people[2], people[0]); //find edge connecting people
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphUMUC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void testBFS(GraphUMUC graphToSearch, Vector start, Vector toFind) {
        System.out.print("\nTesting breadth-first search:\n");
        System.out.print("Searching for connection between " + start + " and " + toFind + "\n");
        int numPathsToDestination = graphToSearch.bfs(start, toFind); //start from vector and search for vector
        if(numPathsToDestination > 0) System.out.println("\tFound " + numPathsToDestination + " connection(s) to destination!!");
        else System.out.println("\tNo connection to destination vector exists.");
        System.out.println();
    }
    
    private static void testDFS(GraphUMUC graphToSearch, Vector start, Vector toFind) {
        System.out.print("\nTesting depth-first search:\n");
        System.out.print("Searching for connection between " + start + " and " + toFind + "\n");
        int numPathsToDestination = graphToSearch.dfs(start, toFind); //start from vector and search for vector
        if(numPathsToDestination > 0) System.out.println("\tFound " + numPathsToDestination + " connection(s) to destination!!");
        else System.out.println("\tNo connection to destination vector exists.");
        System.out.println();
    }
}
