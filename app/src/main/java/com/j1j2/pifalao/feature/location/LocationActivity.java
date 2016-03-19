package com.j1j2.pifalao.feature.location;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.j1j2.data.model.City;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.service.LocationService;
import com.j1j2.pifalao.databinding.ActivityLocationBinding;
import com.j1j2.pifalao.feature.location.di.LocationModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationViewModel.onCreate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationViewModel.onDestory();
        stopService(new Intent(this, LocationService.class));
    }

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
    public void onClick(View v) {
        navigate.navigateToCityActivity(this, null, true);
    }


    @Override
    public void onDistrictItemClickListener(View view, City city, int position) {

        if (position == 0)
            locationViewModel.queryNearByServicePoint();
        else
            locationViewModel.queryInDistrict(city);
    }

    @Override
    public void onItemClickListener(View view, ServicePoint servicePoint) {
        navigate.navigateToServicesActivity(LocationActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, servicePoint);
    }

    @Override
    public void onInfoClickListener(View view, ServicePoint servicePoint) {
        navigate.navigateToServicePointActivity(LocationActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, servicePoint);
    }


}
