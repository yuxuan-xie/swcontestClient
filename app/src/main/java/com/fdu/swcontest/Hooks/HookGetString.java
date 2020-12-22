package com.fdu.swcontest.Hooks;

import android.content.Context;
import android.provider.Settings;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookGetString extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() throws ClassNotFoundException {
        try {
            className = "";
            methodName = "getString";
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String arg = (String)param.args[0];

            }
        };

        SWlog.d("Handling:[20011]");
        XposedHelpers.findAndHookMethod(Settings.System.class, methodName, String.class, xc_methodHook);
        XposedHelpers.findAndHookMethod(Settings.Secure.class, methodName, String.class, xc_methodHook);
        XposedHelpers.findAndHookMethod(Settings.Global.class, methodName, String.class, xc_methodHook);
        SWlog.d("Registered:[20011]");

        XC_MethodHook xcMethodHook2 = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }
        };

        SWlog.d("Handling:[20012]");
        XposedHelpers.findAndHookMethod(Class.forName("android.os.SystemProperties"), "get", String.class, xcMethodHook2);
        XposedHelpers.findAndHookMethod(Class.forName("android.os.SystemProperties"), "get", String.class, String.class, xcMethodHook2);
        SWlog.d("Registered:[20012]");
    }
}
