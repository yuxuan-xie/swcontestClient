package com.fdu.swcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Bundle;

import com.fdu.swcontest.Hooks.AbstractHook;
import com.fdu.swcontest.Util.SWContentObserver;
import com.fdu.swcontest.Util.SWlog;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SWlog.d("MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentObserver contentObserver = new SWContentObserver(this);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(AbstractHook.uri_test, true, contentObserver);
        
    }
}
