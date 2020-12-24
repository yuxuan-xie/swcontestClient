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
        } catch (Exception e) {
            SWlog.e(TAG, "fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                String path = ((Uri) param.args[0]).toString();
//                methodId = 0;
//                这里千万不能将methodId初始化为0，
//                经过艰苦的调试后发现，当hook函数在应用程序的上下文中发起query时仍然会被Xposed拦截。
//                最重要的是，由于上下文相同，两次hook变量会被重复利用，需要十分小心！！！
                if (!path.contains("content://com.fdu.swcontentprovider/api")) {
                    if (path.contains("content://sms")) {
                        methodId = 15010001;
                    } else if (path.contains("content://call_log") || path.contains("vnd.android.cursor.dir/contact_directories") || path.contains("com.android.contacts") || path.contains("content://icc/adn")) {
                        methodId = 15010003;
                    } else {
                        methodId = 15010;
                    }
                    SWlog.d("methodId: " + methodId);
                    updateDB();
                }
            }
        };

        SWlog.d("Handling:[15010]");
        XposedHelpers.findAndHookMethod(ContentResolver.class, methodName, Uri.class, String[].class, Bundle.class, CancellationSignal.class, xc_methodHook);
//        XposedHelpers.findAndHookMethod(ContentResolver.class, methodName, Uri.class, String[].class, String.class, String[].class, String.class, CancellationSignal.class, xc_methodHook);
        SWlog.d("Registered:[15010]");
    }
}