package com.fdu.swcontest.Hooks;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;

import com.fdu.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookAbstractMethods extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            PackageManager pm = this.hookContext.getPackageManager();
            hookclass = pm.getClass();
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xcmethod = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: " + methodId);
                String packageName = hookContext.getPackageName();
                String sequence = "";
                int sequence_length = 0;

                ContentResolver contentResolver = hookContext.getContentResolver();
                Cursor cursor = contentResolver.query(uri_sequence, new String[]{"_id", "packagename", "sequence", "sequence_length"}, "packagename=?",
                        new String[]{packageName}, null);
                assert cursor != null;
                if(cursor.isAfterLast()) {
                    SWlog.d("packagename not in db");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("packagename", packageName);
                    contentValues.put("sequence", "");
                    contentValues.put("sequence_length", 0);
                    contentResolver.insert(uri_sequence, contentValues);

                }else{
                    SWlog.d("packagename in db");
                    cursor.moveToNext();
                    sequence = cursor.getString(2);
                    sequence_length = cursor.getInt(3);
                    cursor.close();
                }
                SWlog.d("start updating db");
                ContentValues contentValues = new ContentValues();
                if(sequence.equals("")){
                    contentValues.put("sequence", methodId);
                }else{
                    contentValues.put("sequence", sequence + SPLITTER + methodId);
                }
                contentValues.put("sequence_length", sequence_length + 1);
                contentResolver.update(uri_sequence, contentValues, "packagename=?", new String[]{packageName});
                SWlog.d("end updating db");
            }
        };



        methodName = "addPreferredActivity";
        methodId = 7001;
        SWlog.d("Handling:[7001]");
        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: " + methodId);
            }
        });
        SWlog.d("Registered:[7001]");

        methodName = "clearPackagePreferredActivities";
        methodId = 7002;
        SWlog.d("Handling:[7002]");
        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: " + methodId);
            }
        });
        SWlog.d("Registered:[7002]");

        methodName = "setApplicationEnabledSetting";
        methodId = 7003;
        SWlog.d("Handling:[7003]");
        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: " + methodId);
            }
        });
        SWlog.d("Registered:[7003]");

        methodName = "setComponentEnabledSetting";
        methodId = 7004;
        SWlog.d("Handling:[7004]");
        XposedBridge.hookAllMethods(hookclass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: " + methodId);
            }
        });
        SWlog.d("Registered:[7004]");

    }


    @Override
    public void setContext(Context context) {this.hookContext = context; this.classloader = context.getClassLoader();}

    @Override
    public void setMethodId(int methodId) {this.methodId = methodId;}

    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }

}