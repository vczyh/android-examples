package com.vczyh.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LocationClient mLocationClient;

    private MyLocationListener myLocationListener = new MyLocationListener();

    private TextView positionText;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myLocationListener);
        positionText = findViewById(R.id.position_text_view);
        mapView = findViewById(R.id.map_view);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat
                .checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        // 设置信息刷新时间
        option.setScanSpan(2000);
        // 设置定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置是否需要地址信息
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            // 设置子图初始经纬度
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            //设置地图缩放
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData myLocationData = new MyLocationData.Builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        baiduMap.setMyLocationData(myLocationData);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            // 获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            // 获取经纬度坐标类型，以LocationClientOption设置的为准
            String coorType = location.getCoorType();
            // 获取定位类型、定位错误码返回码
            int errorCode = location.getLocType();

            StringBuilder sb = new StringBuilder();
            sb
                    .append("维度：").append(latitude).append("\n")
                    .append("经度：").append(longitude).append("\n")
                    .append("国家：").append(location.getCountry()).append("\n")
                    .append("省：").append(location.getProvince()).append("\n")
                    .append("市：").append(location.getCity()).append("\n")
                    .append("区：").append(location.getDistrict()).append("\n")
                    .append("街道：").append(location.getStreet()).append("\n")
                    .append("定位方式：");
            if (errorCode == BDLocation.TypeGpsLocation) {
                sb.append("GPS");
            } else if (errorCode == BDLocation.TypeNetWorkLocation) {
                sb.append("网络");
            }
            Log.d(TAG, "onReceiveLocation: position is " + sb.toString());
            runOnUiThread(() -> {
                positionText.setText(sb);
            });

            navigateTo(location);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
