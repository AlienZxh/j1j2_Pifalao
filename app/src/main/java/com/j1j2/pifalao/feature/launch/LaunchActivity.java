package com.j1j2.pifalao.feature.launch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.j1j2.common.util.Network;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.NetWorkEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityLaunchBinding;
import com.j1j2.pifalao.feature.launch.di.LaunchModule;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class LaunchActivity extends BaseActivity implements MainAplication.AppUpgradeListener {

    ActivityLaunchBinding binding;

    @Inject
    UserLoginPreference userLoginPreference;

    @Inject
    LaunchViewModel launchViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch);
    }

    @Override
    protected void initViews() {
        userRelativePreference.setShowBriberyMoney(true);

//        Beta.init(getApplicationContext(), BuildConfig.DEBUG);
        launchViewModel.setCanNavigate(false);
        launchViewModel.setCheckingUpdate(true);
        Beta.checkUpgrade();

        launchViewModel.initLoginState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainAplication.get(this).registerAppUpgradeListener(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().postSticky(new NetWorkEvent(Network.isConnected(this), Network.getNetworkType(this)));
    }

    @Override
    protected void onDestroy() {
        MainAplication.get(this).clearAppUpgradeListener();
        launchViewModel.onDestory();
        super.onDestroy();
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new LaunchModule(this)).inject(this);
    }


    public void navigateTo() {
        if (userRelativePreference.getIsFirst(true)) {
            navigate.navigateToGuide(this, ActivityOptionsCompat.makeScaleUpAnimation(binding.logo, 0, 0, 0, 0), true);
        } else {
            if (null != userRelativePreference.getSelectedCity(null) && null != userRelativePreference.getSelectedServicePoint(null))
                navigate.navigateToServicesActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(binding.logo, 0, 0, 0, 0), true);
            else if (null != userRelativePreference.getSelectedCity(null) && null == userRelativePreference.getSelectedServicePoint(null))
                navigate.navigateToLocationActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(binding.logo, 0, 0, 0, 0), true, userRelativePreference.getSelectedCity(null));
            else
                navigate.navigateToCityActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(binding.logo, 0, 0, 0, 0), true);
        }
    }


    @Override
    public void onUpgradeFailed(boolean isManual) {
        if (BuildConfig.DEBUG)
            toastor.showToast("升级失败");
        launchViewModel.setCanNavigate(true);//允许跳转
        launchViewModel.setCheckingUpdate(false);//检查更新完成
    }

    @Override
    public void onUpgradeSuccess(boolean isManual) {
        if (BuildConfig.DEBUG)
            toastor.showToast("升级成功");
    }

    @Override
    public void onUpgradeNoVersion(boolean isManual) {
        if (BuildConfig.DEBUG)
            toastor.showToast("onUpgradeNoVersion 无升级");
        launchViewModel.setCanNavigate(true);//允许跳转
        launchViewModel.setCheckingUpdate(false);//检查更新完成

    }

    @Override
    public void onUpgrading(boolean isManual) {
        if (BuildConfig.DEBUG)
            toastor.showToast("升级中");
    }

    @Override
    public void onUpgradeDialogCreate(Context context, View view, UpgradeInfo upgradeInfo) {
        if (BuildConfig.DEBUG)
            toastor.showToast("升级弹框显示");
    }

    @Override
    public void onUpgradeDialogDestory(Context context, View view, UpgradeInfo upgradeInfo) {
        if (BuildConfig.DEBUG)
            toastor.showToast("升级弹框结束");
        //1:建议 2:强制 3:手工
        if (upgradeInfo.upgradeType != 2) {
            launchViewModel.setCanNavigate(true);//允许跳转
            launchViewModel.setCheckingUpdate(false);//检查更新完成
        }
    }
}
