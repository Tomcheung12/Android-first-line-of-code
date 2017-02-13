package com.example.androidthreadtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 2016��8��5��9:58:17
 * 
 * Ϊ�˸��ӷ������������߳��ж�UI���в���,Android���ṩ������һЩ���õĹ���,AsyncTask��������.
 * ����ֻ��һ����ʽ,��û��ʵ��
 * 
 * @author XFHY
 *
 * һ����Ҫ��д��������������ɶ�����Ŀ���onPreExecute(),doInBackground(),onProgressUpdate(),onPostExecute()
 * 
 *  AsyncTask<Void,Integer,Boolean>
 *  
 *  ʹ��AsyncTask�ľ��Ͼ���,��doInBackground()������ȥִ�о���ĺ�ʱ����,��onProgressUpdate()�����н���UI����,
 *  ��onPostExecute()������ִ��һЩ�������β����.
 *  
 *  ����������������,ֻ���дһ�´��뼴��:
 *    new DownloadTask().execute();
 *  
 */
public class DownloadTask extends AsyncTask<Void,Integer,Boolean>{
	
	/**
	 * ��������ں�̨����ʼִ��֮ǰ����,���ڽ���һЩ�����ϵĳ�ʼ������,������ʾһ���������Ի����
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//progressDialog.show();       //��ʾ���ȶԻ���
	}
	
	/**
	 * ��������е����д��붼�������߳�������,����Ӧ��������ȥ�������еĺ�ʱ��������.����һ����ɾͿ���ͨ��return������������ִ�н��
	 * ����,���AsyncTask�ĵ��������Ͳ���ָ������Void,�Ϳ��Բ���������ִ�еĽ��.ע��,������������ǲ����Խ���UI������,�����Ҫ����UI
	 * Ԫ��,����˵������ǰ�����ִ�н���,���Ե���publishProgress(Progress...)���������
	 */
	@Override
	protected Boolean doInBackground(Void... params) {
		/*try {
			while(true){
				int downloadPercent = doDownload();  //����һ���鹹�ķ���   ��ʾȥ����������
				publishProgress(downloadPercent);
				if(downloadPercent >= 100){
					break;
				}
			}
		} catch (Exception e) {
			return false;
		}*/
		return true;
	}
	
	/**
	 * ���ں�̨�����е�����publishProgress(Progress...)������,��������ͻ�ܿ챻����,������Я���Ĳ��������ں�̨�����д��ݹ�����.
	 * ����������п��Զ�UI���в���,���ò����е���ֵ�Ϳ��ԶԽ���Ԫ�ؽ�����Ӧ�ظ���
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		//super.onProgressUpdate(values);
		//progressDialog.setMessage("Downloaded"+values[0]+"%");
	}
	
	/**
	 * ����̨����ִ����ϲ�ͨ��return�����з���ʱ,��������ͻ�ܿ챻����.���ص����ݻ���Ϊ�������ݵ��˷�����,�������÷��ص�����������һЩ
	 * UI����,����˵��������ִ�еĽ��,�Լ��رյ��������Ի����.
	 */
	protected void onPostExecute(Boolean result) {
		/*progressDialog.dismiss();  //�رս������Ի���
		if(result){
			//���سɹ�
			Toast.makeText(context, "Download succeeded", Toast.LENGTH_SHORT).show();
		}else{
			//����ʧ��
			Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
		}*/
	};
}
