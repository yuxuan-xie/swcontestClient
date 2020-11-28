package com.fronzii.swcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static boolean isLaunch = false;
    static boolean isScanning = false;
    static List<String> appName = new ArrayList<String>();
    static Map<String, Context> contextMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isLaunch = true;
        getAppList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getAppList(){
        PackageManager packageManager = this.getPackageManager();
        List<PackageInfo> appListInfo = packageManager.getInstalledPackages(0);
        for(PackageInfo each : appListInfo){
            appName.add(each.packageName);
        }
        for(String each : appName){
            contextMap.put(each, null);
        }
    }
}
