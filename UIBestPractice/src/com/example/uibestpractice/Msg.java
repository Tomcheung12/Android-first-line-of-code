package com.example.uibestpractice;

public class Msg {
	public static final int TYPE_RECEIVED = 0;   //接收
	public static final int TYPE_SENT = 1;       //发送
	private String content;                      //内容
	private int type;                            //消息类型
	public Msg(String content, int type) {       //构造函数
		this.content = content;
		this.type = type;
	}
	public String getContent(){   //获得内容
		return content;
	}
	public int getType(){         //获得消息类型  是接收还是发送
		return type;
	}
	@Override
	public String toString() {
		
		return content+":"+type;
	}
}
