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
 * 2016��8��19��8:52:02
 * 
 * ʹ��Intent���ݶ���
 *   ͨ����2��ʵ�ַ�ʽ,Serializable��Parcelable
 * 
 * @author XFHY
 * 
 * ����ǰ��ʱ��,Intentֻ�ܴ���һЩ��������������.����,�������ع�һ����Intent������ͨ����
 *    Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
 *    intent.putExtra("string_data","hello");
 *    intent.putExtra("int_data",100);
 *    startActivity(intent);
 *    
 *    ������,��SecondActivity�н���
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
			/*------------------��Serializable�ӿڷ�ʽ����Intent���ݶ���------------*/
	        Person person = new Person();
	        person.setName("����");
	        person.setAge(10);
	        Intent intent = new Intent(MainActivity.this,SerializableAc.class);
	        intent.putExtra("person_data", person);
	        startActivity(intent);
			break;
		case R.id.parcel:
			/*------------------��Parcelable�ӿڷ�ʽ����Intent���ݶ���------------*/
	        Person2 person2 = new Person2();
	        person2.setName("����");
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
