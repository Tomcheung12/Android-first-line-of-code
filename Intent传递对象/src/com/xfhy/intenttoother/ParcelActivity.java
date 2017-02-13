package com.xfhy.intenttoother;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 2016��8��19��9:52:36
 * 
 * ������Intent���ݶ���,��Parcelable��ʽ
 * 
 * @author XFHY
 * 
 * ȡ��ʱ����getIntent().getParcelableExtra()����
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
		text2.setText("��ȡ������"+person2.getName()+" "+person2.getAge());
	}
	
	public static String getTempData(){
		return "��ȡ������"+person2.getName()+" "+person2.getAge();
	}
	
}
