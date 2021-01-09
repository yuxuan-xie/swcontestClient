package com.fronzii.swcontest.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class SWQuery {
    private static SQLiteDatabase db;

    public SWQuery(Context context){
        if(db == null) {
            SWDBHelper swdbHelper = new SWDBHelper(context);
            db = swdbHelper.getWritableDatabase();
        }
    }


    public String getSequence(String packageName){
        String res = "";
        Cursor cursor = db.query(SWDBHelper.API_SEQUENCE, new String[]{"sequence"}, "packagename=?", new String[]{packageName}, null, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res = cursor.getString(0);
        }
        cursor.close();
        return res;
    }

    public Map<String, String> getAllSequence(){
        Map<String, String> res = new HashMap<>();
        Cursor cursor = db.query(SWDBHelper.API_SEQUENCE, new String[]{"packagename", "sequence"}, null, null, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            res.put(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return res;
    }

    public void setMal(String packageName, boolean isMal){
        boolean res = getMal(packageName);
        // If the app has already been marked malicious, then do nothing.
        if(!res) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("isMal", isMal ? 1 : 0);
            db.update(SWDBHelper.API_SEQUENCE, contentValues, "packagename=?", new String[]{packageName});
        }

    }

    public boolean getMal(String packageName){
        boolean res = false;
        Cursor cursor = db.query(SWDBHelper.API_SEQUENCE, new String[]{"isMal"}, "packagename=?", new String[]{packageName}, null, null, null, null);
        assert cursor!= null;
        while(cursor.moveToNext()){
            res = cursor.getInt(0)==1;
        }
        cursor.close();
        return res;
    }

    public void close(){
        db.close();
    }
}
