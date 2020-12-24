package com.fdu.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Browser;
import android.provider.ContactsContract;

import com.fdu.swcontest.Util.SWlog;

import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookRegisterContentObserver extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "registerContentObserver";
        } catch (Exception e) {
            SWlog.e(TAG, "fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String path = ((Uri) param.args[0]).toString();

                if (!path.contains("content://com.fdu.swcontentprovider/api")) {
                    if (path.contains("content://sms")) {
                        methodId = 15007001;
                    } else if (path.contains("content://call_log") || path.contains("vnd.android.cursor.dir/contact_directories") || path.contains("com.android.contacts") || path.contains("content://icc/adn")) {
                        methodId = 15007003;
                    } else {
                        methodId = 15007;
                    }

                    SWlog.d("methodId: " + methodId);
                    updateDB();
                }
            }
        };

        SWlog.d("Handling:[15007]");
        XposedHelpers.findAndHookMethod(ContentResolver.class, methodName, Uri.class, boolean.class, ContentObserver.class, xc_methodHook);
        SWlog.d("Registered:[15007]");
    }
}
