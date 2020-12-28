package com.fdu.swcontest.Hooks;

import com.fdu.swcontest.Util.SWlog;

import java.util.ArrayList;
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
