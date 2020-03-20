package reflect;

public class A {
	public void f1() {
		System.out.println("A's f1()");
	}
	
	public String f2() {
		System.out.println("A's f2()");
		return "hello f2";
	}
	
	public String bala(String msg,int qty) {
		System.out.println("A's bala()");
		return "bala:" + msg + " " + qty;
	}
	
	
}
