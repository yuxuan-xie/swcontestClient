package com.fdu.swcontest.Util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.widget.ListAdapter;

import com.fdu.swcontest.Hooks.AbstractHook;

import java.util.ArrayList;
import java.util.List;

public class ApkUtils {

    public static class SWAppInfo{
        public Drawable icon;
        public String appName;
        public Boolean isMal;
    }

    public static List<SWAppInfo> scan(Context context){
        PackageManager packageManager = context.getPackageManager();
        ContentResolver contentResolver = context.getContentResolver();
        List<SWAppInfo> appInfos = new ArrayList<SWAppInfo>();
        try{
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for(int i = 0; i < packageInfos.size(); i++){
                PackageInfo packageInfo = packageInfos.get(i);
                SWAppInfo swAppInfo = new SWAppInfo();
                swAppInfo.appName = packageInfo.packageName;
                if(packageInfo.applicationInfo.loadIcon(packageManager) == null){
                    continue;
                }
                swAppInfo.icon = packageInfo.applicationInfo.loadIcon(packageManager);
                Cursor cursor = contentResolver.query(AbstractHook.uri_sequence, new String[]{"isMal"},
                        "packagename=?", new String[]{packageInfo.packageName}, null);
                assert cursor != null;
                if(cursor.isAfterLast()){
                    swAppInfo.isMal = false;
                }else {
                    cursor.moveToNext();
                    swAppInfo.isMal = cursor.getInt(0) > 0;
                }
                cursor.close();
                appInfos.add(swAppInfo);
            }
        }catch(Exception e){
            SWlog.e(e);
        }
        return appInfos;
    }
}
