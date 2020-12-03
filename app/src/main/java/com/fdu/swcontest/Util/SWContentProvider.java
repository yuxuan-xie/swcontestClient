package com.fdu.swcontest.Util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SWContentProvider extends ContentProvider {
    private Context context;
    private static final String AUTHORITY = "com.fdu.swcontentprovider";
    SWDBHelper myDBHelper = null;
    SQLiteDatabase db = null;

    private static final int MATCH_CODE = 100;

    public static final int Sequence_Code = 1;
    public static final int Test_Code = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "api", Sequence_Code);
        uriMatcher.addURI(AUTHORITY, "test", Test_Code);
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        myDBHelper = new SWDBHelper(getContext());
        db = myDBHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return db.query(getTableName(uri), strings, s, strings1, null, null, s1, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        db.insert(getTableName(uri), null, contentValues);
        context.getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (uriMatcher.match(uri)) {
            case Sequence_Code:
                tableName = SWDBHelper.API_SEQUENCE;
                break;
            case Test_Code:
                tableName = SWDBHelper.TEST;
                break;
        }
        return tableName;
    }
}
