package com.example.uibestpractice;

public class Msg {
	public static final int TYPE_RECEIVED = 0;   //����
	public static final int TYPE_SENT = 1;       //����
	private String content;                      //����
	private int type;                            //��Ϣ����
	public Msg(String content, int type) {       //���캯��
		this.content = content;
		this.type = type;
	}
	public String getContent(){   //�������
		return content;
	}
	public int getType(){         //�����Ϣ����  �ǽ��ջ��Ƿ���
		return type;
	}
	@Override
	public String toString() {
		
		return content+":"+type;
	}
}
