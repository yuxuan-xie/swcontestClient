package com.fronzii.swcontest;

import android.app.Application;
import android.content.Context;

import com.fronzii.swcontest.Hooks.AbstractHook;
import com.fronzii.swcontest.Hooks.HookAbstractMethods;
import com.fronzii.swcontest.Hooks.HookAddView;
import com.fronzii.swcontest.Hooks.HookDelete;
import com.fronzii.swcontest.Hooks.HookGetString;
import com.fronzii.swcontest.Hooks.HookInsert;
import com.fronzii.swcontest.Hooks.HookPutInt;
import com.fronzii.swcontest.Hooks.HookQuery;
import com.fronzii.swcontest.Hooks.HookRegisterContentObserver;
import com.fronzii.swcontest.Hooks.HookRegisterReceiver;
import com.fronzii.swcontest.Hooks.HookSendBroadcast;
import com.fronzii.swcontest.Hooks.HookStartActivity;
import com.fronzii.swcontest.Hooks.HookTemplate;
import com.fronzii.swcontest.Hooks.SingleHookPoint;
import com.fronzii.swcontest.Util.JsonUtils;
import com.fronzii.swcontest.Util.SWlog;

import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {
    public static String TAG = "SWContest";
    public static SingleHookPoint singleHookPoint;
    public static SWencoding sWencoding;

    public void initconfig() {
        // TODO
    }


    public static class SWencoding{
        public Map<String, String> encoding;
    }

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        SWlog.d("Start Hooking");
        if (!loadPackageParam.packageName.equals("com.fronzii.swcontest")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                            SWlog.d("PackageName:" + loadPackageParam.packageName);
                            Context context = (Context) param.args[0];
                            try {
                                String hookJsonStr = JsonUtils.getJson("singleHookPoint.json");
                                singleHookPoint = JsonUtils.jsonToObject(hookJsonStr, SingleHookPoint.class);
//                                singleHookPoint.print();
                                String encoding = JsonUtils.getJson("encoding.json");
                                sWencoding = JsonUtils.jsonToObject(encoding, SWencoding.class);

                            }catch(Exception e){
                                SWlog.e(TAG, e);
                            }

                            AbstractHook myHook;

                            for(Map.Entry<String, List<String>> entry : singleHookPoint.api.entrySet()){
                                for(String each : entry.getValue()){
                                    myHook = new HookTemplate();
                                    myHook.setContext(context);
                                    myHook.setMethodId(Integer.parseInt(entry.getKey()));
                                    myHook.setSignature(each);
                                    myHook.doHook();
                                }
                            }

                            myHook = new HookAbstractMethods();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookStartActivity();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookSendBroadcast();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookRegisterReceiver();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookAddView();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookRegisterContentObserver();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookDelete();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookQuery();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookInsert();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookGetString();
                            myHook.setContext(context);
                            myHook.doHook();

                            myHook = new HookPutInt();
                            myHook.setContext(context);
                            myHook.doHook();

                        }
                    });
        }
    }
}
