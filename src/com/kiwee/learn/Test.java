package com.kiwee.learn;

public class Test {
	
	private int baseone;
	private double baseTwo;
	
    public Test(int baseone, double baseTwo) {
		this.baseone=baseone;
		this.baseTwo=baseTwo;
	}

	// static variable 
    static int a = m1(); 
  
    // static block 
    static
    { 
        System.out.println("Inside static block"); 
    } 
  
    // static method 
    static int m1() 
    { 
        System.out.println("from m1"); 
        return 20; 
    } 
  
    // static method(main !!) 
    public static void main(String[] args) 
    { 
        System.out.println("Value of a : " + a); 
        System.out.println("from main"); 
        Test name = new Test(12,45);
        
        String s = new String();
        
        B _obj = new B(12, 34);
        
        
    } 

}


class B extends A{
	
	

	public B(int one, long two) {
		super(one, two);
		// TODO Auto-generated constructor stub
	}
	
	public B(int one, long two, float three) {
		super(one, two, three);
	}
	
}

class A {
	private int one;
	private long two;
	private float three;
	
	public A() {
		
	}
	
	public A(int one, long two) {
		this.one= one;
		this.two=two;
	}
	
	public A(int one, long two, float three) {
		this.one= one;
		this.two=two;
	}
}
