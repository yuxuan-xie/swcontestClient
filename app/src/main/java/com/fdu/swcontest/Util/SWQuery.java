package com.fdu.swcontest.Util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.fdu.swcontest.Hooks.AbstractHook;

import java.util.HashMap;
import java.util.Map;

public class SWQuery {

    public String getSequence(Context context, String packageName){
        String res = "";
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"sequence"}, "packagename=?", new String[]{packageName}, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res = cursor.getString(0);
        }
        cursor.close();
        return res;
    }

    public Map<String, String> getAllSequence(Context context){
        Map<String, String> res = new HashMap<>();
        ContentResolver  contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"packagename", "sequence"}, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res.put(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return res;
    }
}
