package com.fronzii.swcontest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;

import com.fronzii.swcontest.Collect.Collect;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage{
    public static Context AnzhiContext;
    public static Context WechatContext;
    public static String TAG = "SWContest";
    public static Collect collect = new Collect();

    public void initconfig(){
        
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

    }
}
