package com.fronzii.swcontest;

import android.app.Application;
import android.content.Context;

import com.fronzii.swcontest.Collect.Collect;
import com.fronzii.swcontest.Hooks.AbstractHook;
import com.fronzii.swcontest.Hooks.HookUpdateDisplay;
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

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if(loadPackageParam.processName.contains("com.goapk.market")){
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            SWlog.d("******Hooking Anzhi******");
                            ClassLoader cl = ((Context)param.args[0]).getClassLoader();
                            if(AnzhiContext == null) {
                                AnzhiContext = (Context)param.thisObject;
                            }
                        }
                    });
        }
        else if(loadPackageParam.processName.contains("com.tencent.mm")){
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            SWlog.d("******Hooking Wechat******");
                            ClassLoader cl = ((Context)param.args[0]).getClassLoader();
                            if(WechatContext == null) {
                                WechatContext = (Context) param.thisObject;
                            }

                            AbstractHook myHook;

                            myHook = new HookUpdateDisplay();
                            myHook.setClassLoader(cl);
                            myHook.doHook();
                        }
                    });
        }
    }
}
