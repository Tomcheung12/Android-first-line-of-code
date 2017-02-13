package com.example.networkbestpractice;

/**
 * 2016年8月12日21:54:29
 * 这里是一个接口,onFinish()方法表示服务器成功响应我们请求的时候调用,onError()方法表示当进行网络操作出现错误的时候
 * 调用.这两个方法都带有参数,onFinish()方法中的参数代表着服务器返回的数据,而onError()中的参数记录着错误的详细信息.
 * @author XFHY
 *
 */
public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
