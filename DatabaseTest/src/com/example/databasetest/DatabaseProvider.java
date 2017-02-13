package com.example.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 2016��7��31��8:52:09
 * 
 * �����ṩ��,�̳�ContentProvider,ʵ�����ļ�������
 * ע��:�����ṩ����Ҫ��AndroidManifest.xml�ļ���ע��ſ���
 * @author XFHY
 *
 * 
 */
public class DatabaseProvider extends ContentProvider {

	public static final int BOOK_DIR = 0;
	public static final int BOOK_ITEM = 1;
	public static final int CATEGORY_DIR = 2;
	public static final int CATEGORY_ITEM = 3;
	
	//�����ṩ������  Ȩ��
	public static final String AUTHORITY = "com.example.databasetest.provider";
	//����UriMatcher��������ɵ�ʵ��ƥ������URI�Ĺ���
	private static UriMatcher uriMatcher;
	private MyDatabaseHelper dbHelper;  //MyDatabaseHelper��SQLiteOpenHelper������
	
	static{  //��̬�����
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);  //��ʵ����UriMatcher����
		//����:Ȩ��,·��,�Զ������
		uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);  //����book���ű�
		uriMatcher.addURI(AUTHORITY, "book/#", BOOK_DIR); //����book��������
		uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
		uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
	}
	
	@Override
	public boolean onCreate() {
		/**����
		 * @param context  ������
		 * @param name     ���ݿ�����
		 * @param factory  �����Զ����Cursor,һ�㴫��null
		 * @param version  ���ݿ�汾
		 */ //��ʼ�����ݿ�Ĳ�������
		dbHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//��ѯ����
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {  //ƥ��URI
		case BOOK_DIR://��ѯ���ű�
			cursor = db.query("Book", projection, selection, selectionArgs,
					null, null, sortOrder);
			break;
		case BOOK_ITEM:  //��ѯ��������
			//��URIȨ��֮��Ĳ�����"/"���Ž��зָ�,���ѷָ��Ľ���ŵ�һ���ַ����б���,������б�ĵ�0��
			//λ�ô�ŵ���·��,��1��λ�ô�ŵľ���id��.
			String bookId = uri.getPathSegments().get(1);
			//���ݵõ������idȥBook����ȥ��
			cursor = db.query("Book", projection, "id=?", new String[]{bookId},
					null, null, sortOrder);
			break;
		case CATEGORY_DIR:
			cursor = db.query("Category", projection, selection, selectionArgs,
					null, null, sortOrder);
			break;
		case CATEGORY_ITEM:
			String categoryId = uri.getPathSegments().get(1);
			cursor = db.query("Category", projection, "id=?", new String[]{categoryId},
					null, null, sortOrder);
			break;
		default:
			break;
		}
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book";
		case BOOK_ITEM:
			return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.book";
		case CATEGORY_DIR:
			return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.category";
		case CATEGORY_ITEM:
			return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.category";
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri uriReturn = null;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
		case BOOK_ITEM:
			//�����²�����еı�ʶ      ����
			long newBookId = db.insert("Book", null, values);
			uriReturn = Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
			break;
			
		case CATEGORY_DIR:
		case CATEGORY_ITEM:
			long newCategoryId  = db.insert("Category", null, values);
			uriReturn = Uri.parse("content://"+AUTHORITY+"/category/"+newCategoryId);
			break;
		default:
			break;
		}
		return uriReturn;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		//ɾ������
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int deletedRows = 0;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			deletedRows = db.delete("Book", selection, selectionArgs);
			break;
		case BOOK_ITEM:
			String bookId = uri.getPathSegments().get(1);
			deletedRows = db.delete("Book", "id=?", new String[]{bookId});
			break;
		case CATEGORY_DIR:
			deletedRows = db.delete("Category", selection, selectionArgs);
			break;
		case CATEGORY_ITEM:
			String categoryId = uri.getPathSegments().get(1);
			deletedRows = db.delete("Category", "id=?", new String[]{categoryId});
			break;
		default:
			break;
		}
		return deletedRows;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		//��������
		SQLiteDatabase db = dbHelper.getWritableDatabase(); //��ÿɶ�д�����ݿ�
		int updateRows = 0;  //��¼ɾ���˼���
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:            //����:����,����,where����,ռλ��
			updateRows = db.update("Book", values, selection, selectionArgs);
			break;
		case BOOK_ITEM:
			String bookId = uri.getPathSegments().get(1);
			updateRows = db.update("Book", values, "id=?", new String[]{bookId});
			break;
		case CATEGORY_DIR:
			updateRows = db.update("Category", values, selection, selectionArgs);
			break;
		case CATEGORY_ITEM:
			String categoryId = uri.getPathSegments().get(1);
			updateRows = db.update("Category", values, "id=?", new String[]{categoryId});
			break;
		default:
			break;
		}
		return updateRows;
	}

}
