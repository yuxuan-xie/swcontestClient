package com.fronzii.swcontest.Util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fronzii.swcontest.Hooks.AbstractHook;

import org.json.JSONException;

public class SWContentProvider extends ContentProvider {
    private Context context;
    private static final String AUTHORITY = "com.fronzii.swcontentprovider";
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

    /*
    * query
    * @param uri the uri to query
    * @param projection query columns
    * @param selection query condition
    * @param selectionArgs query condition Args
    * @param sortOrder
    * @return
    * */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return db.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder, null);
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

    /*
    * update
    * @param uri the uri to update
    * @param contentValues
    * @param selection query condition
    * @param selectionArgs condition args
    * */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        assert selectionArgs != null;
        assert contentValues != null;
        String packageName  = selectionArgs[0];
        int sequence_length = contentValues.getAsInteger("sequence_length");
        int lastUpdated = Integer.parseInt(contentValues.getAsString("sequence").split(AbstractHook.Splitter)[sequence_length - 1]);

        dispatchMethodId(lastUpdated, packageName);

        if(sequence_length >= 256){
            try {
                NetUtils.postJson(context, packageName);
            } catch (JSONException e) {
                SWlog.e(e);
            }
            contentValues.put("sequence", "");
            contentValues.put("sequence_length", 0);
            db.update(getTableName(uri), contentValues, selection, selectionArgs);
        }else {
            db.update(getTableName(uri), contentValues, selection, selectionArgs);
        }
        context.getContentResolver().notifyChange(uri, null);
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

    public void dispatchMethodId(int methodId, String packageName){
        // TODO
    }
}
