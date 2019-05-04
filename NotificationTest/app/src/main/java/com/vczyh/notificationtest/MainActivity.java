package com.vczyh.notificationtest;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendNotice = findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:
                sendNotification();
            default:
                break;
        }
    }

    // 申请VIBRATE权限
    private boolean vibratePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(this, new String[]{Manifest.permission.VIBRATE}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendNotification();
                } else {
                    Toast.makeText(this, "你拒绝了授权，不能使用振动提醒功能", Toast.LENGTH_SHORT)
                            .show();
                }
        }
    }

    private void sendNotification() {
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 大于等于8.0
            // 设置通道唯一ID
            String channelId = "chat_msg";
            // 设置通道名
            String channelName = "聊天信息";
            int important = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel =
                    new NotificationChannel(channelId, channelName, important);

            channel.enableVibration(true);
            // 设置没振动 小米手机目前测试不能自定义振动时长
            channel.setVibrationPattern(new long[]{1000, 1000});

            // 只有在锁屏下有效 目前没有测试
            channel.enableLights(true);
            channel.setLightColor(Color.WHITE);

            channel.setSound(Uri.fromFile(
                    new File("/system/media/audio/ringtones/Country.ogg")), null);

            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle("大于8")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    // 设置通知点击事件
                    .setContentIntent(pi)
                    // 点击这条通知后 取消在通知栏的显示 还可以使用manager.cancel(int)实现相同效果
//                            .setAutoCancel(true)
                    // 设置声音
//                            .setSound(Uri.fromFile(
//                                    new File("/system/media/audio/ringtones/Country.ogg")))
//                    .setVibrate(new long[]{0, 1000, 1000, 1000})
                    // 设置LED灯
//                    .setLights(Color.GREEN,1000,1000)
                    .build();
            manager.notify(1, notification);
        } else {
            // 8.0以下可以使用
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("小于8")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pi)
//                            .setAutoCancel(true)
                    // 设置声音
//                            .setSound(Uri.fromFile(
//                                    new File("/system/media/audio/ringtones/Country.ogg")))
                    .setVibrate(new long[]{0, 1000, 1000, 1000})
                    .build();
            manager.notify(1, notification);
        }
    }
}
