package com.dutao.mymobileguard.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dutao.mymobileguard.bean.ContactInfo;

/**
 * Title: 通过内容提供者查询手机通讯录信息<br>
 * Description: <br>
 * Copyright: Copyright (c) 2007<br>
 * @author dutao
 * @version 1.0 
 * @date 2015-6-10
 */
public class ContactInfoParser
{
    public static List<ContactInfo> getContactInfo(Context context){
        ContentResolver resolver = context.getContentResolver();
        List<ContactInfo> list = new ArrayList<ContactInfo>();
        // 1. 查询raw_contacts表，把联系人的id取出来
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        
        Cursor contactCursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        while(contactCursor.moveToNext()){
            ContactInfo info = new ContactInfo();
            String contactId = contactCursor.getString(0);
            if(contactId != null){
                info.setId(contactId);
                Cursor data = resolver.query(datauri, new String[]{"data1","mimetype"}, "raw_contact_id=?", new String[]{contactId}, null);
                while (data.moveToNext()) {
                    String data1 = data.getString(0);
                    String mimetype = data.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        System.out.println("姓名=" + data1);
                        info.setName(data1);
                    } else if ("vnd.android.cursor.item/email_v2"
                            .equals(mimetype)) {
                        System.out.println("邮箱=" + data1);
                        info.setEmail(data1);
                    } else if ("vnd.android.cursor.item/phone_v2"
                            .equals(mimetype)) {
                        System.out.println("电话=" + data1);
                        info.setPhone(data1);
                    } else if ("vnd.android.cursor.item/im".equals(mimetype)) {
                        System.out.println("QQ=" + data1);
                        info.setQq(data1);
                    }
                }
                list.add(info);
                data.close();
            }
        }
        contactCursor.close();
        return list;
    }
}
