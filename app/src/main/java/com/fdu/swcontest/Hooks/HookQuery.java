package com.fdu.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookQuery extends AbstractHook {
    public Class<?> c1, c2, c3, c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "query";
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG, "fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                String path = ((Uri) param.args[0]).getPath();
                methodId = 0;

                assert path != null;
                if (path.contains("sms")) {
                    methodId = 15010001;
                } else if (path.contains("siminfo")) {
                    methodId = 15010002;
                } else if (path.contains("com.android.contacts")) {
                    methodId = 15010003;
                } else if (path.contains("bookmarks")) {
                    methodId = 15010004;
                } else if (path.contains("media")) {
                    methodId = 15010005;
                } else {
                    methodId = 15010;
                }

                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[15010]");
        XposedHelpers.findAndHookMethod(ContentResolver.class, methodName, Uri.class, String[].class, Bundle.class, CancellationSignal.class, xc_methodHook);
        XposedHelpers.findAndHookMethod(ContentResolver.class, methodName, Uri.class, String[].class, String.class, String[].class, CancellationSignal.class, xc_methodHook);
        SWlog.d("Registered:[15010]");
    }
}