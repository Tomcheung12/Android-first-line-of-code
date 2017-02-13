package com.xfhy.intenttoother;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 2016��8��19��9:56:41
 * 
 * Intent���ݶ���ĵ�2�ִ��ݷ�ʽ,Parcelable��ʽ
 * 
 * @author XFHY
 * 
 * Parcelable��ʵ��ԭ���ǽ�һ�������Ķ�����зֽ�,���ֽ���ÿһ���ֶ���Intent��֧�ֵ���������,������ʵ���˴��ݶ���Ĺ�����.
 * 
 * ʵ�ַ�ʽ:ʵ��Parcelable�ӿ�,��д describeContents()��writeToParcel()����.
 *    1.��describeContents()��ֱ�ӷ���0�Ϳ�����.����writeToParcel()��������Ҫ����writeXxx()������Person2���е��ֶ�һһд��,
 *    ע���ַ������;͵���writeString()����,�������ݾ͵���writeInt()����.�Դ�����
 *    2.����֮��,��������Person2�����ṩһ����ΪCREATOR�ĳ���,���ﴴ���� Parcelable.Creator()�ӿڵ�һ��ʵ��.��������ָ��ΪPerson2
 *    .������Ҫ��дcreateFromParcel()��newArray()����.��createFromParcel()������������Ҫȥ��ȡ�ղ�д����name��age�ֶ�.
 *    ������һ��Person2������з���.����name��age���ǵ���Parcel��readXxx()������ȡ����,ע�������ȡ��˳��һ��Ҫ�͸ղ�д����˳��
 *    ��ȫ��ͬ.��newArray()������,��Ҫnew��һ��Person2����,��ʹ�÷����д����size��Ϊ�����С.
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
		dest.writeString(name);   //д��name
		dest.writeInt(age);       //д��age
	}

	public static final Parcelable.Creator<Person2> CREATOR = new Parcelable.Creator<Person2>(){

		@Override
		public Person2 createFromParcel(Parcel source) {
			Person2 person2 = new Person2();
			person2.name = source.readString();  //��ȡname
			person2.age = source.readInt();      //��ȡage
			return person2;
		}

		@Override
		public Person2[] newArray(int size) {
			return new Person2[size];
		}
		
	};
	
}
