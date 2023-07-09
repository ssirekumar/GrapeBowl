package com.kiwee.test;

import java.util.ArrayList;
import java.util.List;

public class TestLearn {

	public static void main(String args[] ) throws Exception {
       int[] a = new int[] {2,5,7,7,9,3,1};
       List<Integer> duplicateList = new ArrayList<>();
       //need to find the number of duplicates in an array.
       int low = 0, high = a.length-1;
       int numberOfDuplicates = 0;
       for(int i=0; i<a.length; i++) {
    	   for (int j = i+1; j < a.length; j++) {
			if(a[i]==a[j]) {
				numberOfDuplicates = numberOfDuplicates +1;
				duplicateList.add(a[i]);
			}
		}
    	Integer[] duplicates = duplicateList.toArray(new Integer[duplicateList.size()]);
    	int[] duplicates = duplicates;
    	   
       }
       System.out.println("Number of duplicates in any array " + numberOfDuplicates);
    }
    
}


public class One{
	
	private int varibleX = 25;
	protected int varibleY = 26;
	
	protected void display() {
		System.out.println("The best package");
	} 
	
}

public class Two extends One{
	
	private int varibleX = 25;
	protected int varibleY = 26;
	
	protected void display() {
		System.out.println("The best package");
	} 
	
}
