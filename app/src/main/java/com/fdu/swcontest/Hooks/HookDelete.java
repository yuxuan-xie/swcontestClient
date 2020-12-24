package com.fdu.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookDelete extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() throws ClassNotFoundException {
        try {
            className = "";
            methodName = "delete";
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                String path = ((Uri) param.args[0]).toString();
                methodId = 0;

                if (!path.contains("content://com.fdu.swcontentprovider/api")) {
                    if (path.contains("content://sms")) {
                        methodId = 15009001;
                    } else if (path.contains("content://call_log") || path.contains("vnd.android.cursor.dir/contact_directories") || path.contains("com.android.contacts") || path.contains("content://icc/adn")) {
                        methodId = 15009003;
                    } else {
                        methodId = 15009;
                    }

                    SWlog.d("methodId: " + methodId);
                    updateDB();
                }
            }
        };

        SWlog.d("Handling:[15009]");
//        XposedHelpers.findAndHookMethod(Class.forName("android.content.ContentResolver"), "delete", Uri.class, Bundle.class, xc_methodHook);
        XposedBridge.hookAllMethods(ContentResolver.class, "delete", xc_methodHook);
        SWlog.d("Registered:[15009]");
    }

}
