package com.j1j2.pifalao.feature.home.offlinemodulehome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivityOfflinemodulehomeBinding;
import com.j1j2.pifalao.feature.home.offlinemodulehome.di.OfflineModuleHomeModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-5-11.
 */
@RequireBundler
public class OfflineModuleHomeActivity extends BaseActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks{

    ActivityOfflinemodulehomeBinding binding;

    @Arg
    Module module;
    @Arg
    ServicePoint servicePoint;

    @Inject
    ServicePointApi servicePointApi;

    UnReadInfoManager unReadInfoManager = null;

    private String callDialogTag = "CALLDIALOG";

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offlinemodulehome);
        binding.setModule(module);
        binding.setServicePoint(servicePoint);
        binding.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        queryModule(servicePoint.getServicePointId());
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);

        MainAplication.get(this).getAppComponent().plus(new OfflineModuleHomeModule()).inject(this);

    }

    public void queryModule(int servicePointId) {
        servicePointApi.queryServicePointServiceModules(servicePointId)
                .compose(this.<WebReturn<List<Module>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<Module>>() {
                    @Override
                    public void onWebReturnSucess(List<Module> modules) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Module module : modules) {
                            if (module.isSubscribed())
                                stringBuilder.append(module.getModuleName() + "、");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        stringBuilder.append("等");
                        binding.setServicePointModules(stringBuilder.toString());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);

        } else {

        }
    }

    public void showCallDialog() {
        showMessageDialogDuplicate(true, callDialogTag, "提示", "确认拨打： " + servicePoint.getMobile() + "？", "取消", "确定");
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.callBtn)
            showCallDialog();
        if (v == binding.individualBtn)
            navigate.navigateToIndividualCenter(this, null, false);
        if (v == binding.inBtn) {
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
