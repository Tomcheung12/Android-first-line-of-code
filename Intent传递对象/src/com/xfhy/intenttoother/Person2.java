package com.xfhy.intenttoother;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 2016年8月19日9:56:41
 * 
 * Intent传递对象的第2种传递方式,Parcelable方式
 * 
 * @author XFHY
 * 
 * Parcelable的实现原理是将一个完整的对象进行分解,而分解后的每一部分都是Intent所支持的数据类型,这样就实现了传递对象的功能了.
 * 
 * 实现方式:实现Parcelable接口,重写 describeContents()和writeToParcel()方法.
 *    1.在describeContents()中直接返回0就可以了.而在writeToParcel()方法中需要调用writeXxx()方法将Person2类中的字段一一写出,
 *    注意字符串类型就调用writeString()方法,整型数据就调用writeInt()方法.以此类推
 *    2.除此之外,还必须在Person2类中提供一个名为CREATOR的常量,这里创建了 Parcelable.Creator()接口的一个实现.并将泛型指定为Person2
 *    .接着需要重写createFromParcel()和newArray()方法.在createFromParcel()方法中我们需要去读取刚才写出的name和age字段.
 *    并创建一个Person2对象进行返回.其中name和age都是调用Parcel的readXxx()方法读取到的,注意这里读取的顺序一定要和刚才写出的顺序
 *    完全相同.而newArray()方法中,需要new出一个Person2数组,并使用方法中传入的size作为数组大小.
 * 
 */
public class Person2 implements Parcelable {

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);   //写出name
		dest.writeInt(age);       //写出age
	}

	public static final Parcelable.Creator<Person2> CREATOR = new Parcelable.Creator<Person2>(){

		@Override
		public Person2 createFromParcel(Parcel source) {
			Person2 person2 = new Person2();
			person2.name = source.readString();  //读取name
			person2.age = source.readInt();      //读取age
			return person2;
		}

		@Override
		public Person2[] newArray(int size) {
			return new Person2[size];
		}
		
	};
	
}
