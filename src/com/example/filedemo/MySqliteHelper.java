package com.example.filedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper{

	public static String CREATE_PERSON="create table person (id integer primary key,name text,age integer)";
	public static String CREATE_BOOK="create table book (id integer,name text,price integer)";
	
	Context mContext;
	
	public MySqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.e("DB", "create");
		db.execSQL(CREATE_PERSON);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.e("DB", "upgrade");
		switch (oldVersion) {
		case 1:
			db.execSQL(CREATE_BOOK);
			break;
		case 2:
			break;
		default:
			break;
		}
	}

}
