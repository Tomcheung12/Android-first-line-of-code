package com.example.uisizetest1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 单位和尺寸
 * dp,sp
 * 2016年7月21日11:25:47
 * 
 * @author XFHY
 * dp主要用于控件
 * sp主要用于指定文字的大小
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;
        Log.i("xfhy","xdpi --> "+xdpi);
        Log.i("xfhy","ydpi --> "+ydpi);
    }
}
