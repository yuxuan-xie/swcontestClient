package com.fdu.swcontest.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SWDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fdusw.db";

    public static final String API_SEQUENCE = "api";
    public static final String TEST = "test";

    private static final int DATABASE_VERSION = 1;

    public SWDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + API_SEQUENCE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " packagename TEXT," + " sequence TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TEST + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " comment TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
