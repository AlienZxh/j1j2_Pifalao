package com.j1j2.pifalao.feature.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivitySettingBinding;
import com.j1j2.pifalao.feature.account.di.AccountModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    ActivitySettingBinding binding;

    @Inject
    ShopCart shopCart;

    @Inject
    UnReadInfoManager unReadInfoManager;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new AccountModule(this)).inject(this);
    }


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.setSettingActivity(this);
    }

    @Override
    protected void initViews() {
        binding.versionName.setText(getLocalVersionName(this));
    }


    private void showCallDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认拨打： 400-808-7172？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PackageManager pkm = getPackageManager();
                        boolean has_permission = (PackageManager.PERMISSION_GRANTED
                                == pkm.checkPermission("android.permission.CALL_PHONE", "com.j1j2.pifalao"));
                        if (has_permission) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-808-7172"));
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
        if (v == binding.serviceCall) {
            showCallDialog();
        }
        if (v == binding.aboutUs)
            navigate.navigateToAboutUs(this, null, false);
        if (v == binding.feedback) {
            navigate.navigateToFeedBack(this, null, false);
        }
        if (v == binding.versionCheck)
            showVersionDialog();
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
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {

        } else {
            finish();
        }
    }

    public void showVersionDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("已是最新版本")
                .setPositiveButton("确定", null)
                .create();
        messageDialog.show();
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
}
