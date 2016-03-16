package com.j1j2.pifalao.app.base;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

/**
 * Created by alienzxh on 16-3-12.
 */
public abstract class BaseMapActivity extends BaseActivity {
    protected abstract void initMap();

    protected abstract MapView getMapView();

    protected BaiduMap mBaiduMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaiduMap = getMapView().getMap();
        if (null != getMapView())
            initMap();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (null != mBaiduMap)
            mBaiduMap.setMyLocationEnabled(false);
        if (null != getMapView())
            getMapView().onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (null != getMapView())
            getMapView().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (null != getMapView())
            getMapView().onPause();
    }
}
