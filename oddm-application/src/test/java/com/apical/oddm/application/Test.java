package com.apical.oddm.application;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {
	
	Integer i = 0;

	private String userName;
	
	public Test(String string) {
		userName = string;
	}
	void rename (String aa) {
		System.out.println(aa);
		aa ="BB";
		System.out.println(aa);
	}
	String rename2 (String aa) {
		System.out.println(aa);
		aa ="BB";
		System.out.println(aa);
		return aa;
	}
	
	void integer () {
		AtomicInteger ii = new AtomicInteger(2) ;
		integer2(ii);
		System.out.println(ii.get());
	}
	void integer2 (AtomicInteger i) {
		i.incrementAndGet();
	}
	
	public static void main(String[] args) {
		/*Test t = new Test();
		String aa = new String("AA");
		t.rename(aa);
		aa = "ddd";
		System.out.println(aa);*/
		
		/*Test t = new Test("AA");
		System.out.println(t);
		sss(t);
		System.out.println(t);*/
		/*Test t = new Test("AA");
		t.integer();*/
	/*	String ss = "销售|admin";
		String[] split = ss.split("\\|");
		System.out.println(split[0]);
*/
		
		
		String s = "'sds gdasda" + "\n\r" + "edaeafd'";
		  System.out.println("转换前："+s);

		 // s = s.replaceAll("\r|\n", "");

		  System.out.println("转换后："+s);
		
		
		
		
	}
	
	
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public static void sss (Test t) {
		t = new Test("BB");
		System.out.println(t);
	}
	@Override
	public String toString() {
		return "Test [userName=" + userName + "]";
	}
	
}
