package com.fdu.swcontest.Hooks;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
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
            methodName = "addView";
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
//                if(arg.type == 2002 || arg.tyqpe == 2010 || arg.type == 2038){
//
//                }



                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[7005]");
        XposedBridge.hookAllMethods(ViewManager.class, methodName, xc_methodHook);
        SWlog.d("Registered:[7005]");
    }

}
