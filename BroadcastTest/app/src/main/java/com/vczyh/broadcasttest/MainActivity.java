package com.vczyh.broadcasttest;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private Button send;
    // 本地广播
    private LocalReceiver localReceiver;
    private IntentFilter localFilter;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 动态广播接收器，接受系统发送的网络变化广播
        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        // 本地广播
        localBroadcastManager = LocalBroadcastManager.getInstance(this);


        // 发送标准广播（异步方式，所有的应用同时接收到）
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent("com.vczyh.broadcattest.MY_BROADCAST");
                // 标准广播
//                sendBroadcast(intent);
                // 有序广播
//                sendOrderedBroadcast(intent, null);

                // 发送本地广播
                Intent intent = new Intent("com.vczyh.LOCAL");
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        // 接受本地广播
        localFilter = new IntentFilter();
        localFilter.addAction("com.vczyh.LOCAL");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, localFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        // 接收网路变化广播
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "network changes", Toast.LENGTH_SHORT).show();
            @SuppressLint("ServiceCast")
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT)
                    .show();
        }
    }

}
