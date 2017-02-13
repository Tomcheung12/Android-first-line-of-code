package com.xfhy.mylog;

import android.util.Log;

/**
 * 2016��8��19��10:56:33
 * ������־��ӡ
 * @author XFHY
 *  
 * �������ط����ô���ľ�̬����,���ɴ�ӡ��־,
 *    eg:LogUtil.d("xfhy","debug log");
 * ֻ��Ҫ��������һ��������ֵLEVEL����
 */
public class LogUtil {
	//�������ͳ���,ֵ�ǵ�����
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	public static final int LEVEL = VERBOSE;   //�������������Ƶ�,�������NOTHING�Ͱ����е���־�����ε�
	
	public static void v(String tag,String msg){
		if(LEVEL <= VERBOSE){
			Log.v(tag,msg);
		}
	}
	
	public static void d(String tag,String msg){
		if(LEVEL <= DEBUG){
			Log.d(tag,msg);
		}
	}
	
	public static void i(String tag,String msg){
		if(LEVEL <= INFO){
			Log.i(tag,msg);
		}
	}
	
	public static void w(String tag,String msg){
		if(LEVEL <= WARN){
			Log.w(tag,msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(LEVEL <= ERROR){
			Log.e(tag,msg);
		}
	}
	
}
