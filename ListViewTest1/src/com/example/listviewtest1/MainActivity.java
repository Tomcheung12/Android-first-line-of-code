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
 * ListView�ļ�ʵ��,����ListView�Ľ��� 2016��7��20��9:19:11
 * 
 * @author XFHY ����Ŀ�������е��������޷�ֱ�Ӵ��ݸ�ListView��,���ǻ���Ҫ���������������.Android�ṩ�˺ܶ���
 *         ������ʵ����,���õ��Ҿ�����ArrayAdapter,������ͨ��������ָ��Ҫ�������������.Ȼ���ڹ��캯���а� Ҫ��������ݴ��뼴��
 * 
 *         ����ListView�Ľ��沽��: 
 *         a.�½�һ����,����ÿ��ѡ�����Щ����,ȫ���������������,
 *         b.Ȼ���½�һ���Զ���Ĳ����ļ�,��������ļ�����ÿ��ѡ��Ĳ���
 *         c.�ٶ���һ���ཫArrayAdapter�̳�,��ʵ������Ĺ��췽����getView()����,
 *         getView()����������ÿ��ѡ�����ͼ��,��Ҫ������ʵ�ֿؼ��İ󶨺�����
 *         d.�ٻص�������,дһ��ArrayList<T>����,���Զ������������ӽ�ȥ,�ٶ���ոռ̳�
 *         ArrayAdapter�����ʵ��,ListView����setAdapter();
 * 
 *         ����ListView������Ч��:
 *         a.getView()��������һ��convertView����,����������ڽ�֮ǰ���غõĲ��ֽ��л���,
 *         �Ա� ֮����Խ�������.
 *         b.��ArrayAdapter��������дһ���ڲ���,�ڲ�����װ����ÿ��ѡ����пؼ���id,����Ե�һ��
 *         ����ʱ���ɰ����ݴ浽����ڲ�����,�ڲ��������ͨ��view.setTag();�浽view��ȥ,view��
 *         Ҫ��ϵͳ����,�����´���Ҫ����ؼ���ʱ��ֱ��ͨ��view.getTag()��ֵ����ڲ���
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
		 * (ListView)findViewById(R.id.list_view); //����ͨ�������ķ�ʽ�鿴��Ļ�������
		 * listview.setAdapter(adapter);
		 */

		initFruits(); // ��ʼ��ˮ������
		FruitAdapter adapter = new FruitAdapter(MainActivity.this,
				R.layout.fruit_item, fruitList);
		listview = (ListView) findViewById(R.id.list_view);
		// ����ͨ�������ķ�ʽ�鿴��Ļ�������
		listview.setAdapter(adapter);
		
		//ListView���������ļ�����
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//����ͨ��position���ж��û����������һ��
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
