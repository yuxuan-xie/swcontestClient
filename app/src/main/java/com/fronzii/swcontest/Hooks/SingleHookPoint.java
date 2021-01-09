package com.fronzii.swcontest.Hooks;

import com.fronzii.swcontest.Util.SWlog;

import java.util.List;
import java.util.Map;

public class SingleHookPoint {
    public Map<String, List<String>> api;

    public void print(){
        api.forEach((key, value)->{
            value.forEach((method)->{
                SWlog.d("method:" + method);
            });
        });
    }

}
