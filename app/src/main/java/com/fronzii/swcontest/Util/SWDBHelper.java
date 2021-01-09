package com.fronzii.swcontest.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SWDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fronziisw.db";

    static final String API_SEQUENCE = "api";
    static final String TEST = "test";

    private static final int DATABASE_VERSION = 1;

    SWDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + API_SEQUENCE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " packagename TEXT," + " sequence TEXT," + " sequence_length INTEGER," + " isMal TINYINT" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TEST + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " comment TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
