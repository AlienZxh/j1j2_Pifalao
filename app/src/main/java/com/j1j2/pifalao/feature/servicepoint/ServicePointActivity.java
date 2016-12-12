package com.j1j2.pifalao.feature.servicepoint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

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

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ServicePointActivity extends BaseActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks {

    ActivityServicepointBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Inject
    ServicePointViewModel servicePointViewModel;

    @Inject
    UserRelativePreference userRelativePreference;


    private String callDialogTag = "CALLDIALOG";

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


    private void showCallDialog() {
        showMessageDialogDuplicate(true, callDialogTag, "提示", "确认拨打： " + servicePoint.getMobile() + "？", "取消", "确定");
    }

    @Override
    public void onClick(View v) {
        if (v == binding.callBtn) {
            showCallDialog();
        }
        if (v == binding.navigationBtn) {
            BDLocation location = MainAplication.get(this).getLocationService().getSuccessLocation();
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
            navigate.navigateToServicesActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(v, 0, 0, 0, 0), true);
        }
        if (v == binding.backBtn) {
            onBackPressed();
        }
    }

    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        super.onDialogPositiveClick(fragmentTag);
        if (fragmentTag.equals(callDialogTag)) {
            callServicePoint();
        }
    }

    @SuppressWarnings("all")
    @AfterPermissionGranted(RC_CALLPHONE_PERM)
    private void callServicePoint() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicePoint.getMobile()));
            startActivity(intent);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "联系服务点，缺少拨打电话权限。",
                    RC_CALLPHONE_PERM, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == RC_CALLPHONE_PERM && EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "联系服务点，缺少拨打电话权限。")
                    .setTitle("缺少权限")
                    .setPositiveButton("设置")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.

        }
    }
}
