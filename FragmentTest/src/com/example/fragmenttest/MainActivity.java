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
 * ��Ƭ
 * 2016��7��23��9:07:54
 * ����һ���򵥵���Ƭʾ��,��һ��������������Ƭ,������������Ƭ���ֻ�ռ�
 * @author XFHY
 * 
 *  ��ʹ����Ƭ:�½���Ƭ����,���½���Ƭ����,�½���Ƭ����ʱ��Ҫ��дFragment��onCreateView()����,
 *  Ȼ�������������ͨ��LayoutInflater��inflate()������
	�ղŶ������Ƭ���ֶ�̬���ؽ���.һ����Ƭ���ֶ�Ӧһ����Ƭ����.
 *  Ȼ������Ҫ�����Ƭ�Ĳ����������Ƭ����,��Ƭ��������Ҫ�� android:name�������Ƭ����.
 *  
 *  ��̬�����Ƭ:
 *    1.��������ӵ���Ƭʵ��
 *    2.��ȡ��FragmentManager,�ڻ�п���ֱ�ӵ���getFragmentManager()�����õ�
 *    3.����һ������,ͨ������beginTransaction()��������
 *    4.�������м�����Ƭ,һ��ʹ��replace()����ʵ��,��Ҫ����������id�ʹ���ӵ���Ƭʵ��
 *    
 *    ��������㷵��ջ,��
 *    //����Ƭ��ģ�ⷵ��ջ   ��������һ������������������ջ��״̬,һ�㴫��null����
	  transaction.addToBackStack(null);
 *    5.�ύ����,����commit()�����������
 *    
 *  ʵ��˫ҳģʽ�뵥ҳģʽ����:
 *    1.�½�res/layout-large�ļ���,Ȼ���������½�һ��activity_main�����ļ�(�������Լ�д�õ�˫ҳ����),
 *    ����res/layout����һ��activity_main�����ļ�(ԭ����,��ҳ����),  
 *    Ȼ��ϵͳ���Զ��ĵ���������Ļ��ƽ��ʱ�ͻ����
 *    res/layout-large�����˫ҳ����,����С��Ļ����ص�ҳ����.
 *    
 *    �����large���޶���,���ݴ�С,��ʵ����small,normal,large,xlarge
 *    ���ݷֱ���:ldpi,mdpi,hdpi,xhdpi
 *    ����:land,port
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
			/*//��������ӵ���Ƭʵ��
			AnotherRightFragment fragment =  new AnotherRightFragment();
			
			//��ȡ��FragmentManager,�ڻ�п���ֱ�ӵ���getFragmentManager()�����õ�
			FragmentManager fragmentManager = getFragmentManager();
			
			//����һ������,ͨ������beginTransaction()��������
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			
			//�������м�����Ƭ,һ��ʹ��replace()����ʵ��,��Ҫ����������id�ʹ���ӵ���Ƭʵ��
			transaction.replace(R.id.right_layout, fragment);
			
			//����Ƭ��ģ�ⷵ��ջ   ��������һ������������������ջ��״̬,һ�㴫��null����
			transaction.addToBackStack(null);
			
			//�ύ����,����commit()�����������
			transaction.commit();*/
			break;

		default:
			break;
		}
	}
}
