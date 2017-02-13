package com.xfhy.mylog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 2016年8月19日10:30:15
 * 
 * 定制自己的日志工具
 * 
 * @author XFHY
 * 
 * Android自带的日志功能非常强大,但是也有缺点,比如在打印日志的控制方面就做得不够好.
 * 打个比方,你正在编写一个比较庞大的项目,期间为了方便调试,在代码的很多地方都打印了大量的日志.最近项目已经基本完成,但是却有一个非常让人头疼
 * 的问题,之前用于调试的那些日志,在项目正式上线之后仍然会照常打印,这样不仅会降低程序的运行效率,而且还可能将一些机密性的数据泄露出去.这时
 * 将打印日志一行一行地删除,似乎费时费力,而且有可能以后还需要.因此,最理想的情况就是可以控制日志的打印,当程序处于开发阶段日志就可以打印,当
 * 程序上线了之后就把日志屏蔽掉.
 * 
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LogUtil.d("xfhy","Haha");   //自己定义的打印日志的类,可以控制打印
    }

}
