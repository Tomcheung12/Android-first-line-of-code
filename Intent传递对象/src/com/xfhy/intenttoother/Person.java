package com.xfhy.intenttoother;

import java.io.Serializable;

/**
 * 2016��8��19��9:12:59
 * 
 * @author XFHY
 * 
 * Serializable�����л�����˼,��ʾ��һ������ת���ɿɴ洢��ɴ����״̬.
 * ���л��ķ����ܼ�,ֻ��Ҫһ����ȥʵ��Serializable�ӿھͿ�����.
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
