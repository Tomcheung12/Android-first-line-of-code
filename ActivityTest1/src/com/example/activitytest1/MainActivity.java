package com.example.activitytest1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Intent显式和隐式
 * 
 * @author XFHY
 * 
 */
public class MainActivity extends BaseActivity {

	private Button first = null;
	private Button httpbtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActivityCollector.addActivity(MainActivity.this);
		Log.i("xfhy", "onCreate");
		first = (Button) findViewById(R.id.first);
		httpbtn = (Button) findViewById(R.id.http);
		first.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 显式
				// Intent intent = new
				// Intent(MainActivity.this,SecondActivity.class);
				// startActivity(intent);

				// 隐式 传入第二个Activity的action和category,action和category需要在配置文件中配置
				/*
				 * Intent intent = new
				 * Intent("com.example.activitytest1.ACTION_START");
				 * intent.addCategory("android.intent.category.MY_SECOND");
				 * startActivity(intent);
				 */

				// 用intent打开网页
				/*
				 * Intent intent = new Intent(Intent.ACTION_VIEW);
				 * intent.setData(Uri.parse("http://www.baidu.com"));
				 * startActivity(intent);
				 */

				// 用Intent调用系统拨号
				/*
				 * Intent intent = new Intent(Intent.ACTION_DIAL);
				 * intent.setData(Uri.parse("tel:10086"));
				 * startActivity(intent);
				 */

				// 用Intent传递数据
				/*
				 * String data = "Hello SecondActivity!"; Intent intent = new
				 * Intent(MainActivity.this,SecondActivity.class);
				 * intent.putExtra("extra_data", data); //参数:键名,数据内容
				 * startActivity(intent);
				 */

				Intent intent = new Intent(MainActivity.this,
						SecondActivity.class);
				startActivityForResult(intent, 1);// 参数:启动的活动,请求码
			}
		});
		httpbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("xfhy","onStart");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("xfhy","onResume");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("xfhy","onPause");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("xfhy","onStop");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("xfhy","onDestroy");
		ActivityCollector.removeActivity(MainActivity.this);
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("xfhy","onRestart");
	}
	
	// 用于接收调用的活动返回结果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				String edata = data.getStringExtra("data_return");
				Log.i("xfhy", edata);
			}
			break;
		default:
			break;
		}
	}
}
