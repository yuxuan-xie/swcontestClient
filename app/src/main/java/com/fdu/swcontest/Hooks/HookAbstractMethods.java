package com.fdu.swcontest.Hooks;

import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookAbstractMethods extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            PackageManager pm = this.hookContext.getPackageManager();
            hookclass = pm.getClass();
            methodName = "addPreferredActivity";
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        SWlog.d("Handling:[7001]");

        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: 7001");
            }
        });
        SWlog.d("Registered:[7001]");

        methodName = "clearPackagePreferredActivities";
        SWlog.d("Handling:[7002]");
        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: 7002");
            }
        });
        SWlog.d("Registered:[7002]");

        methodName = "setApplicationEnabledSetting";
        SWlog.d("Handling:[7003]");
        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: 7003");
            }
        });
        SWlog.d("Registered:[7003]");

        methodName = "setComponentEnabledSetting";
        SWlog.d("Handling:[7004]");
        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: 7004");
            }
        });
        SWlog.d("Registered:[7004]");

    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classloader = classLoader;
    }

    @Override
    public void setContext(Context context) {this.hookContext = context;}

    @Override
    public void setMethodId(int methodId) {this.methodId = methodId;}

    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }

}