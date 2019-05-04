package com.vczyh.materialtest;

import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private List<Fruit> fruitList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;

    private Fruit[] fruits = {
            new Fruit("Apple", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550432839579&di=62c279f3d4c58802155dd7fc35381aec&imgtype=0&src=http%3A%2F%2Fpic5.photophoto.cn%2F20071111%2F0033034127930230_b.jpg"),
            new Fruit("Banana", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3624688194,866649456&fm=26&gp=0.jpg"),
            new Fruit("Grape", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551109076&di=a0e2601cf55367469cf7c8bcbfec2ac8&imgtype=jpg&er=1&src=http%3A%2F%2Ffile03.16sucai.com%2F2016%2F10%2F1100%2F16sucai_p20161010125_17d.JPG"),
            new Fruit("Strawberry", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551109154&di=73048a609daf1ed09e69ce27a7adee47&imgtype=jpg&er=1&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140711%2F0013026495535863_b.jpg")

    };

    private FruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.navigation);
        }
        // NavigationView 滑动菜单
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_friends);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        // FloatingAtionButton 悬浮按钮
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toastText("FAB clicked");
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toastText("Data restored");
                            }
                        }).show();
            }
        });

        // RecycleView
        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        // SwipeRefreshLayout下拉刷新
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        // 设置刷新进度条颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> refreshFruits());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                toastText("You clicked Backup");
                break;
            case R.id.delete:
                toastText("You clicked Delete");
                break;
            case R.id.settings:
                toastText("You clicked Settings");
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void toastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 20; i++) {
            int random = new Random().nextInt(fruits.length);
            fruitList.add(fruits[random]);
        }
    }

    private void refreshFruits() {
        new Thread(() -> {
//            try {
                // 下拉进度条停留俩秒 之后自动消失 防止刷新太快看不到进度条
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            runOnUiThread(() -> {
                initFruits();
                adapter.notifyDataSetChanged();
                // 表示刷新事件结束 并隐藏进度条
                swipeRefreshLayout.setRefreshing(false);
            });

        }).start();

    }
}
