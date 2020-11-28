package com.fronzii.swcontest.Hooks;

import android.content.Context;

import com.fronzii.swcontest.Collect.Collect;
import com.fronzii.swcontest.Util.SWlog;

import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fronzii.swcontest.Main.TAG;
import static com.fronzii.swcontest.Main.collect;

public class HookUpdateDisplay extends AbstractHook{
    public Class<?> c1, c2,c3,c4, fParam, pParam;
    public String className = "";
    public String methodName = "";

    @Override
    public void doHook() {
        try {
            methodName = "onPreferenceTreeClick";
            hookclass = classloader.loadClass("com.tencent.mm.plugin.setting.ui.setting" +
                    ".SettingsAboutMicroMsgUI");
            fParam = classloader.loadClass("com.tencent.mm.ui.base.preference.f");
            pParam = classloader.loadClass("com.tencent.mm.ui.base.preference.Preference");

        } catch (Exception e) {
            SWlog.e(TAG, "寻找xxx.xxx.xxx报错", e);
            return;
        }
        XposedHelpers.findAndHookMethod(hookclass, methodName,
                fParam, pParam,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        collect.collectStackTrace();
                    }
                });
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classloader = classLoader;
    }
}

