package com.fdu.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;

import com.fdu.swcontest.Util.SWlog;

import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookSendBroadcast extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "sendBroadcast";
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Intent args = (Intent)param.args[0];
                methodId = 0;
                if ("com.android.launcher.action.INSTALL_SHORTCUT".equals(Objects.requireNonNull(args.getAction()))) {
                    methodId = 1003007;
                } else {
                    methodId = 1003;
                }

                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[1003]");
        XposedHelpers.findAndHookMethod(ContextWrapper.class, methodName, Intent.class, String.class, xc_methodHook);
        XposedHelpers.findAndHookMethod(ContextWrapper.class, methodName, Intent.class, xc_methodHook);
        SWlog.d("Registered:[1003]");
    }

}