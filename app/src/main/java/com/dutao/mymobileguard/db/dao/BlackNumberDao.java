package com.dutao.mymobileguard.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dutao.mymobileguard.bean.BlackNumberInfo;
import com.dutao.mymobileguard.db.helper.BlackNumberOpenHelper;

/**
 * Created by dutao on 2015/6/16.
 */
public class BlackNumberDao {
    private BlackNumberOpenHelper helper;

    public BlackNumberDao(Context context){
        helper = new BlackNumberOpenHelper(context,"callsafe.db",null,1);
    }

    /**
     * 插入一条数据
     * @param blackNumberInfo
     * @return
     */
    public boolean add(BlackNumberInfo blackNumberInfo){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number",blackNumberInfo.getNumber());
        contentValues.put("mode", blackNumberInfo.getMode());
        long rowid = database.insert("blackinfo", null, contentValues);
        if (rowid == -1l){
            return false;
        }
        return true;
    }

    /**
     * 删除一条数据
     * @param number
     * @return
     */
    public boolean delete(String number){
        SQLiteDatabase database = helper.getWritableDatabase();
        int rownumber = database.delete("blackinfo", "number=?", new String[]{number});
        if(rownumber == 0){
            return false;
        }
        return true;
    }
}
