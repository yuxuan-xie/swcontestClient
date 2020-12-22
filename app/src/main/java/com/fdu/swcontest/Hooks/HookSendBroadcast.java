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
                Intent args = (Intent)param.args[0];
                methodId = 0;
                switch(Objects.requireNonNull(args.getAction())){
                    case Intent.ACTION_PACKAGE_CHANGED:
                        methodId = 1003001;
                        break;
                    case Intent.ACTION_PACKAGE_REMOVED:
                        methodId = 1003002;
                        break;
                    case Intent.ACTION_TIME_TICK:
                        methodId = 1003003;
                        break;
                    case Intent.ACTION_BATTERY_CHANGED:
                        methodId = 1003005;
                        break;
                    case Intent.ACTION_PACKAGE_ADDED:
                        methodId = 1003006;
                        break;
                    case Intent.ACTION_CREATE_SHORTCUT:
                        methodId = 1003007;
                        break;
                    default:
                        methodId = 1003;
                        break;
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