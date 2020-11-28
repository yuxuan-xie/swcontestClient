package com.fronzii.swcontest;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;

import com.fronzii.swcontest.Collect.Collect;
import com.fronzii.swcontest.Util.SWlog;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage{
    public static Context AnzhiContext;
    public static Context WechatContext;
    public static String TAG = "SWContest";
    public static Collect collect = new Collect();

    public void initconfig(){
        // TODO
    }

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if(MainActivity.isLaunch){
            if(MainActivity.appName.contains(loadPackageParam.processName)){
                XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                ClassLoader cl =((Context)param.args[0]).getClassLoader();
                                SWlog.d("PackageName:" + loadPackageParam.processName);

                            }
                        });
            }
        }
    }
}
