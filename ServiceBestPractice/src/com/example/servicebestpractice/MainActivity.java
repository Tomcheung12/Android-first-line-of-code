package com.example.servicebestpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 2016��8��7��10:57:45
 * 
 * ��������ʵ��-��ִ̨�еĶ�ʱ����
 * 
 * @author XFHY
 * 
 * ÿ��10�������һ�ι㲥,�㲥�����������������    ��֤LongRunningServiceÿ10��ͻ�����һ��
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
