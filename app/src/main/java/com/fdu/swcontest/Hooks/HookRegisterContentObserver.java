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
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG, "fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String path = ((Uri)param.args[0]).getPath();
                assert path != null;
                if(path.contains("sms")){
                    methodId = 15007001;
                }
                else if(path.contains("siminfo")){
                    methodId = 15007002;
                }
                else if(path.contains("com.android.contacts")){
                    methodId = 15007003;
                }
                else if(path.contains("bookmarks")){
                    methodId = 15007004;
                }
                else if(path.contains("media")){
                    methodId = 15007005;
                }
                else{
                    methodId = 15007;
                }

                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[15007]");
        XposedHelpers.findAndHookMethod(ContentResolver.class, methodName, Uri.class, Boolean.class, ContentObserver.class, xc_methodHook);
        SWlog.d("Registered:[15007]");
    }
}
