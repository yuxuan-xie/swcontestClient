package com.fdu.swcontest.Hooks;

import android.content.Context;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;
import static com.fdu.swcontest.Main.singleHookPoint;

public class AbandonedHookTemplate extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "";
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }
        XposedHelpers.findAndHookMethod(hookclass, methodName, boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                });
    }

//    @Override
//    public void setClassLoader(ClassLoader classLoader) {
//        this.classloader = classLoader;
//    }

    @Override
    public void setContext(Context context) {this.hookContext = context;}

    @Override
    public void setMethodId(int methodId) {this.methodId = methodId;}

    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }

}