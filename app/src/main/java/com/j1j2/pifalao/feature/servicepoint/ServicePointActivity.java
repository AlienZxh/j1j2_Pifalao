package com.j1j2.pifalao.feature.servicepoint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.FinishLocationActivityEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityServicepointBinding;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ServicePointActivity extends BaseActivity implements View.OnClickListener {

    ActivityServicepointBinding binding;

    @Arg
    ServicePoint servicePoint;
    @Arg
    BDLocation location;

    @Inject
    ServicePointViewModel servicePointViewModel;

    @Inject
    UserRelativePreference userRelativePreference;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(ServicePointActivity.this, R.layout.activity_servicepoint);
        binding.setServicePointViewModel(servicePointViewModel);
    }

    @Override
    protected void initViews() {
        servicePointViewModel.queryModule();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ServicePointModule(this, servicePoint)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.callBtn) {
            PackageManager pkm = this.getPackageManager();
            boolean has_permission = (PackageManager.PERMISSION_GRANTED
                    == pkm.checkPermission("android.permission.CALL_PHONE", "com.j1j2.pifalao"));
            if (has_permission) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicePoint.getMobile()));
                startActivity(intent);
            } else {
                toastor.showSingletonToast("没有拨打电话权限");
            }
        }
        if (v == binding.navigationBtn) {
            if (location == null) {
                toastor.showSingletonToast("定位失败");
                return;
            }
            NaviParaOption naviParaOption = new NaviParaOption()
                    .startPoint(new LatLng(location.getLatitude(), location.getLongitude()))
                    .endPoint(new LatLng(servicePoint.getLat(), servicePoint.getLng()));
            BaiduMapNavigation.setSupportWebNavi(true);
            BaiduMapNavigation.openBaiduMapNavi(naviParaOption, this);

        }
        if (v == binding.inBtn) {
            EventBus.getDefault().post(new FinishLocationActivityEvent());
            userRelativePreference.setSelectedServicePoint(servicePoint);
            userRelativePreference.setShowDeliveryArea(true);
            userRelativePreference.setShowLocation(true);
            navigate.navigateToServicesActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(v, 0, 0, 0, 0), true, servicePoint);
        }
        if (v == binding.backBtn) {
            onBackPressed();
        }
    }
}
