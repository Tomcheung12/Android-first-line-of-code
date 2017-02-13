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
 * 2016��7��29��8:48:08
 * 
 * ����:���������SQLiteOpenHelper���������ݿ�,��ʹ���˿�������ݹ���(�����ṩ��)
 * 
 * @author XFHY 
 * 
 *         �������ݿ�,�½�һ����ȥ�̳�SQLiteopenHelper,ʵ��onCreate()��onUpgrade()����,
 *         ���췽��ѡ������ٵ���Ǹ�
 * 
 *         �������в鿴���ݿ�ʱ: adb shell Ϊ�˽���database cd data cd data ls �����ʾ��adb
 *         opendir failed ,permission denied ��linuxһ������ʱ��su�س�
 *         Ȼ�����ֻ���ͬ��root�����ˣ��ǵ�֮ǰҪ�����ֻ���rootȨ��)
 * 
 *         ���������:sqlite3 BookStore.db -> select * from Book; ����������Ϊ: 1|Dan
 *         Brown|16.96|454|The Da Vinci Co 2|Dan Brown|19.95|510|The Lost Symbol
 *         �����sqlite3�������������:sqlite> select * from Category ...> s ����Ҫ����һ���ĩβ����
 *         ; ��,enter�����˳���
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
		 * context ������ name ���ݿ����� factory �����Զ����Cursor,һ�㴫��null version ���ݿ�汾
		 */
		// �������ݿ�
		dbHelper = new MyDatabaseHelper(MainActivity.this, "BookStore.db",
		 null,1);

		// �ڶ���ִ���������,���ݿ�汾��2,��ִ�е�onUpgrade()
		//dbHelper = new MyDatabaseHelper(MainActivity.this, "BookStore.db", null,2);

		create_database = (Button) findViewById(R.id.create_database);
		add_data = (Button) findViewById(R.id.add_data);
		update_data = (Button) findViewById(R.id.update_data);
		delete_data = (Button) findViewById(R.id.delete_data);
		query_data = (Button) findViewById(R.id.query_data);
		replace_data = (Button) findViewById(R.id.replace_data);

		// ������
		create_database.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dbHelper.getWritableDatabase(); // ��ȡ�ɶ�д�����ݿ�
			}
		});

		//�������
		add_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ������ݿ�ʵ��,����������
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				// ����ContentValues����insert()�����ĵ���������,������װ���ݵ�
				ContentValues values = new ContentValues();

				// id��һ������������,�Ͳ�������
				// ��ʼ��װ��һ������
				values.put("name", "The Da Vinci Code"); // ���������
				values.put("author", "Dan Brown");
				values.put("pages", 454);
				values.put("price", 16.96);
				db.insert("Book", null, values); // �����һ������

				values.clear(); // ���values�е�����

				values.put("name", "The Lost Symbol");
				values.put("author", "Dan Brown");
				values.put("pages", 510);
				values.put("price", 19.95);
				db.insert("Book", null, values); // ����ڶ�������
			}
		});

		//��������
		update_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("price", 10.99);
				
				// ����:����,ContentValues�����װ������,������һ��(ȥ����name=?,��?ռһ��ռλ��),����ͨ��
				// ���ĸ������ṩ��һ���ַ�������Ϊ�����������е�ÿ��ռλ��ָ����Ӧ������.
				// ������������Ӧsql����where����
				int count = db.update("Book", values, "name = ?",
						new String[] { "The Da Vinci Code" });
				// �����������ͼ��,������Ϊ"The DaVinci Code"���Ȿ��۸�ĳ�10.99
			}
		});
		
		//ɾ������
		delete_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				//����,where����,?
				//ɾ������500ҳ����
				db.delete("Book", "pages > ?", new String[]{"500"});
			}
		});
		
		//��ѯ����
		query_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//����query()������ڷ���һ��Cursor����,��ѯ�����������ݶ��������������ȡ��
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				//����:����,����,where����,Ϊռλ��ָ�������ֵ,group by����,��group by��Ľ����һ��Լ��,��ѯ�������ʽ
				//��ѯBook���е���������,���浽Cursor������
				Cursor cursor = db.query("book", null, null, null, null, null, null, null);
				
				//moveToFirst()���������ݵ�ָ���ƶ�����һ�е�λ��,Ȼ��ѭ����ѯÿһ�е�����
				//ͨ��Cursor��getColumnIndex()������ȡ��ĳһ���ڱ��ж�Ӧλ������
				//Ȼ���ٽ�����������뵽��Ӧ��ȡֵ������,�Ϳ��Եõ������ݿ��ж�ȡ����������
				//�����Ҫ��close()�����ر�Cursor
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
		
		//ʹ������,�滻����  Ҫôɾ�������ݺ���������ݵĲ���һ�����,Ҫô�ͻ�Ҫ����ԭ���ľ�����
		//��������Ծ��Ǳ�֤��ĳһϵ�в���Ҫôȫ�����,Ҫôһ�����������
		//����������ı�׼д��
		replace_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.beginTransaction();  //��������
				try {
					db.delete("Book", null, null);  //�����ʾɾ��Book�����ȫ������
					
					/*if(true){  //�����ǹ���������ʧ�ܵ�,Ϊ�˲���
						throw new NullPointerException();  //�ֶ��׳��쳣,������ʧ��
					}*/
					
					ContentValues values = new ContentValues();
					values.put("name", "Game of Thrones");
					values.put("author", "George Martin");
					values.put("pages", 720);
					values.put("price", 20.85);
					db.insert("Book",null,values);  //�������
					
					db.setTransactionSuccessful();   //�����ѳɹ�ִ��
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.endTransaction();   //��������
				}
			}
		});
		
		
	}
}
