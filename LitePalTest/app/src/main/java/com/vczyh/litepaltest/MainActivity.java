package com.vczyh.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建数据库
        Button createDatabase = findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

        // Create
        Button addData = findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("The Da Vinci Code");
                book.setAuthor("Dan Brown");
                book.setPages(454);
                book.setPrice(16.96);
                book.setPress("unknown");
                book.save();
            }
        });

        // Update
        Button updateData = findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Book book = new Book();
//                book.setName("The Lost Symbol");
//                book.setAuthor("Dan Brown");
//                book.setPages(510);
//                book.setPrice(19.95);
//                book.setPress("unknown");
//                book.save();
//                Log.d(TAG, "onClick: book instance is saved : " + book.isSaved());
//                book.setPrice(10.99);
//                book.save();

//                Book book = new Book();
//                book.setPrice(14.95);
//                book.setPress("Anchor");
//                book.updateAll("name =? and author =?", "The Lost Symbol", "Dan Brown");

                Book book = new Book();
                book.setToDefault("pages");
                book.updateAll();
            }
        });

        // Delete
        Button deleteData = findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 第一种调用已存储对象的delete()方法
                // 已存储对象指的是 已经通过save()保存的对象 或者查询出来的对象

                //第二种
                LitePal.deleteAll(Book.class, "price < ?", "15");
            }
        });

        // Query
        // https://github.com/LitePalFramework/LitePal
        Button queryData = findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = LitePal.findAll(Book.class);
                for (Book book : books) {
                    Log.d(TAG, "onClick: book name is " + book.getName());
                    Log.d(TAG, "onClick: book author is " + book.getAuthor());
                    Log.d(TAG, "onClick: book pages is " + book.getPress());
                    Log.d(TAG, "onClick: book price is " + book.getPrice());
                    Log.d(TAG, "onClick: book press is " + book.getPress());
                }

            }
        });
    }
}
