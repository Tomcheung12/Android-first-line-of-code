package com.example.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * 2016年8月12日17:03:34
 * 为了进行SAX解析XML,需要新建一个类来继承自DefaultHandler,并重写5个方法
 * @author XFHY
 * 
 * 那5个方法是startDocument(),startElement(),characters(),endElement(),endDocument();
 * 
 * 每当开始解析某个结点的时候startElement()方法就会得到调用,其中localName参数记录着当前结点的名字,这里我们把它记录下来.
 * 接着在解析结点中具体内容的时候就会调用characters()方法,我们会根据当前的结点名进行判断.将解析出的内容添加到哪一个StringBuilder中
 * .最后在endElement()方法中进行判断,如果app结点已经解析完成,就打印出id,name,version的内容.
 * 
 * 注意:目前id,name,version中都可能包括回车或换行符的,因此在打印之前我们还需要调用一下trim()方法,并且打印完成后还要将
 * StringBuilder的内容清空掉,不然的话会影响下一次内容的读取.
 * 
 */
public class ContentHandler extends DefaultHandler {
	
	private String nodeName;
	private StringBuilder id;
	private StringBuilder name;
	private StringBuilder version;
	
	//开始解析的时候调用
	@Override
	public void startDocument() throws SAXException {
		id = new StringBuilder();
		name = new StringBuilder();
		version = new StringBuilder();
	}
	
	//开始解析某个结点的时候调用
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		//记录当前结点名
		nodeName = localName;
	}
	
	//获取结点中的内容时调用
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if("id".equals(nodeName)){
			id.append(ch,start,length);
		} else if ("name".equals(nodeName)){
			name.append(ch,start,length);
		} else if ("version".equals(nodeName)){
			version.append(ch,start,length);
		}
	}
	
	//完成解析某个某个结点的时候调用
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("app".equals(localName)){
			//trim():返回字符串的副本，忽略前导空白和尾部空白
			Log.d("xfhy","id is "+id.toString().trim());
			Log.d("xfhy","id is "+name.toString().trim());
			Log.d("xfhy","id is "+version.toString().trim());
			
			//最后要将StringBuilder清空掉
			id.setLength(0);
			name.setLength(0);
			version.setLength(0);
		}
	}
	
	//完成整个XML格式解析的时候调用
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
}
