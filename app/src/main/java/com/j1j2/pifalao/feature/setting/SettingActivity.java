package com.j1j2.pifalao.feature.setting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.j1j2.common.util.FileUtils;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivitySettingBinding;
import com.j1j2.pifalao.feature.account.di.AccountModule;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class SettingActivity extends BaseActivity implements View.OnClickListener,
        MainAplication.AppUpgradeListener,
        EasyPermissions.PermissionCallbacks {

    ActivitySettingBinding binding;

    @Inject
    ShopCart shopCart;

    @Inject
    UnReadInfoManager unReadInfoManager;

    private String versionDialogTag = "VERSIONDIALOG";
    private String callDialogTag = "CALLDIALOG";
    private String clearCacheDialogTag = "CLEARCACHEDIALOG";

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        if (MainAplication.get(this).isLogin())
            MainAplication.get(this).getUserComponent().plus(new AccountModule(this)).inject(this);
        else
            navigate.navigateToLogin(this, null, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainAplication.get(this).registerAppUpgradeListener(this);
    }

    @Override
    protected void onDestroy() {
        MainAplication.get(this).clearAppUpgradeListener();
        super.onDestroy();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.setSettingActivity(this);
    }

    @Override
    protected void initViews() {
        binding.versionName.setText(getLocalVersionName(this));

        binding.cacheSize.setText(FileUtils.getFormatSize(FileUtils.getFolderSize(new File(Constant.FilePath.saveFolder))));
    }


    public void showVersionDialog() {
        showMessageDialogDuplicate(true, versionDialogTag, "提示", "已是最新版本", null, "确定");
    }

    public void showCallDialog() {
        showMessageDialogDuplicate(true, callDialogTag, "提示", "确认拨打： 400-808-7172？", "取消", "确定");
    }

    public void showClearCacheDialog() {
        showMessageDialogDuplicate(true, clearCacheDialogTag, "提示", "确认清除缓存吗？", "取消", "确定");
    }


    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.serviceCall) {
            showCallDialog();
        }
        if (v == binding.aboutUs)
            navigate.navigateToAboutUs(this, null, false);
        if (v == binding.feedback) {
            navigate.navigateToFeedBack(this, null, false);
        }
        if (v == binding.versionCheck)
            Beta.checkUpgrade();
        if (v == binding.changePassword) {
            navigate.navigateToChangePassword(this, null, false);
        }
        if (v == binding.logout) {
            MainAplication.get(this).loginOut();
            shopCart.clear();
            unReadInfoManager.clear();
            EventBus.getDefault().postSticky(new LogStateEvent(false));
//            finish();
        }

        if (v == binding.clearCache) {
            showClearCacheDialog();
        }
    }

    @Subscribe(sticky = true)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {

        } else {
            finish();
        }
    }


    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    @Override
    public void onUpgradeFailed(boolean isManual) {

    }

    @Override
    public void onUpgradeSuccess(boolean isManual) {

    }

    @Override
    public void onUpgradeNoVersion(boolean isManual) {
        showVersionDialog();
    }

    @Override
    public void onUpgrading(boolean isManual) {

    }

    @Override
    public void onDownloadCompleted(boolean isComplete) {

    }

    @Override
    public void onUpgradeDialogCreate(Context context, View view, UpgradeInfo upgradeInfo) {

    }

    @Override
    public void onUpgradeDialogDestory(Context context, View view, UpgradeInfo upgradeInfo) {

    }

    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        super.onDialogPositiveClick(fragmentTag);
        if (fragmentTag.equals(callDialogTag)) {
            callPiFaLao();
        } else if (fragmentTag.equals(clearCacheDialogTag)) {
            showProgress("缓存删除中");
            Observable.create(new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {
                    FileUtils.deleteFolderFile(Constant.FilePath.saveFolder, true);
                    subscriber.onCompleted();
                }
            })
//                                .compose(SettingActivity.this.<Void>bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Void>() {
                        @Override
                        public void onCompleted() {
                            binding.cacheSize.setText(FileUtils.getFormatSize(FileUtils.getFolderSize(new File(Constant.FilePath.saveFolder))));
                            dismissProgress();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Void aVoid) {
                        }
                    });
        }
    }

    @SuppressWarnings("all")
    @AfterPermissionGranted(RC_CALLPHONE_PERM)
    private void callPiFaLao() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-808-7172"));
            startActivity(intent);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "拨打电话，缺少拨打电话权限。",
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
            new AppSettingsDialog.Builder(this, "拨打电话，缺少拨打电话权限。")
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
