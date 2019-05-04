package com.vczyh.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.file.FileStore;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate: " + this.toString());
        Log.d(TAG, "onCreate: task is " + getTaskId());

        setContentView(R.layout.first_layout);
        Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 设置Toast
//                Toast.makeText(FirstActivity.this, "You clicked Button 1",
//                        Toast.LENGTH_SHORT).show();

                // 销毁活动 相当于手机back按钮
//                finish();

                // 使用显式Intent启动其他活动
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                startActivity(intent);

                //使用隐式Intent启动其他活动
//                Intent intent = new Intent("com.vczyh.activitytest.ACTION_START");
//                intent.addCategory("com.vczyh.activitytest.MY_CATEGORY");
//                startActivity(intent);

                //使用隐式Intent启动浏览器 android内置活动
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                startActivity(intent);

                //使用隐式Intent启动系统拨号界面 android内置活动
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:10086"));
//                startActivity(intent);

                // 使用Intent向其他活动传递数据
//                String data = "Hello SecondActivity";
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                intent.putExtra("extra_data", data);
//                startActivity(intent);

                // 销毁活动时，返回一个结果给上个活动
//                Intent intent =new Intent(FirstActivity.this, SecondActivity.class);
//                startActivityForResult(intent, 1);

                // 再开启一个FirstActivity 测试活动的启动模式：Standard模式
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);


            }
        });
    }

    // 设置菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 设置菜单响应事件


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    // 接受返回的活动的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1 :
            if (resultCode == RESULT_OK) {
                 String returnedData = data.getStringExtra("data_return");
                Log.d(TAG, "onActivityResult: " + returnedData);
            }
            break;
            default:
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }
}
