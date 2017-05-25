/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaTest;

import FinalProject.Vector;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jeezy
 */
public class JavaTest implements Comparable <JavaTest> {
    public static SORTBY sortBy = SORTBY.SSN;
    public enum SORTBY {SSN, TAXRETURN, EXEMPTION, TAXRATE, WITHHOLDING}
    
    static java.util.Random rn = new java.util.Random ();
    static ArrayList <String> ssns = new ArrayList <>();
    static int nextUID = 1;
    
    static final int NUM_VARS = 6;
    int uid = 0;
    String ssn;
    double taxReturn = 0;
    int exemptions = 0;
    float taxRate = 0;
    float withholding = 0;
    
    static {
        try {
            java.util.Scanner scannerSSN = new java.util.Scanner (new java.io.File("ssns.txt"));
            while (scannerSSN.hasNext()) ssns.add (scannerSSN.next());
        } catch (java.io.FileNotFoundException e) { System.out.println (e); } // end try catch
    } // end static intializer

    public JavaTest () {
        uid = nextUID++;
    }
    
    public JavaTest (String st) {
        this (new Scanner (st));
    }
    
    public JavaTest (Scanner sc) {
        uid = nextUID++;
        
        ssn = sc.next();
        taxReturn = sc.nextDouble();
        exemptions = sc.nextInt();
        taxRate = sc.nextFloat();
        withholding = sc.nextFloat();
    } // end Scanner constructor
    
    @Override
    public int compareTo (JavaTest x) {
        switch (sortBy) {
            case SSN : return ssn.compareTo (x.ssn);
            case TAXRETURN : return (taxReturn > x.taxReturn)? 1 : -1;
            case EXEMPTION: return exemptions - x.exemptions;
            case TAXRATE: return (taxRate > x.taxRate)? 1 : -1;
            case WITHHOLDING: return (withholding > x.withholding)? 1 : -1;
        } // end switch
        return 0;
    } // end compareTo for Comparable interface
    
    @Override
    public String toString () {
        return String.format ("%5d %12s %10.2f %5d %7.2f %10.2f", uid, ssn, taxReturn, exemptions, taxRate, withholding);
    } // end method toString
    
    public static JavaTest [] makeRandom (int m) {
        JavaTest [] sa = new JavaTest [m];
        for (int i = 0; i < sa.length; i++) {
            sa[i] = new JavaTest ();
            sa[i].ssn = ssns.get (rn.nextInt (ssns.size()));
            sa[i].taxReturn = rn.nextDouble () * 2000.0;
            sa[i].exemptions = rn.nextInt (9);
            sa[i].taxRate = rn.nextFloat() % 0.4f; //max 40 percent
            sa[i].withholding = rn.nextFloat() * 4000.0f;
        } // end for each student to instantiate
        return sa;
    } // end method makeRanom
    
    public static boolean write(String toFile, JavaTest[] obj) {
        //deal with text file
        FileWriter theFileWriter = null;
        PrintWriter thePrintWriter = null;
    
        //try creation of filewriter so we can output member variables
        try {
            theFileWriter = new FileWriter(toFile, false); //set path and to append
            thePrintWriter = new PrintWriter(theFileWriter);
            
            //write number of objects recording
            thePrintWriter.print(obj.length);
            thePrintWriter.println();
            
            //write member variables here
            for(int i = 0; i < obj.length; i++) {
                thePrintWriter.print(obj[i].toString());
                thePrintWriter.println();
            }
            
            thePrintWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public static JavaTest[] read(String fromFile) {
        FileReader theFileReader = null;
        //try creation of filereader so we can input questions
        try {
            theFileReader = new FileReader(fromFile);
            
            int theCharacterRead = 0, slots = 0;
            String strRead = "";
            
            while((theCharacterRead = theFileReader.read()) != '\n') {
                strRead += (char)theCharacterRead;
            }
            
            //get number of elements in array
            strRead = strRead.replace("\n", "").replace("\r", "");
            slots = Integer.valueOf(strRead);
            strRead = "";
            
            JavaTest[] loadedObj = new JavaTest[slots];
            for(int i = 0; i < loadedObj.length; i++) {
                loadedObj[i] = new JavaTest();
            }
            
            for(int i = 0, var = 0; i < slots; i++) {
                for(var = 0; var < NUM_VARS; var++) {
                    while((theCharacterRead = theFileReader.read()) == ' ') {
                        //move to the next member variable
                    }
                    
                    strRead += (char)theCharacterRead; //get first character
                    while((theCharacterRead = theFileReader.read()) != ' ') { //add characters until a space
                        if(theCharacterRead == '\n') {
                            break;
                        }

                        strRead += (char)theCharacterRead;
                    }

                    switch(var) {
                        case 0: loadedObj[i].uid = Integer.valueOf(strRead); break;
                        case 1: loadedObj[i].ssn = strRead; break;
                        case 2: loadedObj[i].taxReturn = Double.valueOf(strRead); break;
                        case 3: loadedObj[i].exemptions = Integer.valueOf(strRead); break;
                        case 4: loadedObj[i].taxRate = Float.valueOf(strRead); break;
                        case 5: loadedObj[i].withholding = Float.valueOf(strRead); break;
                    }
                
                    //reset read string
                    strRead = "";
                }
            }
            
            theFileReader.close();
            
            //return the newly built JavaTest obj array
            return loadedObj;
        } catch (IOException ex) {
            Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
            return null; //error
        }
    }
    
    public static void main (String args []) {
        //create and run the GUI
        JavaTestGUI theApp = new JavaTestGUI();
        theApp.setVisible(true);
    } // end main
    
    public static void debugString(String toDebug) {
        System.out.println("String\tcontains:\t" + toDebug);
        System.out.println("\tlength:\t\t" + toDebug.length());
    }
} // end class JavaTest