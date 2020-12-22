package com.fdu.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookDelete extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "delete";
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                String path = ((Uri)param.args[0]).getPath();
                methodId = 0;

                assert path != null;
                if(path.contains("sms")){
                    methodId = 15009001;
                }
                else if(path.contains("siminfo")){
                    methodId = 15009002;
                }
                else if(path.contains("com.android.contacts")){
                    methodId = 15009003;
                }
                else if(path.contains("bookmarks")){
                    methodId = 15009004;
                }
                else if(path.contains("media")){
                    methodId = 15009005;
                }
                else{
                    methodId = 15009;
                }

                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[15009]");
        XposedHelpers.findAndHookMethod(ContentResolver.class, methodName, Uri.class, Bundle.class, xc_methodHook);
        SWlog.d("Registered:[15009]");
    }

}
