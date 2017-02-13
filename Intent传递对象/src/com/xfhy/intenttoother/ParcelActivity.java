package com.xfhy.intenttoother;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 2016年8月19日9:52:36
 * 
 * 这是用Intent传递对象,用Parcelable方式
 * 
 * @author XFHY
 * 
 * 取出时调用getIntent().getParcelableExtra()方法
 * 
 */
public class ParcelActivity extends Activity {

	private TextView text2;
	private static Person2 person2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parcel);
		text2 = (TextView)findViewById(R.id.text2);
		
		person2 = (Person2)getIntent().getParcelableExtra("person2_data");
		text2.setText("读取到数据"+person2.getName()+" "+person2.getAge());
	}
	
	public static String getTempData(){
		return "读取到数据"+person2.getName()+" "+person2.getAge();
	}
	
}
