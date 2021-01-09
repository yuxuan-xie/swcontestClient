package com.fronzii.swcontest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Bundle;
import android.widget.ListView;


import com.fronzii.swcontest.Hooks.AbstractHook;
import com.fronzii.swcontest.Util.ApkUtils;
import com.fronzii.swcontest.Util.SWAdapter;
import com.fronzii.swcontest.Util.SWContentObserver;
import com.fronzii.swcontest.Util.SWlog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SWAdapter swAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ApkUtils.SWAppInfo> appInfos;
    private ApkUtils.SWAppInfo[] appArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SWlog.d("MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentObserver contentObserver = new SWContentObserver(this);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(AbstractHook.uri_sequence, true, contentObserver);

        appInfos = ApkUtils.scan(getApplicationContext());
        appArray = new ApkUtils.SWAppInfo[appInfos.size()];
        appInfos.toArray(appArray);

        swAdapter = new SWAdapter(this, R.layout.list_view, appArray);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        listView = (ListView)findViewById(R.id.sw_list_view);
        listView.setAdapter(swAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);


//        ApkUtils.SWAppInfo[] appArray = (ApkUtils.SWAppInfo[])appInfos.toArray();
//        String[] nItems = new String[appInfos.size()];
//        for (int i = 0; i < appInfos.size(); i++)
//        {
//            ApkUtils.SWAppInfo appInfo = appInfos.get(i);
//            nItems[i] = appInfo.appName;
//        }

//
//        Button btn1 = (Button) findViewById(R.id.button1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//                builder.setTitle("choose app");
//                builder.setItems(nItems, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        boolean isMal = appInfos.get(arg1).isMal;
//                        String str_isMal = isMal? "Yes": "No";
//                        Toast toast = Toast.makeText(getApplicationContext(), "app: " + nItems[arg1] + "\nisMal: " + str_isMal, Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                });
//
//                builder.create().show();
//            }
//        });
    }

    @Override
    public void onRefresh() {
        appInfos = ApkUtils.scan(getApplicationContext());
        appArray = new ApkUtils.SWAppInfo[appInfos.size()];
        appInfos.toArray(appArray);

        swAdapter = new SWAdapter(this, R.layout.list_view, appArray);
        listView.setAdapter(swAdapter);
        
        swipeRefreshLayout.setRefreshing(false);
    }
}
