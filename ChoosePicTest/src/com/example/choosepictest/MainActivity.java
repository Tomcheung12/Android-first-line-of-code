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
 * 2016��8��3��8:00:53
 * 
 * �����ֻ�������ͷ��������,���պ�ü�,����ʾ����
 * �����ѡ����Ƭ,��ʾ����
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
				//�ҽ��ļ�������Ϊ��ǰ����
				String format1 = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
				String imageName = sdf1.format(new Date())+".jpg";
				
				//����File����,���ڴ洢���պ����Ƭ
				//Environment.getExternalStorageDirectory()��SD����Ŀ¼
				File outputImage = new File(Environment.getExternalStorageDirectory(),imageName);
				try {
					if(outputImage.exists()){  //����ļ��Ѵ���,��ɾ���ļ�
						outputImage.delete();
					}
					outputImage.createNewFile(); //�������ļ�
				} catch (Exception e) {
					e.printStackTrace();
				}
				imageUri = Uri.fromFile(outputImage);  //��File����ת��ΪUri����
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  //��ʽ����
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent,TAKE_PHOTO); //��Ϊ�����պ���н������,�������������
			}
		});
        
                      /*------------------�����ѡ����Ƭ-----------------------*/
        chooseFromAlbum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��ʽ����,����ѡ����Ƭ�ĳ���
				Intent intent = new Intent("android.intent.action.GET_CONTENT");
				intent.setType("image/*");
				startActivityForResult(intent, CHOOSE_PHOTO);  //�ڶ���������������
			}
		});
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
		case TAKE_PHOTO:
			if(resultCode==RESULT_OK){  //������ճɹ�
				//������һ��Intent����,���Intent�Ƕ��ĳ�����Ƭ���вü���
				//�ü������Ƭ��Ȼ�������output_image.jpg��
				Intent intent = new Intent("com.android.camera.action.CROP"); 
				intent.setDataAndType(imageUri, "image/*");   //����intent��data��Type����
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);  //�������Uri,�������ļ�������
				startActivityForResult(intent,CROP_PHOTO);   //�ü���ɺ�,�ֻ�ص����onActivityResult()����
			}
			break;
		case CROP_PHOTO:
			if(resultCode==RESULT_OK){  //����ü��ɹ�
				try {
					//BitmapFactory��decodeStream()�������Խ�����ͼƬ������Bitmap����
					Bitmap bitmap = BitmapFactory.
							decodeStream(getContentResolver().openInputStream(imageUri));
					picture.setImageBitmap(bitmap);   //���ü������Ƭ��ʾ����
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case CHOOSE_PHOTO:
			if(resultCode==RESULT_OK){
				//�ж��ֻ�ϵͳ�汾��
				if(Build.VERSION.SDK_INT>=19){
					//4.4������ϵͳʹ�������������ͼƬ
					handleImageOnKitKat(data);   //data�Ƿ��ص�Intent����
				} else {
					handleImageBeforeKitKat(data);
				}
			}
			break;
		default:
			break;
		}
    }
    
    //������װ����Uri
    @TargetApi(19) 
    private void handleImageOnKitKat(Intent data) {
		String imagePath = null;
		Uri uri = data.getData();
		if(DocumentsContract.isDocumentUri(this, uri)){
			//�����document���͵�Uri,��ͨ��document id����
			String docId = DocumentsContract.getDocumentId(uri);
			//getAuthority()�õ��������Uri
			if("com.android.providers.media.documents".equals(uri.getAuthority())){
				String id = docId.split(":")[1];  //���������ָ�ʽ��id    �ַ����ָ�,ȡ����벿��
				String selection = MediaStore.Images.Media._ID+"="+id;
				imagePath = getImagePath(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
			} else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
				//���id��Uri�������
				Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				imagePath = getImagePath(contentUri,null);
			}
		} else if("content".equalsIgnoreCase(uri.getScheme())){
			//�������document���͵�Uri,��ʹ����ͨ��ʽ����
			imagePath = getImagePath(uri,null);
		}
		displayImage(imagePath);
	}
    
    //û�з�װ��ͼƬ��Uri
    private void handleImageBeforeKitKat(Intent data) {
    	Uri uri = data.getData();
    	String imagePath = getImagePath(uri, null);
    	displayImage(imagePath);
    }
    
    //����ͼƬ��Uri
    private String getImagePath(Uri uri, String selection) {
		String path = null;
		//ͨ��Uri��selection���ȡ��ʵ��ͼƬ·��
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if(cursor!=null){
			if(cursor.moveToFirst()){   //��ָ���Ƶ��ʼ����
				//�ȵõ�Media.DATA����һ��,�ٻ�ȡ�����Ԫ��ֵ
				path = cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
			cursor.close();   //�ر�
		}
		return path;
	}
    
    //��ʾͼƬ
    private void displayImage(String imagePath) {
		if(imagePath!=null){
			//BitmapFactory��decodeFile()���Խ�ͼƬתΪBitmap����
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			picture.setImageBitmap(bitmap);
		} else {
			Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
		}
	}
}
