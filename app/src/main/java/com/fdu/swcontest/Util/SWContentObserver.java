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
        Cursor cursor = contentResolver.query(AbstractHook.uri_test, new String[]{"_id", "comment"}, null, null, null);
        assert cursor != null;
        SWlog.d("**************DB CHANGED**************");
        while(cursor.moveToNext()){
            SWlog.d("id:" + cursor.getInt(0) + " comment:" + cursor.getString(1));
        }
        cursor.close();
    }
}
