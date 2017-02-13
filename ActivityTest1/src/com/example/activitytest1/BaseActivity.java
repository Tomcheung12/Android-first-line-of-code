package com.example.activitytest1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("xfhy",getClass().getSimpleName());  //获取当前类的名称
	}
}
