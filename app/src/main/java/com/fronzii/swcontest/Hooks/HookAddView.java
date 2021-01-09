package com.fronzii.swcontest.Hooks;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fronzii.swcontest.Util.SWlog;

import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.fronzii.swcontest.Main.TAG;

public class HookAddView extends AbstractHook{
    public Class<?> c1, c2,c3,c4;
    private String className = "";
    private String methodName = "";

    @Override
    public void doHook() throws ClassNotFoundException {
        try {
            className = "";
            methodName = "addView";
        } catch (Exception e) {
            SWlog.e(TAG,"fail to find xxx.xxx.xxx", e);
            return;
        }

        XC_MethodHook xc_methodHook = new XC_MethodHook() {

            private boolean checkflags(WindowManager.LayoutParams attrs){
                List[] list = new List[]{
                        Arrays.asList(0,1,8),
                        Arrays.asList(0,1,2,3),
                        Arrays.asList(0,1,4,5),
                        Arrays.asList(0),
                        Arrays.asList(0,4,8,0xc),
                        Arrays.asList(0,6),
                        Arrays.asList(0,1)
                };
                int i = 0;
                int flags = attrs.flags;
                int width = attrs.width;
                int height = attrs.height;
                do{
                    int result = flags%0x10;
                    if(!list[i].contains(result))
                    {
                        return false;
                    }
                    flags = flags>>4;
                    i++;
                }while(flags > 0 && i <= 6);

                if(width == ViewGroup.LayoutParams.WRAP_CONTENT && height == ViewGroup.LayoutParams.WRAP_CONTENT && attrs.flags%0x10 == 8)
                    return false;
                return true;
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                methodId = 0;
                WindowManager.LayoutParams arg = (WindowManager.LayoutParams) param.args[1];
                if(arg.type == 2002 || arg.type == 2010 || arg.type == 2038){
                    if(checkflags(arg)){
                        methodId = 7005004;
                    }
                    else{
                        methodId = 7005;
                    }
                }
                else{
                    methodId = 7005;
                }
                SWlog.d("methodId: " + methodId);
                updateDB();
            }
        };

        SWlog.d("Handling:[7005]");
        XposedHelpers.findAndHookMethod(Class.forName("android.view.WindowManagerImpl"), methodName, View.class, ViewGroup.LayoutParams.class, xc_methodHook);
        SWlog.d("Registered:[7005]");
    }

}
