package com.fdu.swcontest.Hooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookStartActivity extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "android.app.Activity";
            methodName = "startActivity";
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xcMethod = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: 1001xxx");
            }
        };

        SWlog.d("Handling:[1001]");
        XposedHelpers.findAndHookMethod(hookclass, methodName, Intent.class, xcMethod);
        XposedHelpers.findAndHookMethod(hookclass, methodName, Intent.class, Bundle.class, xcMethod);
        SWlog.d("Registered:[1001]");
    }


    @Override
    public void setContext(Context context) {this.hookContext = context; this.classloader = context.getClassLoader();}

    @Override
    public void setMethodId(int methodId) {this.methodId = methodId;}

    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }

}