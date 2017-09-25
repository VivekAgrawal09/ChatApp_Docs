package com.example.vivek.myapplication_chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

/**
 * Created by vivek on 25/9/17.
 */
public class ChatBotHelper {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public void createDb(Context context) {
        dbHelper = DBHelper.getInstance(context);
        try {
            db = dbHelper.getWritableDatabase();
        }
        catch(SQLiteException e){
            e.printStackTrace();
        }
    }

    public boolean add(ChatMessage message) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChatBotTable.CHATBOT_NAME, message.getChatBotName());
        contentValues.put(ChatBotTable.CHATBOT_ID, message.getChatBotID());
        contentValues.put(ChatBotTable.CHATBOT_MESSAGE, message.getMessage());
        contentValues.put(ChatBotTable.CHATBOT_EMOTION, message.getEmotion());
        contentValues.put(ChatBotTable.CHATBOT_ISLEFT, message.isLeft() ? 1 : 0);
        db.insert(ChatBotTable.CHATDATA_TABLE, null, contentValues);
        return true;
    }

    public ArrayList<ChatMessage> getAll() {
        Cursor cursor = db.rawQuery( "SELECT * FROM " + ChatBotTable.CHATDATA_TABLE, null );
        if (cursor.getCount() < 1) { cursor.close(); return null; }
        ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChatMessage message = new ChatMessage();
            message.setChatBotName(cursor.getString(cursor.getColumnIndex(ChatBotTable.CHATBOT_NAME)));
            message.setChatBotID(cursor.getInt(cursor.getColumnIndex(ChatBotTable.CHATBOT_ID)));
            message.setMessage(cursor.getString(cursor.getColumnIndex(ChatBotTable.CHATBOT_MESSAGE)));
            message.setEmotion(cursor.getString(cursor.getColumnIndex(ChatBotTable.CHATBOT_EMOTION)));
            message.setLeft(cursor.getInt(cursor.getColumnIndex(ChatBotTable.CHATBOT_ISLEFT))==1 ? true : false);
            messages.add(message);

            cursor.moveToNext();
        }
        cursor.close();
        return messages;
    }
}
