package com.fdu.swcontest.Util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fdu.swcontest.Hooks.AbstractHook;

import java.util.HashMap;
import java.util.Map;

public class SWQuery {
    private ContentResolver contentResolver;

    public SWQuery(){
        this.contentResolver = null;
    }

    public SWQuery(Context context){this.contentResolver = context.getContentResolver();}

    public void setContext(Context context) {
        this.contentResolver = context.getContentResolver();
    }

    public String getSequence(Context context, String packageName){
        String res = "";
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"sequence"}, "packagename=?", new String[]{packageName}, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res = cursor.getString(0);
        }
        cursor.close();
        return res;
    }

    public String getSequence(String packageName){
        String res = "";
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
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"packagename", "sequence"}, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res.put(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return res;
    }

    public Map<String, String> getAllSequence(){
        Map<String, String> res = new HashMap<>();
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"packagename", "sequence"}, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res.put(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return res;
    }

    public void setMal(Context context, String packageName, boolean isMal){
        ContentValues contentValues = new ContentValues();
        contentValues.put("isMal", isMal?1:0);
        contentResolver.update(AbstractHook.uri_sequence, contentValues, "packagename=?", new String[]{packageName});
    }

    public void setMal(String packageName, boolean isMal){
        ContentValues contentValues = new ContentValues();
        contentValues.put("isMal", isMal?1:0);
        contentResolver.update(AbstractHook.uri_sequence, contentValues, "packagename=?", new String[]{packageName});
    }

    public boolean getMal(Context context, String packageName){
        boolean res = false;
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"isMal"}, "packagename=?", new String[]{packageName}, null);
        assert cursor!= null;
        while(cursor.moveToNext()){
            res = cursor.getInt(0)==1;
        }
        cursor.close();
        return res;
    }

    public boolean getMal(String packageName){
        boolean res = false;
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"isMal"}, "packagename=?", new String[]{packageName}, null);
        assert cursor!= null;
        while(cursor.moveToNext()){
            res = cursor.getInt(0)==1;
        }
        cursor.close();
        return res;
    }
}
