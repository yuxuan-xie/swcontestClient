package com.fdu.swcontest.Hooks;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class TestHook extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "android.location.LocationManager";
            methodName = "getProviders";
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }
        XposedHelpers.findAndHookMethod(hookclass, methodName, boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            ContentResolver contentResolver = hookContext.getContentResolver();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("comment", String.valueOf(System.currentTimeMillis()));
                            contentResolver.insert(uri_test, contentValues);
                        }catch(Exception e){
                            SWlog.e(TAG, e);
                        }
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                });
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classloader = classLoader;
    }

    @Override
    public void setContext(Context context) {this.hookContext = context; };

}
