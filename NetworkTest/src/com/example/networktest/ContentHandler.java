package com.example.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * 2016��8��12��17:03:34
 * Ϊ�˽���SAX����XML,��Ҫ�½�һ�������̳���DefaultHandler,����д5������
 * @author XFHY
 * 
 * ��5��������startDocument(),startElement(),characters(),endElement(),endDocument();
 * 
 * ÿ����ʼ����ĳ������ʱ��startElement()�����ͻ�õ�����,����localName������¼�ŵ�ǰ��������,�������ǰ�����¼����.
 * �����ڽ�������о������ݵ�ʱ��ͻ����characters()����,���ǻ���ݵ�ǰ�Ľ���������ж�.����������������ӵ���һ��StringBuilder��
 * .�����endElement()�����н����ж�,���app����Ѿ��������,�ʹ�ӡ��id,name,version������.
 * 
 * ע��:Ŀǰid,name,version�ж����ܰ����س����з���,����ڴ�ӡ֮ǰ���ǻ���Ҫ����һ��trim()����,���Ҵ�ӡ��ɺ�Ҫ��
 * StringBuilder��������յ�,��Ȼ�Ļ���Ӱ����һ�����ݵĶ�ȡ.
 * 
 */
public class ContentHandler extends DefaultHandler {
	
	private String nodeName;
	private StringBuilder id;
	private StringBuilder name;
	private StringBuilder version;
	
	//��ʼ������ʱ�����
	@Override
	public void startDocument() throws SAXException {
		id = new StringBuilder();
		name = new StringBuilder();
		version = new StringBuilder();
	}
	
	//��ʼ����ĳ������ʱ�����
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		//��¼��ǰ�����
		nodeName = localName;
	}
	
	//��ȡ����е�����ʱ����
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
	
	//��ɽ���ĳ��ĳ������ʱ�����
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("app".equals(localName)){
			//trim():�����ַ����ĸ���������ǰ���հ׺�β���հ�
			Log.d("xfhy","id is "+id.toString().trim());
			Log.d("xfhy","id is "+name.toString().trim());
			Log.d("xfhy","id is "+version.toString().trim());
			
			//���Ҫ��StringBuilder��յ�
			id.setLength(0);
			name.setLength(0);
			version.setLength(0);
		}
	}
	
	//�������XML��ʽ������ʱ�����
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
}
