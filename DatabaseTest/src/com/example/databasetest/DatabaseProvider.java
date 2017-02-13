package com.example.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 2016年7月31日8:52:09
 * 
 * 内容提供器,继承ContentProvider,实现它的几个方法
 * 注意:内容提供器需要在AndroidManifest.xml文件中注册才可以
 * @author XFHY
 *
 * 
 */
public class DatabaseProvider extends ContentProvider {

	public static final int BOOK_DIR = 0;
	public static final int BOOK_ITEM = 1;
	public static final int CATEGORY_DIR = 2;
	public static final int CATEGORY_ITEM = 3;
	
	//内容提供者姓名  权限
	public static final String AUTHORITY = "com.example.databasetest.provider";
	//借助UriMatcher类可以轻松地实现匹配内容URI的功能
	private static UriMatcher uriMatcher;
	private MyDatabaseHelper dbHelper;  //MyDatabaseHelper是SQLiteOpenHelper帮助类
	
	static{  //静态代码块
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);  //先实例化UriMatcher对象
		//参数:权限,路径,自定义代码
		uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);  //访问book整张表
		uriMatcher.addURI(AUTHORITY, "book/#", BOOK_DIR); //访问book单条数据
		uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
		uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
	}
	
	@Override
	public boolean onCreate() {
		/**参数
		 * @param context  上下文
		 * @param name     数据库名称
		 * @param factory  返回自定义的Cursor,一般传入null
		 * @param version  数据库版本
		 */ //初始化数据库的操作工具
		dbHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//查询数据
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {  //匹配URI
		case BOOK_DIR://查询整张表
			cursor = db.query("Book", projection, selection, selectionArgs,
					null, null, sortOrder);
			break;
		case BOOK_ITEM:  //查询单条数据
			//将URI权限之后的部分以"/"符号进行分割,并把分割后的结果放到一个字符串列表中,那这个列表的第0个
			//位置存放的是路径,第1个位置存放的就是id了.
			String bookId = uri.getPathSegments().get(1);
			//根据得到的这个id去Book表里去查
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
			//返回新插入的行的标识      表名
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
		//删除数据
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
		//更新数据
		SQLiteDatabase db = dbHelper.getWritableDatabase(); //获得可读写的数据库
		int updateRows = 0;  //记录删除了几行
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:            //参数:表名,数据,where部分,占位符
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
