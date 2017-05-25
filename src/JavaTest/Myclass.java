/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTest;

class Outer_Demo {
   int num;
   
   // inner class
   private abstract class Inner_Demo {
       int innerInt;
       
      public void print() {
          num++;
         System.out.println("This is an inner class" + num + innerInt);
      }
      
      abstract int abFunc(int a);
   }
   
   // Accessing he inner class from the method within
   void display_Inner() {
      Inner_Demo inner = new Inner_Demo() {
          @Override
          int abFunc(int a) {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }
      };
      inner.print();
   }
}
   
public class Myclass {

   public static void main(String args[]) {
      // Instantiating the outer class 
      Outer_Demo outer = new Outer_Demo();
      
      // Accessing the display_Inner() method.
      outer.display_Inner();
   }
}