package com.xfhy.menudemo1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 菜单的创建和简单使用
 * 
 * @author XFHY
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// 菜单的创建
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 通过getMenuInflater可以得到MenuInflater对象,再调用它的inflate()方法就可以创建菜单了
		// 参数:通过哪一个资源来创建文件菜单 指定我们的菜单项将添加到哪一个Menu对象当中
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单的点击响应
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { // 取得当前传入的MenuItem的id(即用户当前点击的那个菜单项)
		case R.id.add_item:
			Toast.makeText(MainActivity.this, "你点击了Add菜单", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.remove_item:
			Toast.makeText(MainActivity.this, "你点击了Remove菜单", Toast.LENGTH_SHORT)
			.show();
			break;
		case R.id.exit_item:
			//System.exit(0);
			finish();    //销毁当前活动
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
