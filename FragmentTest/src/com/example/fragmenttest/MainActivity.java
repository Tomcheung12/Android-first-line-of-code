package com.example.fragmenttest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 碎片
 * 2016年7月23日9:07:54
 * 这是一个简单的碎片示例,在一个活动中添加两个碎片,并让着两个碎片评分活动空间
 * @author XFHY
 * 
 *  简单使用碎片:新建碎片布局,再新建碎片的类,新建碎片的类时需要重写Fragment的onCreateView()方法,
 *  然后在这个方法中通过LayoutInflater的inflate()方法将
	刚才定义的碎片布局动态加载进来.一个碎片布局对应一个碎片的类.
 *  然后在需要添加碎片的布局里添加碎片布局,碎片布局里需要用 android:name来添加碎片的类.
 *  
 *  动态添加碎片:
 *    1.创建待添加的碎片实例
 *    2.获取到FragmentManager,在活动中可以直接调用getFragmentManager()方法得到
 *    3.开启一个事务,通过调用beginTransaction()方法开启
 *    4.向容器中加入碎片,一般使用replace()方法实现,需要传入容器的id和待添加的碎片实例
 *    
 *    如果想摸你返回栈,则
 *    //在碎片中模拟返回栈   用来接收一个名字用于描述返回栈的状态,一般传入null即可
	  transaction.addToBackStack(null);
 *    5.提交事务,调用commit()方法来来完成
 *    
 *  实现双页模式与单页模式并存:
 *    1.新建res/layout-large文件夹,然后在里面新建一个activity_main布局文件(里面是自己写好的双页布局),
 *    本身res/layout就有一个activity_main布局文件(原本的,单页布局),  
 *    然后系统会自动的当遇到大屏幕的平板时就会加载
 *    res/layout-large里面的双页布局,遇到小屏幕则加载单页布局.
 *    
 *    这里的large是限定符,根据大小,其实还有small,normal,large,xlarge
 *    根据分辨率:ldpi,mdpi,hdpi,xhdpi
 *    方向:land,port
 */
public class MainActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			/*//创建待添加的碎片实例
			AnotherRightFragment fragment =  new AnotherRightFragment();
			
			//获取到FragmentManager,在活动中可以直接调用getFragmentManager()方法得到
			FragmentManager fragmentManager = getFragmentManager();
			
			//开启一个事务,通过调用beginTransaction()方法开启
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			
			//向容器中加入碎片,一般使用replace()方法实现,需要传入容器的id和待添加的碎片实例
			transaction.replace(R.id.right_layout, fragment);
			
			//在碎片中模拟返回栈   用来接收一个名字用于描述返回栈的状态,一般传入null即可
			transaction.addToBackStack(null);
			
			//提交事务,调用commit()方法来来完成
			transaction.commit();*/
			break;

		default:
			break;
		}
	}
}
