package com.kiwee.test;

public class JavaMagic extends ChildOne{
	
	private int endValueIS = 34;
	private static int inSideInstanceVarible = 25;
	
	static {
		System.out.println("So the initializatioon "+inSideInstanceVarible);
		new JavaMagic();
	}
   
	public JavaMagic(int a, int b) {
		inSideInstanceVarible = 35;
	}
	
	
	
	public static void main(String[] args) {
		
		JavaMagic name = new JavaMagic();
		
		ChildOne _xyz = new ChildOne(2, new StringBuilder("inside"), "", 123);
		_xyz.methodParentOne();
		ParentOne _parent = new ChildOne();
		_parent.methodParentOne();
		
		new ChildTwo().methodtwo();
		
	}

}


class ParentOne{
	
	private int one;
	private StringBuilder builder;
	
	public ParentOne(){
		
	}
	
	public ParentOne(int one, StringBuilder builder){
		this.one= one;
		this.builder = builder;
	}
	
	public void methodOne() {
		System.out.println("This will be a public method");
	}
	
	protected void methodtwo() {
		System.out.println("This will be a protected method");
	}
	
    public void methodParentOne() {
    	System.out.println("This will be a override method Parent");
	}
	
}

class ChildOne extends ParentOne{
	private String two;
	private int varible;
	
	public ChildOne() {
		// TODO Auto-generated constructor stub
	}
	
	public ChildOne(int one, StringBuilder builder, String two, int varible) {
		super(one, builder);
		this.two = two;
		this.varible = varible;
	}
	
	/*public void methodParentOne() {
		System.out.println("This will be a override method Child");
	}*/
	
    protected void protectedmethodChild() {
    	System.out.println("This will be a protected method of child");
	}
    
    /*public void methodtwo() {
    	System.out.println("This will be a overide method of child");
	}*/
	
}

class ChildTwo extends ChildOne{
	private int insde;
	
	
}
