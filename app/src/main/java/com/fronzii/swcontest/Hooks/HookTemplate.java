package com.fronzii.swcontest.Hooks;

import com.fronzii.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fronzii.swcontest.Main.TAG;

public class HookTemplate extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    public String className = "";
    public String methodName = "";

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
        XposedHelpers.findAndHookMethod(hookclass, methodName,

                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                });
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classloader = classLoader;
    }
}
