package com.fdu.swcontest.Util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fdu.swcontest.Hooks.AbstractHook;
import com.fdu.swcontest.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class SWQuery {
    private final SQLiteDatabase db;

    public SWQuery(Context context){
        SWDBHelper swdbHelper = new SWDBHelper(context);
        this.db = swdbHelper.getWritableDatabase();
    }


    public String getSequence(String packageName){
        String res = "";
        Cursor cursor = this.db.query(SWDBHelper.API_SEQUENCE, new String[]{"sequence"}, "packagename=?", new String[]{packageName}, null, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res = cursor.getString(0);
        }
        cursor.close();
        return res;
    }

    public Map<String, String> getAllSequence(){
        Map<String, String> res = new HashMap<>();
        Cursor cursor = this.db.query(SWDBHelper.API_SEQUENCE, new String[]{"packagename", "sequence"}, null, null, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res.put(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return res;
    }

    public void setMal(String packageName, boolean isMal){
        ContentValues contentValues = new ContentValues();
        contentValues.put("isMal", isMal?1:0);
        this.db.update(SWDBHelper.API_SEQUENCE, contentValues, "packagename=?", new String[]{packageName});

    }

    public boolean getMal(String packageName){
        boolean res = false;
        Cursor cursor = this.db.query(SWDBHelper.API_SEQUENCE, new String[]{"isMal"}, "packagename=?", new String[]{packageName}, null, null, null, null);
        assert cursor!= null;
        while(cursor.moveToNext()){
            res = cursor.getInt(0)==1;
        }
        cursor.close();
        return res;
    }
}
