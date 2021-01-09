package com.fronzii.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.fronzii.swcontest.Main;

import java.util.Objects;

public abstract class AbstractHook {
    ClassLoader classloader;
    Class<?> hookclass;
    Context hookContext;
    int methodId;
    String signature;
    public static String Splitter = ",";
//    public static Uri uri_test = Uri.parse("content://com.fronzii.swcontentprovider/test");
    public static Uri uri_sequence = Uri.parse("content://com.fronzii.swcontentprovider/api");

    public abstract void doHook() throws ClassNotFoundException;
    public void setContext(Context context) {this.hookContext = context; this.classloader = context.getClassLoader();}
    public void setMethodId(int methodId) {this.methodId = methodId;}
    public void setSignature(String signature) {this.signature = signature;}
    public void updateDB(){
        String packageName = hookContext.getPackageName();
        String sequence = "";
        int sequence_length = 0;

        ContentResolver contentResolver = hookContext.getContentResolver();
        Cursor cursor = contentResolver.query(uri_sequence, new String[]{"_id", "packagename", "sequence", "sequence_length"}, "packagename=?",
                new String[]{packageName}, null);
        assert cursor != null;
        if(cursor.isAfterLast()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("packagename", packageName);
            contentValues.put("sequence", "");
            contentValues.put("sequence_length", 0);
            contentResolver.insert(uri_sequence, contentValues);

        }else{
            cursor.moveToNext();
            sequence = cursor.getString(2);
            sequence_length = cursor.getInt(3);
            cursor.close();
        }
        ContentValues contentValues = new ContentValues();
        methodId = Integer.parseInt(Objects.requireNonNull(Main.sWencoding.encoding.get(String.valueOf(methodId))));
        if(sequence.equals("")){
            contentValues.put("sequence", methodId);
        }else{
            contentValues.put("sequence", sequence + Splitter + methodId);
        }
        contentValues.put("sequence_length", sequence_length + 1);
        contentResolver.update(uri_sequence, contentValues, "packagename=?", new String[]{packageName});
    }
}
