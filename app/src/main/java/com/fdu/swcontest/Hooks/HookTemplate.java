package com.fdu.swcontest.Hooks;

import android.content.Context;

import com.fdu.swcontest.Util.ParseSignature;
import com.fdu.swcontest.Util.SWlog;

import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fdu.swcontest.Main.TAG;

public class HookTemplate extends AbstractHook {
    public Class<?> c1, c2, c3, c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() {
        XC_MethodHook methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                SWlog.d("methodId: " + methodId);
                // TODO update the sequence info to db
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        };

        try {
            SWlog.d("Handling:" + "[" + this.methodId + "]" + this.signature);
            ParseSignature.MethodX methodX = ParseSignature.getMethodX(this.signature);
            hookclass = classloader.loadClass(methodX.methodClass);
            int tmpLen = methodX.methodParams.size() + 1;
            Object[] parameterTypesAndCallback = new Object[tmpLen];
            for (int i = 0; i < tmpLen - 1; i++) {
                parameterTypesAndCallback[i] = methodX.methodParams.get(i);
            }
            parameterTypesAndCallback[tmpLen - 1] = methodHook;
            XposedHelpers.findAndHookMethod(hookclass, methodX.methodName, parameterTypesAndCallback);
            SWlog.d("Registered:" + "[" + this.methodId + "]" + this.signature);
        } catch (Exception e) {
            SWlog.e("Fail to find xxx.xxx.xxx", e);
        }
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classloader = classLoader;
    }

    @Override
    public void setContext(Context context) {
        this.hookContext = context;
    }

    @Override
    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }
}
