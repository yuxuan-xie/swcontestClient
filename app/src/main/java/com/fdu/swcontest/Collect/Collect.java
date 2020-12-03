package com.fdu.swcontest.Collect;

import android.content.Context;

import com.fdu.swcontest.Util.SWlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collect {
    public static List<String> appName = new ArrayList<String>();
    public static Map<String, Context> contextMap = new HashMap<>();

    public static void logAppList(){
        for (String each: Collect.appName){
            SWlog.d(each);
        }
    }
}
