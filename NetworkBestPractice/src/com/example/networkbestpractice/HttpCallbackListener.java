package com.example.networkbestpractice;

/**
 * 2016��8��12��21:54:29
 * ������һ���ӿ�,onFinish()������ʾ�������ɹ���Ӧ���������ʱ�����,onError()������ʾ����������������ִ����ʱ��
 * ����.���������������в���,onFinish()�����еĲ��������ŷ��������ص�����,��onError()�еĲ�����¼�Ŵ������ϸ��Ϣ.
 * @author XFHY
 *
 */
public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
