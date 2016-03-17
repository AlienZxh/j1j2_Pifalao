package com.j1j2.pifalao.feature.services;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.databinding.ActivityServicesBinding;
import com.j1j2.pifalao.feature.services.di.ServicesModule;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ServicesActivity extends BaseMapActivity implements ServicesAdapter.OnItemClickListener {

    ActivityServicesBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Inject
    ServicesViewModule servicesViewModule;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(ServicesActivity.this, R.layout.activity_services);
        binding.setSercivepoint(servicePoint);

    }

    @Override
    protected void initViews() {
        binding.moduleList.setLayoutManager(new GridLayoutManager(this, 3));
        ScaleInAnimator scaleInAnimator = new ScaleInAnimator();
        scaleInAnimator.setAddDuration(800);
        binding.moduleList.setItemAnimator(scaleInAnimator);
        binding.moduleList.setAdapter(servicesViewModule.getServicesAdapter());
        servicesViewModule.getServicesAdapter().setOnItemClickListener(this);
        servicesViewModule.queryServicePointServiceModules();

    }

    public void startSearchAnimate() {
        binding.rippleView.startRippleAnimation();
    }

    public void stopSearchAnimate() {
        binding.rippleView.stopRippleAnimation();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ServicesModule(this, servicePoint)).inject(this);
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
    public void onItemClickListener(View view, Module module) {
//        navigate.navigateToStoreStyleomeActivity(ServicesActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module);
        navigate.navigateToMainActivity(ServicesActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module);
    }

}
