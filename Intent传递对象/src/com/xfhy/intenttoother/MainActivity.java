package com.xfhy.intenttoother;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 2016年8月19日8:52:02
 * 
 * 使用Intent传递对象
 *   通常有2种实现方式,Serializable和Parcelable
 * 
 * @author XFHY
 * 
 * 在以前的时候,Intent只能传递一些基本的数据类型.现在,我们来回顾一下用Intent传递普通数据
 *    Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
 *    intent.putExtra("string_data","hello");
 *    intent.putExtra("int_data",100);
 *    startActivity(intent);
 *    
 *    接下来,在SecondActivity中接收
 *     getIntent.getStringExtra("string_data");
 *     getIntent.getIntExtra("int_data",0);
 *  * 
 */
public class MainActivity extends Activity implements OnClickListener{

	private Button serializ;
	private Button parcel;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        serializ = (Button)findViewById(R.id.serializ);
        parcel = (Button)findViewById(R.id.parcel);
        
        serializ.setOnClickListener(this);
        parcel.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.serializ:
			/*------------------用Serializable接口方式来用Intent传递对象------------*/
	        Person person = new Person();
	        person.setName("张三");
	        person.setAge(10);
	        Intent intent = new Intent(MainActivity.this,SerializableAc.class);
	        intent.putExtra("person_data", person);
	        startActivity(intent);
			break;
		case R.id.parcel:
			/*------------------用Parcelable接口方式来用Intent传递对象------------*/
	        Person2 person2 = new Person2();
	        person2.setName("张三");
	        person2.setAge(10);
	        Intent intent2 = new Intent(MainActivity.this,ParcelActivity.class);
	        intent2.putExtra("person2_data", person2);
	        startActivity(intent2);
			break;
		default:
			break;
		}
	}
}
