package com.fdu.swcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


import com.fdu.swcontest.Hooks.AbstractHook;
import com.fdu.swcontest.Util.ApkUtils;
import com.fdu.swcontest.Util.SWContentObserver;
import com.fdu.swcontest.Util.SWlog;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SWlog.d("MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentObserver contentObserver = new SWContentObserver(this);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(AbstractHook.uri_sequence, true, contentObserver);


        List<ApkUtils.SWAppInfo> appInfos = ApkUtils.scan(getApplicationContext());
        String[] nItems = new String[appInfos.size()];
        for (int i = 0; i < appInfos.size(); i++)
        {
            ApkUtils.SWAppInfo appInfo = (ApkUtils.SWAppInfo)appInfos.get(i);
            nItems[i] = appInfo.appName;
        }

        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("choose app");
                builder.setItems(nItems, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        boolean isMal = appInfos.get(arg1).isMal;
                        String str_isMal = isMal? "Yes": "No";
                        Toast toast = Toast.makeText(getApplicationContext(), "app: " + nItems[arg1] + "\nisMal: " + str_isMal, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

                builder.create().show();
            }
        });

    }

}
