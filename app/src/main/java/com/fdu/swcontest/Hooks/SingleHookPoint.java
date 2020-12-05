package com.fdu.swcontest.Hooks;

import com.fdu.swcontest.Util.SWlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleHookPoint {
    public Map<String, List<String>> api;
//    private Map<String, Map<String, String>> Action_bindService;
//    private Map<String, Map<String, String>> Action_finishActivity;
//    private Map<String, Map<String, String>> Action_getAppTasks;
//    private Map<String, Map<String, String>> Action_getProcessMemoryInfo;
//    private Map<String, Map<String, String>> Action_getProcessesInErrorState;
//    private Map<String, Map<String, String>> Action_killBackgroundProcesses;
//    private Map<String, Map<String, String>> Action_moveTaskToFront;
//    private Map<String, Map<String, String>> Action_navigateUpTo;
//    private Map<String, Map<String, String>> Action_setRequestedOrientation;
//    private Map<String, Map<String, String>> Action_startActivities;
//    private Map<String, Map<String, String>> Action_unbindService;
//    private Map<String, Map<String, String>> Action_unregisterReceiver;
//    private Map<String, Map<String, String>> Action_updateConfiguration;
//    private Map<String, Map<String, String>> Action_createAndManageUser;
//    private Map<String, Map<String, String>> Action_removeActiveAdmin;
//    private Map<String, Map<String, String>> Action_resetPassword;
//    private Map<String, Map<String, String>> Action_DevicePolicyManager_lockNow;
//    private Map<String, Map<String, String>> Action_wipeData;
//    private Map<String, Map<String, String>> Action_StatusBarManagerServiceSetIcon;
//    private Map<String, Map<String, String>> Action_requestLocationUpdates;





    public void print(){
        api.forEach((key, value)->{
            value.forEach((method)->{
                SWlog.d("method:" + method);
            });
        });
    }

}
