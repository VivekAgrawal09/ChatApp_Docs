package com.example.vivek.myapplication_chat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static DBHelper sInstance;
	
	private static final String DATABASE_NAME = "ChatBot.db";
    private static final int DATABASE_VERSION = 1;
    
    public static synchronized DBHelper getInstance(Context context) {

        if (sInstance == null) {
          sInstance = new DBHelper(context);
        }
        return sInstance;
      }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ChatBotTable.CHATDATA_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    
}
