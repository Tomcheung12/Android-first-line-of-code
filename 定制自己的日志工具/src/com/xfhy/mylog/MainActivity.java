package com.xfhy.mylog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 2016��8��19��10:30:15
 * 
 * �����Լ�����־����
 * 
 * @author XFHY
 * 
 * Android�Դ�����־���ܷǳ�ǿ��,����Ҳ��ȱ��,�����ڴ�ӡ��־�Ŀ��Ʒ�������ò�����.
 * ����ȷ�,�����ڱ�дһ���Ƚ��Ӵ����Ŀ,�ڼ�Ϊ�˷������,�ڴ���ĺܶ�ط�����ӡ�˴�������־.�����Ŀ�Ѿ��������,����ȴ��һ���ǳ�����ͷ��
 * ������,֮ǰ���ڵ��Ե���Щ��־,����Ŀ��ʽ����֮����Ȼ���ճ���ӡ,���������ή�ͳ��������Ч��,���һ����ܽ�һЩ�����Ե�����й¶��ȥ.��ʱ
 * ����ӡ��־һ��һ�е�ɾ��,�ƺ���ʱ����,�����п����Ժ���Ҫ.���,�������������ǿ��Կ�����־�Ĵ�ӡ,�������ڿ����׶���־�Ϳ��Դ�ӡ,��
 * ����������֮��Ͱ���־���ε�.
 * 
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LogUtil.d("xfhy","Haha");   //�Լ�����Ĵ�ӡ��־����,���Կ��ƴ�ӡ
    }

}
