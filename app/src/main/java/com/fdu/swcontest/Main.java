package com.fdu.swcontest;

import android.app.Application;
import android.content.Context;

import com.fdu.swcontest.Hooks.AbstractHook;
import com.fdu.swcontest.Hooks.HookTemplate;
import com.fdu.swcontest.Hooks.SingleHookPoint;
import com.fdu.swcontest.Hooks.TestHook;
import com.fdu.swcontest.Util.JsonUtils;
import com.fdu.swcontest.Util.SWlog;

import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {
    public static String TAG = "SWContest";
    public static SingleHookPoint singleHookPoint;

    public void initconfig() {
        // TODO
    }


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        SWlog.d("Start Hooking");
        if (!loadPackageParam.packageName.equals("com.fronzii.swcontest")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                            SWlog.d("PackageName:" + loadPackageParam.packageName);
                            Context context = (Context) param.args[0];
                            try {
                                String hookJsonStr = JsonUtils.getJson("singleHookPoint.json");
                                singleHookPoint = JsonUtils.jsonToObject(hookJsonStr, SingleHookPoint.class);
                            }catch(Exception e){
                                SWlog.e(TAG, e);
                            }

                            AbstractHook myHook;

                            for(Map.Entry<String, List<String>> entry : singleHookPoint.api.entrySet()){
                                for(String each : entry.getValue()){
                                    myHook = new HookTemplate();
                                    myHook.setClassLoader(cl);
                                    myHook.setContext(context);
                                    myHook.setMethodId(Integer.parseInt(entry.getKey()));
                                    myHook.setSignature(each);
                                    myHook.doHook();
                                }

                            }
                        }
                    });
        }
    }
}
