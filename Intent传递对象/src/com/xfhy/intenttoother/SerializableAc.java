package com.xfhy.intenttoother;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 2016年8月19日9:27:22
 * 这是用Intent传递对象,Serializable方式
 * @author XFHY
 *
 */
public class SerializableAc extends Activity {

	private TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serializable);
		
		text = (TextView)findViewById(R.id.text);
		//获得MainActivity传递过来的数据
		Person person = (Person)getIntent().getSerializableExtra("person_data");
		text.setText("获取到数据: \n"+person.getName()+" "+person.getAge());
	}


}
