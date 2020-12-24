package com.fdu.swcontest.Hooks;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;

import com.fdu.swcontest.Util.SWlog;

import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

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
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: " + methodId);

                updateDB();
            }
        };



//        methodName = "addPreferredActivity";
//        methodId = 7001;
//        SWlog.d("Handling:[7001]");
//        XposedBridge.hookAllMethods(hookclass, methodName, xc_methodHook);
//        SWlog.d("Registered:[7001]");
//
//        methodName = "clearPackagePreferredActivities";
//        methodId = 7002;
//        SWlog.d("Handling:[7002]");
//        XposedBridge.hookAllMethods(hookclass, methodName, xc_methodHook);
//        SWlog.d("Registered:[7002]");

        methodName = "setApplicationEnabledSetting";
        methodId = 7003;
        SWlog.d("Handling:[7003]");
        XposedBridge.hookAllMethods(hookclass, methodName, xc_methodHook);
        SWlog.d("Registered:[7003]");


        xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                PackageManager pm = hookContext.getPackageManager();
                ComponentName arg0 = (ComponentName) param.args[0];
                ComponentName temp = Objects.requireNonNull(pm.getLaunchIntentForPackage(arg0.getPackageName())).getComponent();
                if (temp != null && temp.getClassName().equals(arg0.getClassName())) {
                    if ((int) param.args[1] == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                        methodId = 7004001;
                    } else {
                        methodId = 7004;
                    }

                    SWlog.d("methodId: " + methodId);
                    updateDB();
                }
            }
        };
        methodName = "setComponentEnabledSetting";
        methodId = 7004;
        SWlog.d("Handling:[7004]");
        XposedBridge.hookAllMethods(hookclass, methodName, xc_methodHook);
        SWlog.d("Registered:[7004]");

    }
}