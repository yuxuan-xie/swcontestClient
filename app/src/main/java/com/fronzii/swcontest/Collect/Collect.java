package com.fronzii.swcontest.Collect;

import android.content.SharedPreferences;

import com.fronzii.swcontest.Util.SWlog;

import java.util.Arrays;

import static com.fronzii.swcontest.Main.TAG;

public class Collect {
    public void collectStackTrace(){
        Throwable throwable = new Throwable();
        StackTraceElement[] stack = throwable.getStackTrace();
        for(StackTraceElement each : stack){
            SWlog.d(TAG, each.getLineNumber() + ":" + each.getClassName() + '.' + each.getMethodName());
        }
    }
}
