package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 2016��7��29��8:51:07
 * 
 * �̳�SQLiteOpenHelper,����ɹ��췽����ʵ��,ʵ��onCreate()��onUpgrade()����
 * 
 * @author XFHY
 * 
 * �������,integer������,text���ı�,real�Ǹ�����,blob�Ƕ���������
 * 
 * ��adb����ȥ�����ݿ�Ŀ¼�Ļ�,��Ҫ��platform-tools���õ�Path����������ȥ.Ȼ��cmd->adb shell
 * �����ݿ�����Ŀ¼/data/data/com.example.databasetest/databases 
 * ��ls ���Կ�����ǰ�����ݿ�BookStore.db�Ѿ���������,����sqlite3 BookStore.db
 * ������sqlite3���ߴ����ݿ�,Ȼ������.schema������ʾ��ǰ���ݿ�Ĵ��������
 * 
 * 
 * �������������ݿ�����д��:
 *   �û��ǵ�һ�ΰ�װ������,����2�ű�
 *   �û����������ݿ�,���ж�һ���ǲ���ͨ���ϰ汾�����µ�,ֻ��Ҫ����1�ű�Category����
 *   �����������ݿ�ʱ,û��break���,����Ϊ�˱�֤ÿһ�ε����ݿ��޸Ķ��ܱ�ȫ��ִ�е�.
		���������������ݿ�汾1,�Ұ�װ�����µ����,����Ҫ���������ݿ�汾3,����Ҫ��1->2,2->3�����в���
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{

	//�����ĸ�����ϸ����
	private static final String CREATE_BOOK = "create table Book(" +
			"id integer primary key autoincrement, " +
			"author text, " +
			"price real, " +
			"pages integer, " +
			"name text, " +
			"category_id integer)";     //����������ݿ�(�������ݿ�����),�����������
	//��ŷ�����,��������
	private static final String CREATE_CATEGORY = "create table Category (" +  //���ݿ��������ڶ���ʱ,�����������
			"id integer primary key autoincrement, " +
			"category_name text, " +
			"category_code integer)";
	
	
	/**
	 * @param context  ������
	 * @param name     ���ݿ�����
	 * @param factory  �����Զ����Cursor,һ�㴫��null
	 * @param version  ���ݿ�汾
	 */
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//ִ�н������                                           �û��ǵ�һ�ΰ�װ������,����2�ű�
		db.execSQL(CREATE_BOOK);
		db.execSQL(CREATE_CATEGORY);
		Log.d("xfhy","����ɹ�!");
	}

	//ֻ���ڸ������ݿ�ʱ,�Ż�ִ�е��������   �û����������ݿ�,���ж�һ���ǲ���ͨ���ϰ汾�����µ�,ֻ��Ҫ����1�ű�Category����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//�����������ݿ�ʱ,û��break���,����Ϊ�˱�֤ÿһ�ε����ݿ��޸Ķ��ܱ�ȫ��ִ�е�.
		//���������������ݿ�汾1,�Ұ�װ�����µ����,����Ҫ���������ݿ�汾3,����Ҫ��1->2,2->3�����в���
		switch (oldVersion) {
		case 1:  //���ԭ�������ݰ汾��1,������Ҫ�������ݿ�,��Ҫ����
			db.execSQL(CREATE_CATEGORY);  //����Category��
		case 2:  //���ԭ�������ݿ�汾��2,������Ҫ����Book�������,��Ҫִ������sql���
			db.execSQL("alter table Book add column category_id integer");
		default:
		}
	}

}
