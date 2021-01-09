package com.fronzii.swcontest.Hooks;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;

import com.fronzii.swcontest.Util.SWlog;

import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fronzii.swcontest.Main.TAG;

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
                    case DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN:
                        methodId = 1001004;
                        break;
                    case Intent.ACTION_CALL:
                        methodId = 1001006;
                        break;
                    case Intent.ACTION_DIAL:
                        methodId = 1001013;
                        break;
                    case "com.android.packageinstaller.PackageInstallerActivity":
                        methodId = 100101601;
                        break;
                    case "android.provider.Telephony.ACTION_CHANGE_DEFAULT":
                        methodId = 100101603;
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