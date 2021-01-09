package com.fronzii.swcontest.Hooks;

import android.app.AppOpsManager;

import com.fronzii.swcontest.Util.SWlog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fronzii.swcontest.Main.TAG;

public class HookSetMode extends AbstractHook {
    public Class<?> c1, c2, c3, c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        try {
            className = "";
            methodName = "setMode";
        } catch (Exception e) {
            SWlog.e(TAG, "fail to find xxx.xxx.xxx", e);
            return;
        }
        XC_MethodHook xc_methodHook1 = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int arg = (int)param.args[0];
                // The code of those OPSTRs was obtained by invoking AppOpsManager.strOpTpOp(),
                // which is also noted as @testapi and was called via reflection during testing
                if(arg == 14 || arg == 16 || arg == 20){
                    methodId = 8006001;
                }
                else{
                    methodId = 8006;
                }
                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        XC_MethodHook xc_methodHook2 = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                String arg = (String)param.args[0];

                if(arg.equals(AppOpsManager.OPSTR_READ_SMS) || arg.equals(AppOpsManager.OPSTR_RECEIVE_SMS)
                        || arg.equals(AppOpsManager.OPSTR_SEND_SMS)){
                    methodId = 8006001;
                }
                else{
                    methodId = 8006;
                }

                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };


        SWlog.d("Handling:[8006]");
        XposedHelpers.findAndHookMethod(AppOpsManager.class, methodName, int.class, int.class, String.class, int.class, xc_methodHook1);
        XposedHelpers.findAndHookMethod(AppOpsManager.class, methodName, String.class, int.class, String.class, int.class, xc_methodHook2);
        SWlog.d("Registered:[8006]");
    }
}
