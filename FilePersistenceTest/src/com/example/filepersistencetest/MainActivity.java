package com.example.filepersistencetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 2016年7月27日7:20:20
 * @author XFHY
 * 存储文件,从文件读取
 * 
 * TextUtils.isEmpty(inputText),非常好用,它可以一次性判断两种空值.当传入的字符串等于null或者等于空字符串
 * 的时候,这个方法都会返回true,从而使得我们不需要单独去判断着两种空值,再使用逻辑运算符连接起来了.
 * 
 */
public class MainActivity extends Activity {

	private EditText edit = null;
	private Button btn_save = null;
	private Button btn_read = null;
	StringBuilder data_file = new StringBuilder(); // 文件中的内容

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edit = (EditText) findViewById(R.id.edit);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_read = (Button) findViewById(R.id.btn_read);

		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String data = edit.getText().toString();
				save(data);
			}
		});
		btn_read.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String inputText = readFile();
				if (!TextUtils.isEmpty(inputText)) {  //判断非空 (null和空字符串都是反话true)
					edit.setText(data_file); // 设置数据到EditText控件中

					// 输入光标移动到文件末尾位置
					edit.setSelection(edit.length());
				}
			}
		});
	}

	/**
	 * 用户退出时,记得保存数据
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		String data = edit.getText().toString();
		save(data);
	}

	/**
	 * 阅读文件data中的内容
	 */
	public String readFile() {
		FileInputStream input = null;
		BufferedReader reader = null;
		try {
			// 打开文件,有可能文件找不到,抛出FileNotFoundException异常
			input = openFileInput("data");
			if (input != null) {
				//BufferedReader
				// 这个类就是一个包装类，它可以包装字符流，将字符流放入缓存里，先把字符读到缓存里，
				// 到缓存满了或者你flush的时候，再读入内存，就是为了提供读的效率而设计的
				reader = new BufferedReader(new InputStreamReader(input));
				String line = "";
				while ((line = reader.readLine()) != null) { // 一行一行的读取
					data_file.append(line + "\n");
				}
				Toast.makeText(MainActivity.this, "读取文件成功", 0).show();
				// edit.setText(data_file); //设置数据到EditText控件中
				// Log.i("xfhy","文件内容:"+data_file);
				return data_file.toString();
			}
		} catch (FileNotFoundException e1) { // 文件不存在
			Toast.makeText(MainActivity.this, "文件不存在", 0).show();
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			if (input != null) { // 关闭输入流
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 存储数据到文件中
	 * 
	 * @param data
	 */
	public void save(String data) {
		FileOutputStream out = null; // 文件输出流,输出内容到文件中
		BufferedWriter writer = null; // 缓冲流
		try {
			// 打开文件,如果不存在,则创建.如果存在,则覆盖原来的内容
			out = openFileOutput("data", Context.MODE_PRIVATE);

			// out = openFileOutput("data", Context.MODE_APPEND); //
			// 打开文件,添加内容到文件末尾

			// OutputStreamWriter是转换流
			/*
			 * OutputStreamWriter 是字符流通向字节流的桥梁：可使用指定的 charset 将要写入流中的字符编码成字节。
			 * 它使用的字符集可以由名称指定或显式给定，否则将接受平台默认的字符集。
			 * 
			 * 为了获得最高效率，可考虑将 OutputStreamWriter 包装到 BufferedWriter
			 * 中，以避免频繁调用转换器。例如：
			 * 
			 * Writer out = new BufferedWriter(new
			 * OutputStreamWriter(System.out));
			 */
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(data); // 向文件中写入数据
			Toast.makeText(MainActivity.this, "保存成功!", 0).show();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) { // 需要在finally中关闭文件流,关闭之前判断是否为空
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
