package com.example.filedemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button btn_1;
	Button btn_2;
	Button btn_3;
	Button btn_4;
	Button btn_5;
	Button btn_6;
	Button btn_7;
	Button btn_8;
	EditText et;
	EditText et2;
	MySqliteHelper helper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	public void initView(){
		btn_1=(Button)findViewById(R.id.button1);
		btn_2=(Button)findViewById(R.id.button2);
		btn_3=(Button)findViewById(R.id.button3);
		btn_4=(Button)findViewById(R.id.button4);
		btn_5=(Button)findViewById(R.id.button5);
		btn_6=(Button)findViewById(R.id.button6);
		btn_7=(Button)findViewById(R.id.button7);
		btn_8=(Button)findViewById(R.id.button8);
		et=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		helper=new MySqliteHelper(MainActivity.this, "person.db", null, 2);
		//storage save
		btn_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("FILE", "write");
				if(et.getText()!=null){
					try {
						saveFile(et.getText().toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		//sharedpreference save
		btn_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("SharedPreference", "write");
				if(et.getText()!=null){
					savePreference(et.getText().toString());
				}
			}
		});
		
		//sqlite save
		btn_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("Sqlite", "write");
				saveSqlite(helper);
			}
		});
		
        btn_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("FILE", "read");
				try {
					et2.setText(read());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        btn_5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("SharedPreference", "read");
				readPreference();
			}
		});
        
        btn_6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("Sqlite", "read");
				query(helper);
			}
		});
        
        btn_7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("Sqlite", "update");
				update(helper);
			}
		});

        btn_8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("Sqlite", "delete");
				delete(helper);
			}
		});
	}
	
	
	public void saveFile(String s) throws IOException{
		FileOutputStream out=null;
		BufferedWriter writer=null;
		try {
			out=openFileOutput("data.txt", Context.MODE_PRIVATE);
			writer=new BufferedWriter(new OutputStreamWriter(out));
			writer.write(s);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(writer!=null){
				writer.close();
			}
		}				
		 
	}
	
    public String read() throws IOException{
		FileInputStream in;
		BufferedReader reader = null;
		StringBuilder builder = null;
		try {
			in=openFileInput("data.txt");
			reader=new BufferedReader(new InputStreamReader(in));
			builder=new StringBuilder();
			String line;
			while ((line=reader.readLine())!=null) {
				builder.append(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return builder.toString();
		
	}

	public void savePreference(String s){
		SharedPreferences preferences=getSharedPreferences("data", MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString("frist", s);
		editor.commit();
	}
	
	public void readPreference(){
		SharedPreferences preferences=getSharedPreferences("data", MODE_PRIVATE);
		et2.setText(preferences.getString("frist", ""));
	}

	public void saveSqlite(MySqliteHelper helper){
		SQLiteDatabase db=helper.getWritableDatabase();
		db.beginTransaction();
		ContentValues values=new ContentValues();
		values.put("id", "1");
		values.put("name", "wang");
		values.put("age", "15");
		db.insert("person", null, values);
		values.clear();
		values.put("id", "2");
		values.put("name", "li");
		values.put("age", "35");
		db.insert("person", null, values);
		db.setTransactionSuccessful();
		//db.execSQL("insert into person (id,name,age)values(?,?,?)",new String[]{"1","test","10"});
		db.endTransaction();
	}
	
	public void update(MySqliteHelper helper) {
		SQLiteDatabase db=helper.getWritableDatabase();
		db.beginTransaction();
		ContentValues values=new ContentValues();
		values.put("age", "22");
		//db.execSQL("update person set age=? where id=?",new String[]{"222","1"});
		db.update("person", values, "id=?", new String[]{"1"});
		db.setTransactionSuccessful();
		db.endTransaction();
		
	}
	

	public void delete(MySqliteHelper helper) {
		SQLiteDatabase db=helper.getWritableDatabase();
		db.beginTransaction();
		//db.execSQL("delete from person where id =?", new String[]{"1"});
		db.delete("person", "id=?", new String[]{"1"});
		db.setTransactionSuccessful();
		db.endTransaction();
	}

    public void query(MySqliteHelper helper) {
		SQLiteDatabase db=helper.getReadableDatabase();
		db.beginTransaction();
		Cursor cursor=db.query("person", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Log.e("name", cursor.getString(cursor.getColumnIndex("name")));
				Log.e("age", ""+cursor.getInt(cursor.getColumnIndex("age")));
				Log.e("id", ""+cursor.getInt(cursor.getColumnIndex("id")));
			}while(cursor.moveToNext());
		}
		cursor.close();
		//db.rawQuery("select * from person",null);
		db.setTransactionSuccessful();
		db.endTransaction();
	}

}
