package com.fdu.swcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.fdu.swcontest.Collect.Collect;
import com.fdu.swcontest.Util.SWlog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static boolean isLaunch = false;
    public static boolean isScanning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Uri uri_test = Uri.parse("content://com.fdu.swcontentprovider/test");
        isLaunch = true;
        getAppList();
        SWlog.d("MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver resolver2 = getContentResolver();
        Cursor cursor = resolver2.query(uri_test, new String[]{"_id", "comment"}, null, null, null);
        assert cursor != null;
        while (cursor.moveToNext()) {
            SWlog.d("id:" + cursor.getInt(0) + "comment:" + cursor.getString(1));
        }
        cursor.close();
    }

    public void getAppList(){
        PackageManager packageManager = this.getPackageManager();
        List<PackageInfo> appListInfo = packageManager.getInstalledPackages(0);
        for(PackageInfo each : appListInfo){
            Collect.appName.add(each.packageName);
        }
        for(String each : Collect.appName){
            Collect.contextMap.put(each, null);
        }
    }
}
