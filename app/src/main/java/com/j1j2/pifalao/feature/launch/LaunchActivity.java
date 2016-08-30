package com.j1j2.pifalao.feature.launch;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;

import com.j1j2.common.util.Network;
import com.j1j2.common.util.ScreenUtils;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.NetWorkEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityLaunchBinding;
import com.j1j2.pifalao.feature.launch.di.LaunchModule;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import javax.inject.Inject;

public class LaunchActivity extends BaseActivity {

    ActivityLaunchBinding binding;

    @Inject
    UserLoginPreference userLoginPreference;

    @Inject
    LaunchViewModel launchViewModel;

    @Inject
    UserRelativePreference userRelativePreference;


    ProgressBar progressBar;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch);
    }

    @Override
    protected void initViews() {
        launchViewModel.getUpdateInfo();
        launchViewModel.initLoginState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().postSticky(new NetWorkEvent(Network.isConnected(this), Network.getNetworkType(this)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        launchViewModel.onDestory();
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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

    public void showCompulsoryUpdateDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("新版本可用，是否更新？")
                .setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchViewModel.downloadAPK();
                    }
                })
                .create();
        messageDialog.show();
    }

    public void showUpdateDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("新版本可用，是否更新？")
                .setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchViewModel.setCheckingUpdate(false);
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchViewModel.downloadAPK();
                    }
                })
                .create();
        messageDialog.show();
    }

    public void hideDownloadDialog() {
        if (null == messageDialog || !messageDialog.isShowing())
            return;
        messageDialog.hide();
    }

    public void showDownloadDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        View progressBarView = getLayoutInflater().inflate(R.layout.view_update_load, null, false);
        progressBar = (ProgressBar) progressBarView.findViewById(R.id.progressbar);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("下载中")
                .setMessage("已下载0%")
                .setView(progressBarView, 0, ScreenUtils.dpToPx(20), 0, 0)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();
        messageDialog.show();
    }

    public void setDownloadProgress(float progress, long total) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        messageDialog.setMessage("   共" + df.format(total / 1000000) + "M,已下载：" + progress + "%");
        progressBar.setProgress((int) progress);
    }

    public void installAPK(File apkfile) {
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        startActivity(i);
    }

    public int getVersionCode() {
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void navigateTo() {
//        navigate.navigateToGuide(this, ActivityOptionsCompat.makeScaleUpAnimation(binding.logo, 0, 0, 0, 0), true);
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

}
