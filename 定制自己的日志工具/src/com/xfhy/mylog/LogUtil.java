package com.xfhy.mylog;

import android.util.Log;

/**
 * 2016年8月19日10:56:33
 * 控制日志打印
 * @author XFHY
 *  
 * 在其他地方调用此类的静态方法,即可打印日志,
 *    eg:LogUtil.d("xfhy","debug log");
 * 只需要控制其中一个常量的值LEVEL即可
 */
public class LogUtil {
	//定义整型常量,值是递增的
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	public static final int LEVEL = VERBOSE;   //这里是拿来控制的,如果等于NOTHING就把所有的日志都屏蔽掉
	
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
