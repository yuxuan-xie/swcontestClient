package com.fronzii.swcontest.Hooks;

import android.content.ContentResolver;
import android.net.Uri;

import com.fronzii.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import static com.fronzii.swcontest.Main.TAG;

public class HookInsert extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() throws ClassNotFoundException {
        try {
            className = "";
            methodName = "insert";
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String path = ((Uri) param.args[0]).toString();
//                methodId = 0;

                if(!path.contains("content://com.fronzii.swcontentprovider/api")) {
                    if (path.contains("content://sms")) {
                        methodId = 15011001;
                    } else if (path.contains("content://call_log") || path.contains("vnd.android.cursor.dir/contact_directories") || path.contains("com.android.contacts") || path.contains("content://icc/adn")) {
                        methodId = 15011003;
                    } else {
                        methodId = 15011;
                    }
                    SWlog.d("methodId: " + methodId);
                    updateDB();
                }

            }
        };

        SWlog.d("Handling:[15011]");
        XposedBridge.hookAllMethods(ContentResolver.class, methodName, xc_methodHook);
        SWlog.d("Registered:[15011]");
    }

}
