/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project3;

import JavaTest2.JavaTest;
import static JavaTest2.JavaTest.makeRandom;


/**
 *
 * @author Jeezy
 */
public class MySort {
    //insertionSort for integer array only
    public static void insertionSort(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int copyNumber = numbers[i];
            int j = i;
            while (j > 0 && copyNumber < numbers[j-1]) {
                numbers[j] = numbers[j-1];
                j--;
            }
            numbers[j] = copyNumber;
        }
    }
    
    //generic form of insertionSort for objects
    public static <T extends Comparable<T>> void insertionSort(T[] array) {
        for (int i = 0; i < array.length; i++) {
            T copyArray = array[i];
            int j = i;
            while (j > 0 && copyArray.compareTo(array[j-1]) < 0) {
                array[j] = array[j-1];
                j--;
            }
            array[j] = copyArray;
        }
    }
    
    //mergeSort for integer array only
    public int[] mergeSort(int array[]) {
        if(array.length > 1) { // if the array has more than 1 element, split it
            int elementsInA1 = array.length / 2;
            int elementsInA2 = array.length - elementsInA1;
            int arr1[] = new int[elementsInA1];
            int arr2[] = new int[elementsInA2];
            
            System.arraycopy(array, 0, arr1, 0, elementsInA1); // copy the first part of 'array' into 'arr1', causing arr1 to become full
            for(int i = elementsInA1; i < elementsInA1 + elementsInA2; i++) arr2[i - elementsInA1] = array[i]; // copy the remaining elements of 'array' into 'arr2'
                    
            // recursively call mergeSort on each of the two sub-arrays
            arr1 = mergeSort(arr1);
            arr2 = mergeSort(arr2);

            // [i] for main array, [j] for arr1, [k] for arr2
            int i = 0, j = 0, k = 0;
            while(arr1.length != j && arr2.length != k) {
                if(arr1[j] < arr2[k]) { // if the current element of arr1 is less than current element of arr2
                    array[i] = arr1[j]; // copy the current element of arr1 into the final array
                    i++; j++; //increase indexes
                } else { // the current element of arr2 is less than current element of arr1
                    array[i] = arr2[k]; // copy the current element of arr1 into the final array
                    i++; k++; //increase indexes
                }
            }
            
            // copy into the final array
            while(arr1.length != j) {
                array[i] = arr1[j];
                i++; j++;
            }
            while(arr2.length != k) {
                array[i] = arr2[k];
                i++; k++;
            }
        }
        
        return array; // return the sorted array
    }
    
    //generic form of mergeSort for objects
    public static <T extends Comparable<T>> T[] mergeSort(T[] array) {
        if(array.length > 1) { // if array has more than 1 element, split it
            int elementsInA1 = array.length / 2;
            int elementsInA2 = array.length - elementsInA1;
            T[] firstHalf = (T[])(new Comparable[elementsInA1]);
            T[] secondHalf = (T[])(new Comparable[elementsInA2]);
            
            System.arraycopy(array, 0, firstHalf, 0, elementsInA1); // copy first part of array into firstHalf
            for(int i = elementsInA1; i < elementsInA1 + elementsInA2; i++) secondHalf[i - elementsInA1] = array[i]; // copy remaining into secondHalf
                    
            // recursively call mergeSort on each of the two sub-arrays
            firstHalf = mergeSort(firstHalf);
            secondHalf = mergeSort(secondHalf);

            // [i] for array, [j] for firstHalf, [k] for secondHalf
            int i = 0, j = 0, k = 0;
            while(firstHalf.length != j && secondHalf.length != k) {
                if(firstHalf[j].compareTo(secondHalf[k]) < 0) { // if the current element of firstHalf is less than current element of secondHalf
                    array[i] = firstHalf[j]; // copy into array
                    i++; j++; //increase indexes
                } else {
                    array[i] = secondHalf[k]; // copy into array
                    i++; k++; //increase indexes
                }
            }
            
            // copy into the final array
            while(firstHalf.length != j) {
                array[i] = firstHalf[j];
                i++; j++;
            }
            while(secondHalf.length != k) {
                array[i] = secondHalf[k];
                i++; k++;
            }
        }
        
        return array; // return the sorted array
    }

    public static void main (String args []) {
        //create int array for testing
        int testIntegers[] = new int[] { 3,5,1,2,4 }; //unsorted int array
        debugObject("--Pre-sorted Integer Array--", testIntegers);
        MySort.insertionSort(testIntegers); //sort the int array
        debugObject("--Post-sorted Integer Array--", testIntegers);
        
        //test insertionSort with JavaTest
        JavaTest[] testObjects = makeRandom(5);
        debugObject("--Pre-sorted Object Array--", testObjects);
        
        //sort by SSN
        MySort.insertionSort(testObjects);
        debugObject("--Insertion Sorted Object Array (by SSN)--", testObjects);
        
        //sort by TaxReturn
        JavaTest.sortBy = JavaTest.SORTBY.TAXRETURN;
        MySort.insertionSort(testObjects);
        debugObject("--Insertion Sorted Object Array (by Tax Return)--", testObjects);
        
        //sort by Exemption
        JavaTest.sortBy = JavaTest.SORTBY.EXEMPTION;
        MySort.insertionSort(testObjects);
        debugObject("--Insertion Sorted Object Array (by Exemptions)--", testObjects);
        
        //sort by Tax Rate
        JavaTest.sortBy = JavaTest.SORTBY.TAXRATE;
        MySort.insertionSort(testObjects);
        debugObject("--Insertion Sorted Object Array (by Tax Rate)--", testObjects);
        
        //sort by Withholding
        JavaTest.sortBy = JavaTest.SORTBY.WITHHOLDING;
        MySort.insertionSort(testObjects);
        debugObject("--Insertion Sorted Object Array (by Withholding)--", testObjects);
        
        //test mergeSort with JavaTest
        JavaTest[] testObjects2 = makeRandom(5);
        debugObject("--Pre-sorted Object Array--", testObjects2);
        
        //sort by SSN
        JavaTest.sortBy = JavaTest.SORTBY.SSN;
        MySort.mergeSort(testObjects2);
        debugObject("--Merge Sorted Object Array (by SSN)--", testObjects2);
        
        //sort by TaxReturn
        JavaTest.sortBy = JavaTest.SORTBY.TAXRETURN;
        MySort.mergeSort(testObjects2);
        debugObject("--Merge Sorted Object Array (by Tax Return)--", testObjects2);
        
        //sort by Exemption
        JavaTest.sortBy = JavaTest.SORTBY.EXEMPTION;
        MySort.mergeSort(testObjects2);
        debugObject("--Merge Sorted Object Array (by Exemptions)--", testObjects2);
        
        //sort by Tax Rate
        JavaTest.sortBy = JavaTest.SORTBY.TAXRATE;
        MySort.mergeSort(testObjects2);
        debugObject("--Merge Sorted Object Array (by Tax Rate)--", testObjects2);
        
        //sort by Withholding
        JavaTest.sortBy = JavaTest.SORTBY.WITHHOLDING;
        MySort.mergeSort(testObjects2);
        debugObject("--Merge Sorted Object Array (by Withholding)--", testObjects2);
    } // end main

    //functions for output
    public static void debugObject(String header, int[] array) {
        System.out.println(header);
        for(int i = 0; i < array.length; i++) System.out.println("Index " + String.valueOf(i) + " = " + array[i]);
    }
    
    public static void debugObject(String header, JavaTest[] objects) {
        System.out.println(header);
        for(int i = 0; i < objects.length; i++) System.out.println("Index " + String.valueOf(i) + " = " + objects[i]);
    }
}
