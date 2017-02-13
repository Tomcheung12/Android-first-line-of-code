package com.example.androidthreadtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 2016年8月5日9:58:17
 * 
 * 为了更加方便我们在子线程中对UI进行操作,Android还提供了另外一些好用的工具,AsyncTask就是其中.
 * 下面只是一个方式,并没有实现
 * 
 * @author XFHY
 *
 * 一般需要重写几个方法才能完成对任务的控制onPreExecute(),doInBackground(),onProgressUpdate(),onPostExecute()
 * 
 *  AsyncTask<Void,Integer,Boolean>
 *  
 *  使用AsyncTask的诀窍就是,在doInBackground()方法中去执行具体的耗时任务,在onProgressUpdate()方法中进行UI操作,
 *  在onPostExecute()方法中执行一些任务的收尾工作.
 *  
 *  如果想启动这个任务,只需编写一下代码即可:
 *    new DownloadTask().execute();
 *  
 */
public class DownloadTask extends AsyncTask<Void,Integer,Boolean>{
	
	/**
	 * 这个方法在后台任务开始执行之前调用,用于进行一些界面上的初始化操作,比如显示一个进度条对话框等
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//progressDialog.show();       //显示进度对话框
	}
	
	/**
	 * 这个方法中的所有代码都会在子线程中运行,我们应该在这里去处理所有的耗时操作任务.任务一旦完成就可以通过return语句来将任务的执行结果
	 * 返回,如果AsyncTask的第三个泛型参数指定的是Void,就可以不返回任务执行的结果.注意,在这个方法中是不可以进行UI操作的,如果需要更新UI
	 * 元素,比如说反馈当前任务的执行进度,可以调用publishProgress(Progress...)方法来完成
	 */
	@Override
	protected Boolean doInBackground(Void... params) {
		/*try {
			while(true){
				int downloadPercent = doDownload();  //这是一个虚构的方法   表示去做下载任务
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
	 * 当在后台任务中调用了publishProgress(Progress...)方法后,这个方法就会很快被调用,方法中携带的参数就是在后台任务中传递过来的.
	 * 在这个方法中可以对UI进行操作,利用参数中的数值就可以对界面元素进行相应地更新
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		//super.onProgressUpdate(values);
		//progressDialog.setMessage("Downloaded"+values[0]+"%");
	}
	
	/**
	 * 当后台任务执行完毕并通过return语句进行返回时,这个方法就会很快被调用.返回的数据会作为参数传递到此方法中,可以利用返回的数据来进行一些
	 * UI操作,比如说提醒任务执行的结果,以及关闭掉进度条对话框等.
	 */
	protected void onPostExecute(Boolean result) {
		/*progressDialog.dismiss();  //关闭进度条对话框
		if(result){
			//下载成功
			Toast.makeText(context, "Download succeeded", Toast.LENGTH_SHORT).show();
		}else{
			//下载失败
			Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
		}*/
	};
}
