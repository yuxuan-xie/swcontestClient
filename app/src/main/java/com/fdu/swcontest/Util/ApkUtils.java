package com.fdu.swcontest.Util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ApkUtils {

    public static class SWAppInfo{
        public Drawable icon;
        public String appName;
    }

    public static List<SWAppInfo> scan(PackageManager packageManager){
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
                appInfos.add(swAppInfo);
            }
        }catch(Exception e){
            SWlog.e(e);
        }
        return appInfos;
    }
}
