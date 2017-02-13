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
 * 2016��7��28��8:29:47 
 * ����SharePreferences�洢����
 * 
 * @author XFHY
 * �õ�SharedPreferences�������,�Ϳ��Կ�ʼ��SharedPreferences�ļ��д洢������,��Ҫ��Ϊ����:
 * 1.����SharedPreferences�����edit()��������ȡһ��SharedPreferences.Editor����
 * 2.��SharedPreferences.Editor�������������,�������һ���������͵ľ���putBoolean()����,���һ��
 * �ַ�����ʹ��putString()����,�Դ�����
 * 3.����commit()��������ӵ������ύ,�Ӷ�������ݴ洢����
 * 
 * SharedPreferences�ļ���ʹ��XML��ʽ�������ݽ��й����
 * 
 * �õ�SharedPreferences����,ͨ���ö����getXXX()�������data.xml�ļ���id�������ֵ,
 * �ڶ���������Ĭ��ֵ,��������ļ�ֵ�Ҳ���ʱ,����ʲô����Ĭ��ֵ����
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
		
		//�洢����
		saveData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//���SharedPreferences.Editor����,�ļ�����data,���Լ�Ӧ�ó���ɶ�д
				SharedPreferences.Editor editor = getSharedPreferences("data",
						MODE_PRIVATE).edit();
				editor.putString("id", "2014110101");  //�������
				editor.putString("name", "Сǿ");
				editor.putInt("age", 20);
				editor.putBoolean("isMan",true);
				editor.putFloat("score", (float) 91.6);
				editor.commit();  //�ύ����
				Toast.makeText(MainActivity.this, "�洢�ɹ�!", 0).show();  
			}
		});
		
		//��ȡ����
		restore_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�õ�SharedPreferences����
				SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
				
				//���data.xml�ļ���id�������ֵ,�ڶ���������Ĭ��ֵ,��������ļ�ֵ�Ҳ���ʱ,
				//����ʲô����Ĭ��ֵ����
				String id = pref.getString("id", "2014110100");
				String name = pref.getString("name", "����");
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
