package com.example.servicebestpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 2016年8月7日10:57:45
 * 
 * 服务的最佳实践-后台执行的定时任务
 * 
 * @author XFHY
 * 
 * 每隔10秒就启动一次广播,广播立刻又启动这个服务    保证LongRunningService每10秒就会启动一次
 * 
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Intent intent = new Intent(this,LongRunningService.class);
        startService(intent);
    }
}
