package com.j1j2.pifalao.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.j1j2.common.util.Toastor;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.di.ActivityComponent;
import com.j1j2.pifalao.app.di.ActivityModule;
import com.j1j2.pifalao.app.dialog.MessageDialogFragmentBundler;
import com.j1j2.pifalao.app.dialog.impl.IDialogNegativeButtonClickListener;
import com.j1j2.pifalao.app.dialog.impl.IDialogPositiveButtonClickListener;
import com.j1j2.pifalao.app.event.BaseEvent;
import com.j1j2.pifalao.app.event.NetWorkEvent;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by alienzxh on 16-3-4.
 */
public abstract class BaseActivity extends RxAppCompatActivity
        implements IDialogPositiveButtonClickListener,
        IDialogNegativeButtonClickListener {
    protected final int RC_SETTINGS_SCREEN = 109;
    protected static final int RC_CALLPHONE_PERM = 110;

    private String pageName = getClass().getSimpleName();

    @Inject
    protected Navigate navigate;

    @Inject
    public Toastor toastor;

    protected Fragment currentFragment;

    protected DialogFragment messageDialog;

    protected android.app.AlertDialog progressDialog;

    private String netWorkDialogTag = "NETWORKDIALOG";

    protected void initActionBar() {

    }

    protected abstract void initBinding();

    protected abstract void initViews();

    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent();
        initBinding();
        initActionBar();
        initViews();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
        Logger.d("umeng activity " + pageName + " - display - ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName()); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
        Logger.d("umeng activity " + pageName + " - hidden - ");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        if (isMessageDialogShowing())
            messageDialog.dismiss();

    }


    protected void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentFragment != null
                && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    protected ActivityComponent getActivityComponent() {
        return MainAplication.get(this).getAppComponent().plus(getActivityModule());
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new SpotsDialog(this, message, R.style.CustomSpotDialog);
            progressDialog.setCancelable(false);
        } else
            progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void showMessageDialogDuplicate(boolean cancelable, String fragmentTag, String title, String message, String negativeButtonText, String positiveButtonText) {
        messageDialog = MessageDialogFragmentBundler.build().fragmentTag(fragmentTag)
                .cancelable(cancelable)
                .title(title)
                .message(message)
                .negativeButtonText(negativeButtonText)
                .positiveButtonText(positiveButtonText)
                .create();
        messageDialog.show(getSupportFragmentManager(), fragmentTag);
    }

    public void showMessageDialogNotDuplicate(boolean cancelable, String fragmentTag, String title, String message, String negativeButtonText, String positiveButtonText) {
        dismissMessageDialog();
        messageDialog = MessageDialogFragmentBundler.build().fragmentTag(fragmentTag)
                .cancelable(cancelable)
                .title(title)
                .message(message)
                .negativeButtonText(negativeButtonText)
                .positiveButtonText(positiveButtonText)
                .create();
        messageDialog.show(getSupportFragmentManager(), fragmentTag);
    }

    public void dismissMessageDialog() {
        if (isMessageDialogShowing())
            messageDialog.dismiss();

    }

    public boolean isMessageDialogShowing() {
        if (messageDialog != null && messageDialog.isVisible())
            return true;
        else
            return false;
    }


    @Subscribe
    public void onBaseEvent(BaseEvent baseEvent) {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onNetWorkEvent(NetWorkEvent netWorkEvent) {
        if (getClass().getName().equals("com.j1j2.pifalao.feature.launch.LaunchActivity"))
            return;

        String message = "";
        boolean showDialog = false;

        if (!netWorkEvent.isConnected()) {
            message = "网络连接失败，请检查网络设置。";
            showDialog = true;
        }

        if (!showDialog) {
            dismissMessageDialog();
            return;
        } else {
            showMessageDialogNotDuplicate(true, netWorkDialogTag, "提示", message, "取消", "设置");
        }

    }

    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        if (fragmentTag.equals(netWorkDialogTag)) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    public void onDialogNegativeClick(String fragmentTag) {

    }
}
