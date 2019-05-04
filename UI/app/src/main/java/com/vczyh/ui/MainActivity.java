package com.vczyh.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.text_view);
        Button button = findViewById(R.id.button);
        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);

        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                // EditTest
//                String inputText = editText.getText().toString();
//                Toast.makeText(this, inputText, Toast.LENGTH_LONG).show();

                // ImageView
//                imageView.setImageResource(R.drawable.img_2);

                // ProgressBar
                // 显示消失
//                if (progressBar.getVisibility() == View.GONE) {
//                    progressBar.setVisibility(View.VISIBLE);
//                } else {
//                    progressBar.setVisibility(View.GONE);
//                }
                // 进度条当前进度
//                int progress = progressBar.getProgress();
//                progress += 10;
//                progressBar.setProgress(progress);

                // AlertDialog
//                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                dialog.setTitle("This is Dialog");
//                dialog.setMessage("Something important");
//                dialog.setCancelable(false);  // 默认为true：可以点击提示框之外的界面关闭提示框
//                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // TODO: 2019/2/1
//                    }
//                });
//                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // TODO: 2019/2/1
//                    }
//                });
//                dialog.show();

                // ProgressDialog
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("This is a ProgressDialog");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
//                progressDialog.dismiss();  // 关闭对话框
                progressDialog.show();
                break;
            default:
        }
    }
}
