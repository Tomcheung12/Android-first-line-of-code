package com.example.activitytest1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends BaseActivity {
	private Button back = null;
	private Button third = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		ActivityCollector.addActivity(SecondActivity.this);
		back = (Button)findViewById(R.id.back);
		third = (Button)findViewById(R.id.third);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("data_return", "Hello FirstActivity!");
				setResult(RESULT_OK,intent);  //设置返回数据
				finish();  //销毁当前活动
			}
		});
		third.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SecondActivity.this,Third.class);
				startActivity(i);
			}
		});

		/*Intent intent = getIntent();
		String data = intent.getStringExtra("extra_data");
		Log.d("xfhy", data);*/
	}
}
