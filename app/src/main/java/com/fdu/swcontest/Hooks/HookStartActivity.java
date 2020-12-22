package com.fdu.swcontest.Hooks;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.fdu.swcontest.Util.SWlog;

import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookStartActivity extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "android.app.Activity";
            methodName = "startActivity";
            hookclass = classloader.loadClass(className);
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xcMethod = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Intent args = (Intent)param.args[0];
                methodId = 0;
                switch(Objects.requireNonNull(args.getAction())){
                    case Intent.ACTION_SEND:
                        methodId = 1001001;
                        break;
                    case Intent.ACTION_WEB_SEARCH:
                        methodId = 1001002;
                        break;
                    case Intent.ACTION_EDIT:
                        methodId = 1001003;
                        break;
                    case DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN:
                        methodId = 1001004;
                        break;
                    case Intent.ACTION_ATTACH_DATA:
                        methodId = 1001005;
                        break;
                    case Intent.ACTION_CALL:
                        methodId = 1001006;
                        break;
                    case Intent.ACTION_DELETE:
                        methodId = 1001007;
                        break;
                    case Intent.ACTION_SEARCH:
                        methodId = 1001008;
                        break;
                    case Intent.ACTION_PICK:
                        methodId = 1001009;
                        break;
                    case Intent.ACTION_PICK_ACTIVITY:
                        methodId = 1001010;
                        break;
                    case Intent.ACTION_SENDTO:
                        methodId = 1001011;
                        break;
                    case Intent.ACTION_GET_CONTENT:
                        methodId = 1001012;
                        break;
                    case Intent.ACTION_DIAL:
                        methodId = 1001013;
                        break;
                    case Intent.ACTION_MAIN:
                        methodId = 1001014;
                        break;
                    case Intent.ACTION_INSERT:
                        methodId = 1001015;
                        break;
                    case Intent.ACTION_VIEW:
                        methodId = 1001016;
                        break;
                    default:
                        methodId = 1001;
                        break;
                }
                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[1001]");
        XposedHelpers.findAndHookMethod(hookclass, methodName, Intent.class, xcMethod);
        XposedHelpers.findAndHookMethod(hookclass, methodName, Intent.class, Bundle.class, xcMethod);
        SWlog.d("Registered:[1001]");
    }

}