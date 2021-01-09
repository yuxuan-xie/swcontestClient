package com.fronzii.swcontest.Util;

import android.util.Log;



public class SWlog {
    private static final boolean ENABLE = true;
    private static final String tag = "SWContest";

    public static void d(String msg){
        if(!ENABLE){
            return;
        }
        Log.d(tag, msg);
    }

    public static void d(String tag, String msg){
        if(!ENABLE){
            return;
        }
        Log.d(tag, msg);
    }

    public static void e(String msg){
        if(!ENABLE){
            return;
        }
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg){
        if(!ENABLE){
            return;
        }
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable t){
        if(!ENABLE){
            return;
        }
        Log.e(tag, msg, t);
    }

    public static void e(Throwable t){
        if(!ENABLE){
            return;
        }
        Log.e(tag,"", t);
    }

    public static void e(String tag, Throwable t){
        if(!ENABLE){
            return;
        }
        Log.e(tag, Log.getStackTraceString(t));
    }
}
