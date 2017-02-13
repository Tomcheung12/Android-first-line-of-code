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
 * 2016��7��27��7:20:20
 * @author XFHY
 * �洢�ļ�,���ļ���ȡ
 * 
 * TextUtils.isEmpty(inputText),�ǳ�����,������һ�����ж����ֿ�ֵ.��������ַ�������null���ߵ��ڿ��ַ���
 * ��ʱ��,����������᷵��true,�Ӷ�ʹ�����ǲ���Ҫ����ȥ�ж������ֿ�ֵ,��ʹ���߼����������������.
 * 
 */
public class MainActivity extends Activity {

	private EditText edit = null;
	private Button btn_save = null;
	private Button btn_read = null;
	StringBuilder data_file = new StringBuilder(); // �ļ��е�����

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
				if (!TextUtils.isEmpty(inputText)) {  //�жϷǿ� (null�Ϳ��ַ������Ƿ���true)
					edit.setText(data_file); // �������ݵ�EditText�ؼ���

					// �������ƶ����ļ�ĩβλ��
					edit.setSelection(edit.length());
				}
			}
		});
	}

	/**
	 * �û��˳�ʱ,�ǵñ�������
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		String data = edit.getText().toString();
		save(data);
	}

	/**
	 * �Ķ��ļ�data�е�����
	 */
	public String readFile() {
		FileInputStream input = null;
		BufferedReader reader = null;
		try {
			// ���ļ�,�п����ļ��Ҳ���,�׳�FileNotFoundException�쳣
			input = openFileInput("data");
			if (input != null) {
				//BufferedReader
				// ��������һ����װ�࣬�����԰�װ�ַ��������ַ������뻺����Ȱ��ַ����������
				// ���������˻�����flush��ʱ���ٶ����ڴ棬����Ϊ���ṩ����Ч�ʶ���Ƶ�
				reader = new BufferedReader(new InputStreamReader(input));
				String line = "";
				while ((line = reader.readLine()) != null) { // һ��һ�еĶ�ȡ
					data_file.append(line + "\n");
				}
				Toast.makeText(MainActivity.this, "��ȡ�ļ��ɹ�", 0).show();
				// edit.setText(data_file); //�������ݵ�EditText�ؼ���
				// Log.i("xfhy","�ļ�����:"+data_file);
				return data_file.toString();
			}
		} catch (FileNotFoundException e1) { // �ļ�������
			Toast.makeText(MainActivity.this, "�ļ�������", 0).show();
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			if (input != null) { // �ر�������
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
	 * �洢���ݵ��ļ���
	 * 
	 * @param data
	 */
	public void save(String data) {
		FileOutputStream out = null; // �ļ������,������ݵ��ļ���
		BufferedWriter writer = null; // ������
		try {
			// ���ļ�,���������,�򴴽�.�������,�򸲸�ԭ��������
			out = openFileOutput("data", Context.MODE_PRIVATE);

			// out = openFileOutput("data", Context.MODE_APPEND); //
			// ���ļ�,������ݵ��ļ�ĩβ

			// OutputStreamWriter��ת����
			/*
			 * OutputStreamWriter ���ַ���ͨ���ֽ�������������ʹ��ָ���� charset ��Ҫд�����е��ַ�������ֽڡ�
			 * ��ʹ�õ��ַ�������������ָ������ʽ���������򽫽���ƽ̨Ĭ�ϵ��ַ�����
			 * 
			 * Ϊ�˻�����Ч�ʣ��ɿ��ǽ� OutputStreamWriter ��װ�� BufferedWriter
			 * �У��Ա���Ƶ������ת���������磺
			 * 
			 * Writer out = new BufferedWriter(new
			 * OutputStreamWriter(System.out));
			 */
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(data); // ���ļ���д������
			Toast.makeText(MainActivity.this, "����ɹ�!", 0).show();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) { // ��Ҫ��finally�йر��ļ���,�ر�֮ǰ�ж��Ƿ�Ϊ��
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
