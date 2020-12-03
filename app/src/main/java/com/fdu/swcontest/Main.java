package com.fdu.swcontest;

import android.app.Application;
import android.content.Context;

import com.fdu.swcontest.Hooks.AbstractHook;
import com.fdu.swcontest.Hooks.TestHook;
import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {
    public static String TAG = "SWContest";

    public void initconfig() {
        // TODO
    }


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        SWlog.d("Start Hooking");
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                        SWlog.d("PackageName:" + loadPackageParam.packageName);
                        Context context = (Context) param.args[0];

                        AbstractHook myHook;

                        myHook = new TestHook();
                        myHook.setClassLoader(cl);
                        myHook.setContext(context);
                        myHook.doHook();
                    }
                });
    }
}
