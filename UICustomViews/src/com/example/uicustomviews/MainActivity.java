package com.example.uicustomviews;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * ���벼��,���Լ�д�Ĳ���,���뵽��һ��������ȥ
 * include 
 * �������:�ظ���д���ִ���
 * 
 * �Զ���ؼ�:���ÿ�ζ�Ҫ�ظ���дͬһ�����ֵİ�ť�ĵ���¼���(��Ҫ��ӵ������ļ�����)
 * @author XFHY
 *
 */
public class MainActivity extends Activity {

	private Button edit = null;
	private EditText edittext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main); 
        edit = (Button)findViewById(R.id.title_edit);
        edittext = (EditText)findViewById(R.id.edittext);
        edit.setOnClickListener(new OnClickListener() {   //ʹEditText���Ա༭
			
			@Override
			public void onClick(View v) {
				edittext.setFocusable(true);
				edittext.setEnabled(true);
			}
		});
    }
}
