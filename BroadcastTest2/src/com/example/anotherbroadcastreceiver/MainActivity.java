package com.example.anotherbroadcastreceiver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 2016年7月26日7:45:16
 * 定义一个广播接收器,用于接收上一小节中的自定义广播
 * @author XFHY
 * 我们应用程序发出的广播,其他的应用程序是可以收到的
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AnotherBroadcastReceiver
    }
}
