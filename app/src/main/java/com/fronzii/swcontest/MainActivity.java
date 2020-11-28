package com.fronzii.swcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static boolean launchFlag = false;
    static boolean scanFlag = false;
    static List<PackageInfo> appListInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        launchFlag = true;
        getAppList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getAppList(){
        PackageManager packageManager = this.getPackageManager();
        appListInfo = packageManager.getInstalledPackages(0);
    }
}
