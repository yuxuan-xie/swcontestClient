package com.fdu.swcontest.Hooks;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookAddView extends AbstractHook{
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

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                methodId = 0;
                ViewGroup.LayoutParams arg = (ViewGroup.LayoutParams)param.args[1];



                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[7005]");
        XposedHelpers.findAndHookMethod(ViewGroup.class, methodName, View.class, ViewGroup.LayoutParams.class, xc_methodHook);
        SWlog.d("Registered:[7005]");
    }

}
