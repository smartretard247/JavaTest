package javatest;

import java.util.Scanner;

/**
* This program calculates and times the calculation for
* a Fibonacci number.
*/
public class Fibonacci {
    /**
     * The recursive portion, tests for initial case of n == 0 or n == 1, then 
     * recursively adds Fibonacci of n-1 with Fibonnacci of n-2.
     * @param n
     * @return 
     */
    private static int fibonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n-1) + fibonacci(n-2);
    }
    /**
     * Calculates the Fibonacci number.  This method maintains the amount of
     * milliseconds to calculate, and displays to the user once complete.
     * @param input 
     */
    public static void calculate(int input) {
        long startTime = System.currentTimeMillis();
        System.out.println("The Fibonacci number for " + input +
        " is " + fibonacci(input));
        long endTime = System.currentTimeMillis();
        System.out.println("Total time used = " +
        (endTime - startTime) + " milliseconds");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Calculate Fibonacci for the number: ");
        Fibonacci.calculate(sc.nextInt());
    }
}