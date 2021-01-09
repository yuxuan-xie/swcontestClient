package com.fronzii.swcontest.Hooks;

import android.content.ContextWrapper;
import android.content.IntentFilter;
import android.provider.Telephony;

import com.fronzii.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import static com.fronzii.swcontest.Main.TAG;

public class HookRegisterReceiver extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "registerReceiver";
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }
        XC_MethodHook xc_methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                methodId = 0;

                IntentFilter arg = (IntentFilter)param.args[1];
                if (arg != null) {
                    if (arg.hasAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION) || arg.hasAction(Telephony.Sms.Intents.SMS_DELIVER_ACTION)) {
                        methodId = 1017001;
                    } else {
                        methodId = 1017;
                    }

                    SWlog.d("methodId: " + methodId);
                    updateDB();
                }
            }
        };
        SWlog.d("Handling:[1017]");
//        XposedHelpers.findAndHookMethod(ContextWrapper.class, methodName, BroadcastReceiver.class, IntentFilter.class, xc_methodHook);
//        SWlog.d("1");
//        XposedHelpers.findAndHookMethod(ContextWrapper.class, methodName, BroadcastReceiver.class, IntentFilter.class, int.class, xc_methodHook);
//        SWlog.d("2");
//        XposedHelpers.findAndHookMethod(ContextWrapper.class, methodName, BroadcastReceiver.class, IntentFilter.class, String.class, Handler.class, int.class, xc_methodHook);
//        SWlog.d("3");
//        XposedHelpers.findAndHookMethod(ContextWrapper.class, methodName, BroadcastReceiver.class, IntentFilter.class, String.class, Handler.class, xc_methodHook);
        XposedBridge.hookAllMethods(ContextWrapper.class, methodName, xc_methodHook);
        SWlog.d("Registered:[1017]");
    }


}