package com.fdu.swcontest.Util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;

import com.fdu.swcontest.Hooks.AbstractHook;

public class SWContentObserver extends ContentObserver {
    private Context myContext;

    public SWContentObserver(Context context) {
        super(new Handler());
        myContext = context;
    }

    public void onChange(final boolean selfChange){
        ContentResolver contentResolver = this.myContext.getContentResolver();
        Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"_id", "packagename", "sequence", "sequence_length"}, null, null, null);
        assert cursor != null;
        SWlog.d("**************DB CHANGED**************");
        while(cursor.moveToNext()){
            SWlog.d("_id:" + cursor.getString(0) + " packagename:" + cursor.getString(1) + " sequence:" + cursor.getString(2) + " sequence_length:" + cursor.getInt(3));
        }
        cursor.close();
    }
}
