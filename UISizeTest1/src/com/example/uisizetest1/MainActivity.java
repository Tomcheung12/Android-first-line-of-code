package com.example.uisizetest1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * ��λ�ͳߴ�
 * dp,sp
 * 2016��7��21��11:25:47
 * 
 * @author XFHY
 * dp��Ҫ���ڿؼ�
 * sp��Ҫ����ָ�����ֵĴ�С
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
