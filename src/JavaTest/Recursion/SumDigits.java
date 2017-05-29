package JavaTest.Recursion;

import java.util.Scanner;

/**
* This program adds all the digits in an integer.
*/
public class SumDigits {
  /**
   * Given a non-negative integer n, will add up all the digits of the integer.  The initial case
   * is n>=10.  Each recursion will remove the right most digit.
   * @param n
   * @return 
   */
  public static long sumDigits(long n) {
    if(n>=10) { // do we still have double digits?
      long x = n % 10; // save the rightmost digit
      return x + sumDigits(n/10); // recur without the rightmost digit
    } else {
      return n; // add the final digit to the total
    }
  }
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Integer: ");
    long n = sc.nextLong();
    long startTime = System.currentTimeMillis();
    System.out.println("The sum of the digits in " + n + " is " + SumDigits.sumDigits(n) + ".");
    long endTime = System.currentTimeMillis();
    System.out.println("Execution time: " + (endTime - startTime) + " millisecond(s).");
  }
}
