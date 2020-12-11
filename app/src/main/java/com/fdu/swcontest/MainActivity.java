package com.fdu.swcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.Bundle;

import com.fdu.swcontest.Hooks.AbstractHook;
import com.fdu.swcontest.Util.SWContentObserver;
import com.fdu.swcontest.Util.SWlog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SWlog.d("MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentObserver contentObserver = new SWContentObserver(this);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(AbstractHook.uri_sequence, true, contentObserver);
    }

}
