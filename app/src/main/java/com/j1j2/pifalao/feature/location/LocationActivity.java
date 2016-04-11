package com.j1j2.pifalao.feature.location;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.j1j2.data.model.City;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.event.FinishLocationActivityEvent;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityLocationBinding;
import com.j1j2.pifalao.feature.location.di.LocationModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-9.
 */
@RequireBundler
public class LocationActivity extends BaseMapActivity implements View.OnClickListener, LocationServicePointAdapter.OnItemClickListener, LocationDistrictAdapter.OnDistrictItemClickListener {

    ActivityLocationBinding binding;

    @Arg
    City city;

    @Inject
    LocationViewModel locationViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    private OverlayManager servicepointOverlayManager = null;
    private List<OverlayOptions> overlayOptionses = null;

    private boolean isFirst = true;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(LocationActivity.this, R.layout.activity_location);
        binding.setLocationViewModel(locationViewModel);
        binding.districtList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.districtList.setAdapter(locationViewModel.getLocationDistrictAdapter());
        binding.servicepointList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this).sizeResId(R.dimen.height_1px).margin(AutoUtils.getPercentWidthSize(30), 0)
                .colorResId(R.color.colorGrayF0F0F0).build());
        binding.servicepointList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.servicepointList.setAdapter(locationViewModel.getLocationServicePointAdapter());
        locationViewModel.getLocationDistrictAdapter().setOnDistrictItemClickListener(this);
        locationViewModel.getLocationServicePointAdapter().setOnItemClickListener(this);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new LocationModule(this, city)).inject(this);
    }

    @Override
    protected void initViews() {

    }

    public void addServicePointOverlay(List<ServicePoint> servicePoints) {
        if (overlayOptionses.size() > 0) {
            servicepointOverlayManager.removeFromMap();
            overlayOptionses.clear();
        }
        BitmapDescriptor markIcon = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_servicepoint);
        int i = 0;
        for (ServicePoint servicePoint : servicePoints) {
            LatLng point = new LatLng(servicePoint.getLat(), servicePoint.getLng());
            MarkerOptions mark = new MarkerOptions()//
                    .position(point)//
                    .icon(markIcon)//
                    .zIndex(i)//
                    .draggable(false)//
                    .animateType(MarkerOptions.MarkerAnimateType.drop);
            // 把数据的shopId保存到队列
            overlayOptionses.add(mark);
            i++;
        }
        servicepointOverlayManager.addToMap();
        markIcon.recycle();
    }

    public LatLng getNortheast(BDLocation location) {
        LatLng mapPoint = new LatLng(location.getLatitude(), location.getLongitude());
        Point centerPoint = mBaiduMap.getProjection().toScreenLocation(mapPoint);
        float pixels = mBaiduMap.getProjection().metersToEquatorPixels(30000);
        Point northeastPoint = new Point((int) (centerPoint.x + pixels), (int) (centerPoint.y + pixels));
        return mBaiduMap.getProjection().fromScreenLocation(northeastPoint);
    }

    public LatLng getSouthwest(BDLocation location) {
        LatLng mapPoint = new LatLng(location.getLatitude(), location.getLongitude());
        Point centerPoint = mBaiduMap.getProjection().toScreenLocation(mapPoint);
        float pixels = mBaiduMap.getProjection().metersToEquatorPixels(30000);
        Point southwestPoint = new Point((int) (centerPoint.x - pixels), (int) (centerPoint.y - pixels));
        return mBaiduMap.getProjection().fromScreenLocation(southwestPoint);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initMap() {
        getMapView().showZoomControls(false);
        getMapView().showScaleControl(true);
        overlayOptionses = new ArrayList<>();
        servicepointOverlayManager = new ServicePointOverlayManager(mBaiduMap, overlayOptionses);

        LatLng point = new LatLng(28.175983, 113.023015);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 14.0f);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层

    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEvent event) {
        BDLocation location = event.getLocation();
        locationViewModel.onCreate(location);
        moveToMyLocation(location);
    }

    public void moveToMyLocation(BDLocation location) {
        if (location == null)
            return;
        // 地图显示我的位置
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())// 定位精度
                .direction(100)// GPS定位时方向角度,顺时针0-360
                .latitude(location.getLatitude())// 百度纬度坐标
                .longitude(location.getLongitude())// 百度经度坐标
                .speed(location.getSpeed())// GPS定位时速度
                .satellitesNum(location.getSatelliteNumber())// GPS定位时卫星数目
                .build();
        if (mBaiduMap == null)
            return;
        if (isFirst) {
            isFirst = false;
            mBaiduMap.setMyLocationData(locData);
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 14.0f);
            mBaiduMap.setMapStatus(u);
        }
    }

    @Override
    protected MapView getMapView() {
        return binding.mapView;
    }

    @Override
    public void onClick(View v) {
        navigate.navigateToCityActivity(this, null, true);
    }


    @Override
    public void onDistrictItemClickListener(View view, City city, int position) {
        if (position == 0) {
            LocationEvent locationEvent = EventBus.getDefault().getStickyEvent(LocationEvent.class);
            if (locationEvent != null)
                locationViewModel.queryNearByServicePoint(locationEvent.getLocation());
        } else {
            LocationEvent locationEvent = EventBus.getDefault().getStickyEvent(LocationEvent.class);
            if (locationEvent != null)
                locationViewModel.queryInDistrict(locationEvent.getLocation(), city);
        }
    }

    @Override
    public void onItemClickListener(View view, ServicePoint servicePoint) {
        userRelativePreference.setSelectedServicePoint(servicePoint);
        navigate.navigateToServicesActivity(LocationActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), true, servicePoint);
    }

    @Override
    public void onInfoClickListener(View view, ServicePoint servicePoint) {
        navigate.navigateToServicePointActivity(LocationActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, servicePoint);
    }

    public class ServicePointOverlayManager extends OverlayManager {
        private List<OverlayOptions> overlayOptionses;

        public ServicePointOverlayManager(BaiduMap baiduMap, List<OverlayOptions> overlayOptionses) {
            super(baiduMap);
            this.overlayOptionses = overlayOptionses;
        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            return this.overlayOptionses;
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            return false;
        }

        @Override
        public boolean onPolylineClick(Polyline polyline) {
            return false;
        }
    }

    @Subscribe
    public void onFinishEvent(FinishLocationActivityEvent event) {
        finish();
    }

}
