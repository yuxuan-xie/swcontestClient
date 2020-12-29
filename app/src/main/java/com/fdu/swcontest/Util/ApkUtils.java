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
        public boolean isMal;
    }

    public static List<SWAppInfo> scan(Context context){
        PackageManager packageManager = context.getPackageManager();
        List<SWAppInfo> appInfos = new ArrayList<SWAppInfo>();
        try{
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
            for(PackageInfo packageInfo : packageInfos){
                SWAppInfo swAppInfo = new SWAppInfo();

                swAppInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();

                if(packageInfo.applicationInfo.loadIcon(packageManager) == null){
                    continue;
                }
                swAppInfo.icon = packageInfo.applicationInfo.loadIcon(packageManager);
                swAppInfo.isMal = new SWQuery(context).getMal(packageInfo.packageName);

                appInfos.add(swAppInfo);
            }
        }catch(Exception e){
            SWlog.e(e);
        }
        return appInfos;
    }
}
