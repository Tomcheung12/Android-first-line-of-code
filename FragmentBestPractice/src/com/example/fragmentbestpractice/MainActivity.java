package com.example.fragmentbestpractice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * 2016��7��24��9:08:12
 * ��Ƭ�����ʵ��-һ�����װ������Ӧ��
 * @author XFHY
 * 
 * ����:
 *  1.�½�һ�����ŵ�ʵ����,News.�����б��������2������.
 *  2.�½�һ��news_item.xml����,�����б�������Ĳ���,�������ŵı���Ĳ���
 *  3.�½�һ�����ű����б��������,������������̳���ArrayAdapter,��������ָ��ΪNews��
 *  4.�½������ļ�news_content_frag.xml,�����������ݲ���, (���ű���,ϸ��,���Ĳ���).
 *  5.�½�һ��NewsContentFragment��,�̳���Fragment,��������ڼ����������ݲ���(��4��)
 *  6.�½�һ���ڻ��ʹ�õ��������ݲ���,�½�news_content.xml,������������Ų���(��5���Ǹ���),��������
 *     ��Ƭ.   ��Ƭ�Ĳ��ֺͻ�Ĳ��ֶ������news_content.xml
*   7.�½�NewsContentActivity��,��Ϊ��ʾ�������ݵĻ,�����������Ƭ����,����û���С�ֻ��������ű���
*      �����ת������
*   8.�½�һ����ʾ�����б�Ĳ���,news_title_frag.xml,����ֻ��һ��ListView,��������Ǹ���Ƭ�õ�.
*   9.������Ҫ�½�һ��NewsTitleFragment,�̳���Fragment,�����Ƭ��Ϊ�˼��������ListView,�������ж�
*      ��˫ҳģʽ���ǵ�ҳģʽ,�����˫ҳģʽ,�������Ƭ,��ҳģʽ�������NewsContentActivity
*   10.����activity_main��,���ظոյ�ListView��Ƭ,name=NewsTitleFragment
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
}
