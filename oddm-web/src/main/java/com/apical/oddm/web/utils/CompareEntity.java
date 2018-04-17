package com.apical.oddm.web.utils;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月24日 下午5:23:57 
 * @version 1.0 
 */

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public class CompareEntity {

	public static void main(String[] args) {

		Entity1 e1 = new Entity1();
		e1.setName("名字");
		e1.setAge(10);
		e1.setMoney(new BigDecimal("22222"));

		Entity1 e2 = new Entity1();

		// e2.setName("名字");
		e2.setAge(11);
		e2.setMoney(new BigDecimal("33333"));

		System.out.println("比较之前");
		System.out.println("name:" + e1.getName());
		System.out.println("age:" + e1.getAge());
		System.out.println("money:" + e1.getMoney());
		System.out.println("name:" + e2.getName());
		System.out.println("age:" + e2.getAge());
		System.out.println("money:" + e2.getMoney());
		System.out.println("-------------------");
		try {
			Compare.compare(e1, e2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------");
		System.out.println("比较之后");
		System.out.println("name:" + e1.getName());
		System.out.println("age:" + e1.getAge());
		System.out.println("money:" + e1.getMoney());
		System.out.println("name:" + e2.getName());
		System.out.println("age:" + e2.getAge());
		System.out.println("money:" + e2.getMoney());
	}
}

class Compare {
	public static void compare(Object src, Object target) throws Exception {
		Class<?> srcClass = src.getClass();
		Class<?> targetClass = target.getClass();
		Field[] e1Field = srcClass.getDeclaredFields();
		Field[] e2Field = targetClass.getDeclaredFields();
		for (int i = 0; i < e1Field.length; i++) {
			Field e1F = e1Field[i];
			Object o1 = srcClass.getMethod(getMethodName(e1F.getName())).invoke(src);
			for (int j = 0; j < e2Field.length; j++) {
				Field e2F = e2Field[j];
				if (e1F.getName().equals(e2F.getName())) {
					Class<?> type1 = e1F.getType();
					Class<?> type2 = e2F.getType();
					if (!type1.equals(type2)) {
						throw new RuntimeException("两个类型不一致");
					}
					Object o2 = targetClass.getMethod(getMethodName(e2F.getName())).invoke(target);
					if (o1 == null && o2 != null) {
						srcClass.getMethod(setMethodName(e1F.getName()), type1).invoke(src, o2);
					}
					if (o1 != null && o2 == null) {
						targetClass.getMethod(setMethodName(e2F.getName()), type2).invoke(target, o1);
					}
					if (o1 != null && o2 != null & !o1.equals(o2)) {
						System.out.println(o1 + " 不等于 " + o2);
					}
				}
			}
		}
	}

	private static String getMethodName(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
	}

	private static String setMethodName(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
	}
}

class Entity1 {

	private String name;

	private int age;

	private BigDecimal money;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
class Entity2 {

	private String ooo;

	private int xxx;

	private Date data;

	public String getOoo() {
		return ooo;
	}

	public void setOoo(String ooo) {
		this.ooo = ooo;
	}

	public int getXxx() {
		return xxx;
	}

	public void setXxx(int xxx) {
		this.xxx = xxx;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}