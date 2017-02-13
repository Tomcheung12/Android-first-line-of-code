package com.xfhy.intenttoother;

import java.io.Serializable;

/**
 * 2016年8月19日9:12:59
 * 
 * @author XFHY
 * 
 * Serializable是序列化的意思,表示将一个对象转换成可存储或可传输的状态.
 * 序列化的方法很简单,只需要一个类去实现Serializable接口就可以了.
 * 
 */
public class Person implements Serializable{
	private String name;
	private int age;
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
