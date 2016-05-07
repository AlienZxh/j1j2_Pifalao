package com.j1j2.pifalao.app.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.di.ActivityComponent;
import com.j1j2.pifalao.app.di.ActivityModule;
import com.j1j2.pifalao.app.event.BaseEvent;
import com.j1j2.pifalao.app.event.NetWorkEvent;
import com.j1j2.common.util.Toastor;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by alienzxh on 16-3-4.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Inject
    protected Navigate navigate;

    @Inject
    public Toastor toastor;

    protected Fragment currentFragment;

    protected AlertDialog messageDialog;

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
    protected void onStop() {
        super.onStop();
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            if (messageDialog != null && messageDialog.isShowing()) {
                messageDialog.dismiss();
                return;
            } else {
                return;
            }
        } else {
            if (messageDialog != null && messageDialog.isShowing()) {
                messageDialog.dismiss();
            }
        }
        messageDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(message)
                .setNegativeButton("取消", null)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                }).create();
        messageDialog.show();
    }
}
