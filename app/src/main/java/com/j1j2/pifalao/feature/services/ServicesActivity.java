package com.j1j2.pifalao.feature.services;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.j1j2.common.util.QRCodeUtils;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityServicesBinding;
import com.j1j2.pifalao.databinding.ViewQrcodeBinding;
import com.j1j2.pifalao.feature.services.di.ServicesModule;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ServicesActivity extends BaseMapActivity implements ServicesAdapter.OnItemClickListener, View.OnClickListener, OnDismissListener {

    ActivityServicesBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Inject
    ServicesViewModule servicesViewModule;

    @Inject
    UserRelativePreference userRelativePreference;

    Marker servicepointMark;
    MarkerOptions servicepointMarkOptions;

    ViewQrcodeBinding qrBinding;
    DialogPlus qrDialog;

    ObjectAnimator animator;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(ServicesActivity.this, R.layout.activity_services);
        binding.setSercivepoint(servicePoint);
    }

    @Override
    protected void initViews() {
        qrBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_qrcode, null, false);
        qrDialog = DialogPlus.newDialog(this).setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setInAnimation(android.R.anim.fade_in)
                .setOutAnimation(android.R.anim.fade_out)
                .setContentHolder(new ViewHolder(qrBinding.getRoot()))
                .setContentHeight(AutoUtils.getPercentHeightSize(650))
                .setContentWidth(AutoUtils.getPercentHeightSize(650))
                .setOnDismissListener(this)
                .setContentBackgroundResource(R.color.colorWhite)
                .create();
        //_______________________________________________________________________________________
        binding.moduleList.setLayoutManager(new GridLayoutManager(this, 3));
        ScaleInAnimator scaleInAnimator = new ScaleInAnimator();
        scaleInAnimator.setAddDuration(800);
        binding.moduleList.setItemAnimator(scaleInAnimator);
        binding.moduleList.setAdapter(servicesViewModule.getServicesAdapter());
        servicesViewModule.getServicesAdapter().setOnItemClickListener(this);
        servicesViewModule.queryServicePointServiceModules();
        binding.setOnClick(this);
    }

    public void startSearchAnimate() {
//        binding.rippleView.startRippleAnimation();
        animator = ObjectAnimator.ofFloat(binding.radarImg, "rotation", 0f, 1800f);
        animator.setDuration(7500);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.start();
    }

    public void stopSearchAnimate() {
        binding.radarImg.postDelayed(new Runnable() {
            @Override
            public void run() {
//                binding.rippleView.stopRippleAnimation();
                animator.cancel();
                binding.radarImg.setVisibility(View.INVISIBLE);
                binding.radarBgImg.setVisibility(View.INVISIBLE);
            }
        }, 2700);

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

        addServicepointOverlay();
    }

    private void addServicepointOverlay() {
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


    public void setQRCode(final String str) {
        qrBinding.qrcodeImg.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "特权码已刷新", Toast.LENGTH_SHORT).show();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                Bitmap qr = QRCodeUtils.createQRCodeWithLogo(str, qrBinding.qrcodeImg.getHeight(), bitmap);
                qrBinding.qrcodeImg.setImageBitmap(qr);
            }
        });

    }

    public void showQRDialog() {
        servicesViewModule.queryQRCode();
        qrDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding.rippleView.isRippleAnimationRunning())
            stopSearchAnimate();
        if (animator.isRunning())
            animator.cancel();
    }

    @Override
    protected MapView getMapView() {
        return binding.mapView;
    }

    @Override
    public void onItemClickListener(View view, Module module) {
        navigate.navigateToMainActivity(ServicesActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.loginBtn) {
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigate.navigateToLogin(this, null, false);
        }
        if (v == binding.servicepoint) {
            if (null != userRelativePreference.getSelectedCity(null))
                navigate.navigateToLocationActivity(this, null, true, userRelativePreference.getSelectedCity(null));
            else
                navigate.navigateToCityActivity(this, null, true);
        }
        if (v == binding.dragView) {
            if (MainAplication.get(this).isLogin())
                showQRDialog();
            else
                navigate.navigateToLogin(this, null, false);
        }
    }

    @Override
    public void onDismiss(DialogPlus dialog) {
        servicesViewModule.stopQueryQRCode();
    }


}
