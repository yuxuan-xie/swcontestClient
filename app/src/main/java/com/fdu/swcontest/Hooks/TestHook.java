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
                            contentValues.put("_id", 2);
                            contentValues.put("comment", hookContext.getPackageName());
                            contentResolver.insert(uri_test, contentValues);

                            Cursor cursor = contentResolver.query(uri_test, new String[]{"_id", "comment"}, null, null, null);
                            assert cursor != null;
                            while (cursor.moveToNext()) {
                                SWlog.d("id:" + cursor.getInt(0) + " comment:" + cursor.getString(1));
                            }
                            cursor.close();
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
