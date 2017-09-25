package com.example.vivek.myapplication_chat;

/**
 * Created by vivek on 25/9/17.
 */
public class ChatBotTable {
    public static final String CHATDATA_TABLE			= "ChatData";
    public static final String CHATBOT_NAME		= "chatbotName";
    public static final String CHATBOT_ID		= "chatbotId";
    public static final String CHATBOT_MESSAGE			= "message";
    public static final String CHATBOT_EMOTION		= "emotion";
    public static final String CHATBOT_ISLEFT		= "isLeft";

    public static final String CHATDATA_CREATE_TABLE =
            "create table if not exists " + CHATDATA_TABLE + " (" +
                    "_id integer primary key autoincrement, " +
                    CHATBOT_NAME 		+ " text, " 	+
                    CHATBOT_ID 		    + " integer, " 	+
                    CHATBOT_MESSAGE  	+ " text,  " 	+
                    CHATBOT_EMOTION  	+ " text,  " 	+
                    CHATBOT_ISLEFT  	+ " integer " 	+
                    ");";
}
