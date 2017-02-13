package com.example.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 2016年7月29日8:48:08
 * 
 * 功能:这个程序用SQLiteOpenHelper来操作数据库,还使用了跨程序数据共享(内容提供器)
 * 
 * @author XFHY 
 * 
 *         创建数据库,新建一个类去继承SQLiteopenHelper,实现onCreate()和onUpgrade()方法,
 *         构造方法选择参数少点的那个
 * 
 *         用命令行查看数据库时: adb shell 为了进入database cd data cd data ls 结果提示了adb
 *         opendir failed ,permission denied 和linux一样，这时候su回车
 *         然后在手机上同意root就行了（记得之前要开放手机的root权限)
 * 
 *         查表中数据:sqlite3 BookStore.db -> select * from Book; 此例中数据为: 1|Dan
 *         Brown|16.96|454|The Da Vinci Co 2|Dan Brown|19.95|510|The Lost Symbol
 *         如果在sqlite3命令中输入错误:sqlite> select * from Category ...> s 则需要在下一句的末尾输入
 *         ; 号,enter才能退出来
 */
public class MainActivity extends Activity {

	private Button create_database = null;
	private Button add_data = null;
	private Button update_data = null;
	private Button delete_data = null;
	private Button query_data = null;
	private Button replace_data = null;
	private MyDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * context 上下文 name 数据库名称 factory 返回自定义的Cursor,一般传入null version 数据库版本
		 */
		// 创建数据库
		dbHelper = new MyDatabaseHelper(MainActivity.this, "BookStore.db",
		 null,1);

		// 第二次执行这条语句,数据库版本是2,会执行到onUpgrade()
		//dbHelper = new MyDatabaseHelper(MainActivity.this, "BookStore.db", null,2);

		create_database = (Button) findViewById(R.id.create_database);
		add_data = (Button) findViewById(R.id.add_data);
		update_data = (Button) findViewById(R.id.update_data);
		delete_data = (Button) findViewById(R.id.delete_data);
		query_data = (Button) findViewById(R.id.query_data);
		replace_data = (Button) findViewById(R.id.replace_data);

		// 创建表
		create_database.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dbHelper.getWritableDatabase(); // 获取可读写的数据库
			}
		});

		//添加数据
		add_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 获得数据库实例,用来操作的
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				// 这是ContentValues对象insert()方法的第三个参数,用来组装数据的
				ContentValues values = new ContentValues();

				// id那一列是自增长的,就不用设置
				// 开始组装第一条数据
				values.put("name", "The Da Vinci Code"); // 达芬奇密码
				values.put("author", "Dan Brown");
				values.put("pages", 454);
				values.put("price", 16.96);
				db.insert("Book", null, values); // 插入第一条数据

				values.clear(); // 清空values中的数据

				values.put("name", "The Lost Symbol");
				values.put("author", "Dan Brown");
				values.put("pages", 510);
				values.put("price", 19.95);
				db.insert("Book", null, values); // 插入第二条数据
			}
		});

		//更新数据
		update_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("price", 10.99);
				
				// 参数:表名,ContentValues对象封装的数据,更新哪一行(去更新name=?,而?占一个占位符),可以通过
				// 第四个参数提供的一个字符串数组为第三个参数中的每个占位符指定相应的内容.
				// 第三个参数对应sql语句的where部分
				int count = db.update("Book", values, "name = ?",
						new String[] { "The Da Vinci Code" });
				// 上述代码的意图是,将名字为"The DaVinci Code"的这本书价格改成10.99
			}
		});
		
		//删除数据
		delete_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				//表名,where部分,?
				//删除大于500页的书
				db.delete("Book", "pages > ?", new String[]{"500"});
			}
		});
		
		//查询数据
		query_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//调用query()方法后悔返回一个Cursor对象,查询到的所有数据都将从这个对象中取出
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				//参数:表名,列名,where部分,为占位符指定具体的值,group by的列,对group by后的结果进一步约束,查询结果排序方式
				//查询Book表中的所有数据,并存到Cursor对象中
				Cursor cursor = db.query("book", null, null, null, null, null, null, null);
				
				//moveToFirst()方法将数据的指针移动到第一行的位置,然后循环查询每一行的数据
				//通过Cursor的getColumnIndex()方法获取到某一列在表中对应位置索引
				//然后再将这个索引传入到相应的取值方法中,就可以得到从数据库中读取到的数据了
				//最后需要用close()方法关闭Cursor
				if(cursor.moveToFirst()){
					do{
						String name = cursor.getString(cursor.getColumnIndex("name"));
						String author = cursor.getString(cursor.getColumnIndex("author"));
					    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
					    double price = cursor.getDouble(cursor.getColumnIndex("price"));
					    Log.d("xfhy","name :"+name);
					    Log.d("xfhy","author :"+author);
					    Log.d("xfhy","pages :"+pages);
					    Log.d("xfhy","price :"+price);
					    
					}while(cursor.moveToNext());
				}
				cursor.close();
			}
		});
		
		//使用事务,替换数据  要么删除旧数据和添加新数据的操作一起完成,要么就还要保留原来的旧数据
		//事务的特性就是保证让某一系列操作要么全部完成,要么一个都不会完成
		//这里是事务的标准写法
		replace_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.beginTransaction();  //开启事务
				try {
					db.delete("Book", null, null);  //这里表示删除Book表里的全部数据
					
					/*if(true){  //这里是故意让事务失败的,为了测试
						throw new NullPointerException();  //手动抛出异常,让事务失败
					}*/
					
					ContentValues values = new ContentValues();
					values.put("name", "Game of Thrones");
					values.put("author", "George Martin");
					values.put("pages", 720);
					values.put("price", 20.85);
					db.insert("Book",null,values);  //添加数据
					
					db.setTransactionSuccessful();   //事务已成功执行
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.endTransaction();   //结束事务
				}
			}
		});
		
		
	}
}
