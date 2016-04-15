package com.j1j2.pifalao.feature.catservicepoint;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.databinding.ActivityCatservicepointBinding;
import com.j1j2.pifalao.feature.catservicepoint.di.CatServicePointModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-29.
 */
@RequireBundler
public class CatServicePointActivity extends BaseMapActivity implements View.OnClickListener {

    ActivityCatservicepointBinding binding;

    @Arg
    int servicePointId;

    @Inject
    CatServicePointViewModel catServicePointViewModel;

    Marker servicepointMark;
    MarkerOptions servicepointMarkOptions;

    @Override
    protected void initMap() {
        getMapView().showZoomControls(false);
        getMapView().showScaleControl(true);

        LatLng point = new LatLng(28.175983, 113.023015);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 17.0f);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
    }

    @Override
    protected MapView getMapView() {
        return binding.mapView;
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_catservicepoint);
        binding.setCatServicePointViewModel(catServicePointViewModel);
    }

    @Override
    protected void initViews() {
        catServicePointViewModel.queryServicePoint(servicePointId);
    }

    public void addServicepointOverlay(ServicePoint servicePoint) {
        BitmapDescriptor markIcon = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_servicepoint);
        if (servicepointMark != null)
            servicepointMark.remove();

        servicepointMarkOptions = new MarkerOptions();
        servicepointMarkOptions.position(new LatLng(servicePoint.getLat(), servicePoint.getLng()));
        servicepointMarkOptions.draggable(false);
        servicepointMarkOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
        servicepointMarkOptions.icon(markIcon);
        servicepointMark = (Marker) mBaiduMap.addOverlay(servicepointMarkOptions);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(servicepointMark.getPosition(), 17.0f);
        mBaiduMap.setMapStatus(u);
        markIcon.recycle();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new CatServicePointModule(this)).inject(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEvent event) {
        BDLocation location = event.getLocation();
        if (isLocationSuccess(location)) {
            // 地图显示我的位置
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())// 定位精度
                    .direction(100)// GPS定位时方向角度,顺时针0-360
                    .latitude(location.getLatitude())// 百度纬度坐标
                    .longitude(location.getLongitude())// 百度经度坐标
                    .speed(location.getSpeed())// GPS定位时速度
                    .satellitesNum(location.getSatelliteNumber())// GPS定位时卫星数目
                    .build();
            if (mBaiduMap != null)
                mBaiduMap.setMyLocationData(locData);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
