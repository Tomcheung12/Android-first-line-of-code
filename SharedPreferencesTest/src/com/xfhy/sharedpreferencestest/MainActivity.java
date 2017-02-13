package com.xfhy.sharedpreferencestest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 2016年7月28日8:29:47 
 * 利用SharePreferences存储数据
 * 
 * @author XFHY
 * 得到SharedPreferences对象过后,就可以开始向SharedPreferences文件中存储数据了,主要分为三步:
 * 1.调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor对象
 * 2.向SharedPreferences.Editor对象中添加数据,比如添加一个布尔类型的就是putBoolean()方法,添加一个
 * 字符串则使用putString()方法,以此类推
 * 3.调用commit()方法将添加的数据提交,从而完成数据存储操作
 * 
 * SharedPreferences文件是使用XML格式来对数据进行管理的
 * 
 * 得到SharedPreferences对象,通过该对象的getXXX()方法获得data.xml文件中id这个键的值,
 * 第二个参数是默认值,即当传入的键值找不到时,会以什么样的默认值返回
 */
public class MainActivity extends Activity {

	private Button saveData;
	private Button restore_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		saveData = (Button) findViewById(R.id.save_data);
		restore_data = (Button) findViewById(R.id.restore_data);
		
		//存储数据
		saveData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//获得SharedPreferences.Editor对象,文件名是data,仅自己应用程序可读写
				SharedPreferences.Editor editor = getSharedPreferences("data",
						MODE_PRIVATE).edit();
				editor.putString("id", "2014110101");  //添加数据
				editor.putString("name", "小强");
				editor.putInt("age", 20);
				editor.putBoolean("isMan",true);
				editor.putFloat("score", (float) 91.6);
				editor.commit();  //提交数据
				Toast.makeText(MainActivity.this, "存储成功!", 0).show();  
			}
		});
		
		//读取数据
		restore_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//得到SharedPreferences对象
				SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
				
				//获得data.xml文件中id这个键的值,第二个参数是默认值,即当传入的键值找不到时,
				//会以什么样的默认值返回
				String id = pref.getString("id", "2014110100");
				String name = pref.getString("name", "张三");
				int age = pref.getInt("age", 1);
				Boolean isMan = pref.getBoolean("isMan", false);
				float score = pref.getFloat("score", (float) 60.00);
				Log.d("xfhy","id :"+id);
				Log.d("xfhy","name :"+name);
				Log.d("xfhy","age :"+age);
				Log.d("xfhy","isMan :"+isMan);
				Log.d("xfhy","score :"+score);
			}
		});
	}
}
