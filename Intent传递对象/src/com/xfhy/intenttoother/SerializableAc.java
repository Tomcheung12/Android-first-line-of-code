package com.xfhy.intenttoother;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 2016��8��19��9:27:22
 * ������Intent���ݶ���,Serializable��ʽ
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
		//���MainActivity���ݹ���������
		Person person = (Person)getIntent().getSerializableExtra("person_data");
		text.setText("��ȡ������: \n"+person.getName()+" "+person.getAge());
	}


}
