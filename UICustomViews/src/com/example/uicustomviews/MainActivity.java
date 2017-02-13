package com.example.uicustomviews;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * 引入布局,把自己写的布局,引入到另一个布局里去
 * include 
 * 解决问题:重复编写布局代码
 * 
 * 自定义控件:解决每次都要重复编写同一个布局的按钮的点击事件等(需要添加到布局文件里面)
 * @author XFHY
 *
 */
public class MainActivity extends Activity {

	private Button edit = null;
	private EditText edittext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main); 
        edit = (Button)findViewById(R.id.title_edit);
        edittext = (EditText)findViewById(R.id.edittext);
        edit.setOnClickListener(new OnClickListener() {   //使EditText可以编辑
			
			@Override
			public void onClick(View v) {
				edittext.setFocusable(true);
				edittext.setEnabled(true);
			}
		});
    }
}
