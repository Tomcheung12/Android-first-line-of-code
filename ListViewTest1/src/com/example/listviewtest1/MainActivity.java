package com.example.listviewtest1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * ListView的简单实用,定制ListView的界面 2016年7月20日9:19:11
 * 
 * @author XFHY 此项目中数组中的数据是无法直接传递给ListView的,我们还需要借助适配器来完成.Android提供了很多适
 *         配器的实现类,好用的我觉得是ArrayAdapter,它可以通过泛型来指定要适配的数据类型.然后在构造函数中把 要适配的数据传入即可
 * 
 *         定制ListView的界面步骤: 
 *         a.新建一个类,就是每个选项的那些属性,全部放在这个类里面,
 *         b.然后新建一个自定义的布局文件,这个布局文件就是每个选项的布局
 *         c.再定义一个类将ArrayAdapter继承,再实现里面的构造方法和getView()方法,
 *         getView()是用来返回每个选项的视图的,需要在里面实现控件的绑定和设置
 *         d.再回到主类中,写一个ArrayList<T>集合,将自定义的数据类添加进去,再定义刚刚继承
 *         ArrayAdapter的类的实例,ListView设置setAdapter();
 * 
 *         提升ListView的运行效率:
 *         a.getView()方法中有一个convertView参数,这个参数用于将之前加载好的布局进行缓存,
 *         以便 之后可以进行重用.
 *         b.在ArrayAdapter的子类中写一个内部类,内部类中装的是每个选项布局中控件的id,则可以第一次
 *         加载时即可把数据存到这个内部类中,内部类的数据通过view.setTag();存到view中去,view又
 *         要被系统缓存,则在下次需要这个控件的时候直接通过view.getTag()赋值这个内部类
 */
public class MainActivity extends Activity {

	/*
	 * private String[] data =
	 * {"Apple","banana","Orange","Watermelon","Pear","Grape",
	 * "Pineaoole","Strawberry","Strawberry","Cherry","Mango"}; private ListView
	 * listview = null; ArrayAdapter<String> adapter = null;
	 */
	private ListView listview = null;
	private List<Fruit> fruitList = new ArrayList<Fruit>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * adapter = new
		 * ArrayAdapter<String>(MainActivity.this,android.R.layout.
		 * simple_expandable_list_item_1,data); listview =
		 * (ListView)findViewById(R.id.list_view); //可以通过滚动的方式查看屏幕外的数据
		 * listview.setAdapter(adapter);
		 */

		initFruits(); // 初始化水果数据
		FruitAdapter adapter = new FruitAdapter(MainActivity.this,
				R.layout.fruit_item, fruitList);
		listview = (ListView) findViewById(R.id.list_view);
		// 可以通过滚动的方式查看屏幕外的数据
		listview.setAdapter(adapter);
		
		//ListView的子项点击的监听器
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//可以通过position来判断用户点击的是那一项
				Fruit fruit = fruitList.get(position);
				Toast.makeText(MainActivity.this, fruit.getName(), 0).show();
			}
		});
	}

	private void initFruits() {
		Fruit apple = new Fruit("Apple", R.drawable.e);
		fruitList.add(apple);
		Fruit banana = new Fruit("Banana", R.drawable.e);
		fruitList.add(banana);
		Fruit orange = new Fruit("Orange", R.drawable.e);
		fruitList.add(orange);
		Fruit watermelon = new Fruit("Watermelon", R.drawable.e);
		fruitList.add(watermelon);
		Fruit pear = new Fruit("Pear", R.drawable.e);
		fruitList.add(pear);
		Fruit grape = new Fruit("Grape", R.drawable.e);
		fruitList.add(grape);
		Fruit pineaoole = new Fruit("Pineaoole", R.drawable.e);
		fruitList.add(pineaoole);
		Fruit strawberry = new Fruit("Strawberry", R.drawable.e);
		fruitList.add(strawberry);
		Fruit cherry = new Fruit("Cherry", R.drawable.e);
		fruitList.add(cherry);
		Fruit mango = new Fruit("Mango", R.drawable.e);
		fruitList.add(mango);
	}
}
