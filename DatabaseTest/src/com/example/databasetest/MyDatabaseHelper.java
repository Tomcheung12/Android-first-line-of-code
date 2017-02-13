package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 2016年7月29日8:51:07
 * 
 * 继承SQLiteOpenHelper,并完成构造方法的实现,实现onCreate()和onUpgrade()方法
 * 
 * @author XFHY
 * 
 * 建表语句,integer是整型,text是文本,real是浮点型,blob是二进制类型
 * 
 * 用adb命令去打开数据库目录的话,需要将platform-tools配置到Path环境变量中去.然后cmd->adb shell
 * 打开数据库所在目录/data/data/com.example.databasetest/databases 
 * 再ls 可以看到当前的数据库BookStore.db已经在里面了,输入sqlite3 BookStore.db
 * 可以用sqlite3工具打开数据库,然后输入.schema可以显示当前数据库的创建的语句
 * 
 * 
 * 这里是升级数据库的最佳写法:
 *   用户是第一次安装这个软件,则建立2张表
 *   用户是升级数据库,则判断一下是不是通过老版本来更新的,只需要创建1张表Category即可
 *   这里升级数据库时,没有break语句,这是为了保证每一次的数据库修改都能被全部执行到.
		比如我现在是数据库版本1,我安装了最新的软件,就需要升级到数据库版本3,就需要做1->2,2->3的所有操作
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{

	//存放书的各种详细数据
	private static final String CREATE_BOOK = "create table Book(" +
			"id integer primary key autoincrement, " +
			"author text, " +
			"price real, " +
			"pages integer, " +
			"name text, " +
			"category_id integer)";     //第三版的数据库(假设数据库升级),加了这个属性
	//存放分类名,分类代码等
	private static final String CREATE_CATEGORY = "create table Category (" +  //数据库升级到第二本时,新增了这个表
			"id integer primary key autoincrement, " +
			"category_name text, " +
			"category_code integer)";
	
	
	/**
	 * @param context  上下文
	 * @param name     数据库名称
	 * @param factory  返回自定义的Cursor,一般传入null
	 * @param version  数据库版本
	 */
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//执行建表语句                                           用户是第一次安装这个软件,则建立2张表
		db.execSQL(CREATE_BOOK);
		db.execSQL(CREATE_CATEGORY);
		Log.d("xfhy","建表成功!");
	}

	//只有在更新数据库时,才会执行到这个方法   用户是升级数据库,则判断一下是不是通过老版本来更新的,只需要创建1张表Category即可
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//这里升级数据库时,没有break语句,这是为了保证每一次的数据库修改都能被全部执行到.
		//比如我现在是数据库版本1,我安装了最新的软件,就需要升级到数据库版本3,就需要做1->2,2->3的所有操作
		switch (oldVersion) {
		case 1:  //如果原来的数据版本是1,则现在要升级数据库,需要建表
			db.execSQL(CREATE_CATEGORY);  //创建Category表
		case 2:  //如果原来的数据库版本是2,则现在要增加Book表的属性,需要执行以下sql语句
			db.execSQL("alter table Book add column category_id integer");
		default:
		}
	}

}
