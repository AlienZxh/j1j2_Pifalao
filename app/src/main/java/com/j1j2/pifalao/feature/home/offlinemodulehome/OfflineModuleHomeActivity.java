package com.j1j2.pifalao.feature.home.offlinemodulehome;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationData;
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
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.databinding.ActivityOfflinemodulehomeBinding;
import com.j1j2.pifalao.feature.home.offlinemodulehome.di.OfflineModuleHomeModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-5-11.
 */
@RequireBundler
public class OfflineModuleHomeActivity extends BaseActivity implements View.OnClickListener {

    ActivityOfflinemodulehomeBinding binding;

    @Arg
    Module module;
    @Arg
    ServicePoint servicePoint;

    @Inject
    ServicePointApi servicePointApi;

    BDLocation location;

    UnReadInfoManager unReadInfoManager = null;

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
    public void onLocationEvent(LocationEvent event) {
        location = event.getLocation();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);

        } else {

        }
    }

    private void showCallDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认拨打： " + servicePoint.getMobile() + "？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PackageManager pkm = getPackageManager();
                        boolean has_permission = (PackageManager.PERMISSION_GRANTED
                                == pkm.checkPermission("android.permission.CALL_PHONE", "com.j1j2.pifalao"));
                        if (has_permission) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicePoint.getMobile()));
                            startActivity(intent);
                        } else {
                            toastor.showSingletonToast("没有拨打电话权限");
                        }
                    }
                })
                .create();
        messageDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.callBtn)
            showCallDialog();
        if (v == binding.individualBtn)
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigate.navigateToLogin(this, null, false);
        if (v == binding.inBtn) {
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
}
