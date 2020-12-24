package com.fdu.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookPutInt extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "putInt";
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String arg = (String)param.args[1];

                if(arg.equals(Settings.System.SCREEN_BRIGHTNESS)){
                    methodId = 24004;
                    SWlog.d("methodId: " + methodId);
                    updateDB();
                }
            }
        };

        SWlog.d("Handling:[24004]");
        XposedHelpers.findAndHookMethod(Settings.System.class, methodName, ContentResolver.class, String.class, int.class, xc_methodHook);
        XposedHelpers.findAndHookMethod(Settings.Secure.class, methodName, ContentResolver.class, String.class, int.class, xc_methodHook);
        XposedHelpers.findAndHookMethod(Settings.Global.class, methodName, ContentResolver.class, String.class, int.class, xc_methodHook);
        SWlog.d("Registered:[24004]");
    }

}
