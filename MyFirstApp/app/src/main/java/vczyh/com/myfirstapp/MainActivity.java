package vczyh.com.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    Button but1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.t1);
        but1=findViewById(R.id.but1);
        but1.setOnClickListener(new L1());
    }

    class L1 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            t1.setText("你好，这个一个按钮事件响应");
        }
    }
}
