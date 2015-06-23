package com.dutao.mymobileguard.db.dao;

import java.io.File;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Title: <br>
 * Description: <br>
 * Copyright: Copyright (c) 2007<br>
 * 
 * @author dutao
 * @version 1.0
 * @date 2015-5-28
 */
public class VirusDao
{
    /**
     * 判断病毒库文件是否存在
     * 
     * @return  是否存在
     */
    public static Boolean isVirusExists()
    {
        File file = new File(
                "/data/data/com.itheima.mobileguard/files/antivirus.db");
        return file.exists() && file.length() > 0;
    }
    
    /**
     * 获取当前病毒库版本号
     * 
     * @return  当前病毒库版本号
     */
    public static String getVirusVersion()
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(
                "/data/data/com.dutao.mymobileguard/files/antivirus.db", null, SQLiteDatabase.OPEN_READONLY);
        String version = "0";
//        Cursor cursor = sqLiteDatabase.rawQuery("select subcnt from version ", null);
//        while(cursor.moveToNext()){
//            version = cursor.getString(0);
//        }
//        cursor.close();
//        sqLiteDatabase.close();
        return version;
    }
    
    /**
     * 获取当前病毒库版本号
     * 
     * @return  当前病毒库版本号
     */
    public static void updateVirusVersion(String newVirusVersion)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(
                "/data/data/com.itheima.mobileguard/files/antivirus.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("subcnt", newVirusVersion);
        sqLiteDatabase.update("version", values, null, null);
        sqLiteDatabase.close();
    }
}
