package com.example.choosepictest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 2016年8月3日8:00:53
 * 
 * 调用手机的摄像头进行拍照,拍照后裁剪,并显示出来
 * 从相册选择照片,显示出来
 * 
 * @author XFHY
 *
 */
public class MainActivity extends Activity {

	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int CHOOSE_PHOTO = 3;
	private Button takePhoto = null;
	private ImageView picture = null;
	private Button chooseFromAlbum = null;
	private Uri imageUri = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        takePhoto = (Button)findViewById(R.id.take_photo);
        picture = (ImageView)findViewById(R.id.picture);
        chooseFromAlbum = (Button)findViewById(R.id.choose_from_album);
        takePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//我将文件名命名为当前日期
				String format1 = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
				String imageName = sdf1.format(new Date())+".jpg";
				
				//创建File对象,用于存储拍照后的照片
				//Environment.getExternalStorageDirectory()是SD卡根目录
				File outputImage = new File(Environment.getExternalStorageDirectory(),imageName);
				try {
					if(outputImage.exists()){  //如果文件已存在,则删除文件
						outputImage.delete();
					}
					outputImage.createNewFile(); //创建新文件
				} catch (Exception e) {
					e.printStackTrace();
				}
				imageUri = Uri.fromFile(outputImage);  //将File对象转换为Uri对象
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  //隐式启动
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent,TAKE_PHOTO); //因为拍完照后会有结果返回,所以用这个启动
			}
		});
        
                      /*------------------从相册选择照片-----------------------*/
        chooseFromAlbum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//隐式启动,可以选择照片的程序
				Intent intent = new Intent("android.intent.action.GET_CONTENT");
				intent.setType("image/*");
				startActivityForResult(intent, CHOOSE_PHOTO);  //第二个参数是请求码
			}
		});
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
		case TAKE_PHOTO:
			if(resultCode==RESULT_OK){  //如果拍照成功
				//构建出一个Intent对象,这个Intent是对拍出的照片进行裁剪的
				//裁剪后的照片依然会输出到output_image.jpg中
				Intent intent = new Intent("com.android.camera.action.CROP"); 
				intent.setDataAndType(imageUri, "image/*");   //设置intent的data和Type属性
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);  //解析这个Uri,并设置文件的名称
				startActivityForResult(intent,CROP_PHOTO);   //裁剪完成后,又会回到这个onActivityResult()方法
			}
			break;
		case CROP_PHOTO:
			if(resultCode==RESULT_OK){  //如果裁剪成功
				try {
					//BitmapFactory的decodeStream()方法可以将这张图片解析成Bitmap对象
					Bitmap bitmap = BitmapFactory.
							decodeStream(getContentResolver().openInputStream(imageUri));
					picture.setImageBitmap(bitmap);   //将裁剪后的照片显示出来
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case CHOOSE_PHOTO:
			if(resultCode==RESULT_OK){
				//判断手机系统版本号
				if(Build.VERSION.SDK_INT>=19){
					//4.4及以上系统使用这个方法处理图片
					handleImageOnKitKat(data);   //data是返回的Intent数据
				} else {
					handleImageBeforeKitKat(data);
				}
			}
			break;
		default:
			break;
		}
    }
    
    //解析封装过的Uri
    @TargetApi(19) 
    private void handleImageOnKitKat(Intent data) {
		String imagePath = null;
		Uri uri = data.getData();
		if(DocumentsContract.isDocumentUri(this, uri)){
			//如果是document类型的Uri,则通过document id处理
			String docId = DocumentsContract.getDocumentId(uri);
			//getAuthority()得到解析后的Uri
			if("com.android.providers.media.documents".equals(uri.getAuthority())){
				String id = docId.split(":")[1];  //解析出数字格式的id    字符串分割,取出后半部分
				String selection = MediaStore.Images.Media._ID+"="+id;
				imagePath = getImagePath(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
			} else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
				//添加id到Uri的最后面
				Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				imagePath = getImagePath(contentUri,null);
			}
		} else if("content".equalsIgnoreCase(uri.getScheme())){
			//如果不是document类型的Uri,则使用普通方式处理
			imagePath = getImagePath(uri,null);
		}
		displayImage(imagePath);
	}
    
    //没有封装的图片的Uri
    private void handleImageBeforeKitKat(Intent data) {
    	Uri uri = data.getData();
    	String imagePath = getImagePath(uri, null);
    	displayImage(imagePath);
    }
    
    //传入图片的Uri
    private String getImagePath(Uri uri, String selection) {
		String path = null;
		//通过Uri和selection类获取真实的图片路径
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if(cursor!=null){
			if(cursor.moveToFirst()){   //将指针移到最开始那里
				//先得到Media.DATA在那一列,再获取这个单元的值
				path = cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
			cursor.close();   //关闭
		}
		return path;
	}
    
    //显示图片
    private void displayImage(String imagePath) {
		if(imagePath!=null){
			//BitmapFactory的decodeFile()可以将图片转为Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			picture.setImageBitmap(bitmap);
		} else {
			Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
		}
	}
}
