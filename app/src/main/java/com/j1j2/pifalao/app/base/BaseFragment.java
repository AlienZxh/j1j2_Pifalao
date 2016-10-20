package com.j1j2.pifalao.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.dialog.MessageDialogFragmentBundler;
import com.j1j2.pifalao.app.event.BaseEvent;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;

import dmax.dialog.SpotsDialog;

/**
 * Created by alienzxh on 16-3-16.
 */
public abstract class BaseFragment extends RxFragment {

    private String pageName = getFragmentName();

    protected abstract String getFragmentName();

    protected abstract View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initViews();


    protected void setupActivityComponent() {

    }

    protected android.app.AlertDialog progressDialog;

    protected DialogFragment messageDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActivityComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initBinding(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(true, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(false, false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isResumed()) {
            onVisibilityChangedToUser(isVisibleToUser, true);
        }
    }

    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     *
     * @param isVisibleToUser                      true：用户能看见当前Fragment；false：用户看不见当前Fragment
     * @param isHappenedInSetUserVisibleHintMethod true：本次回调发生在setUserVisibleHintMethod方法里；false：发生在onResume或onPause方法里
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser, boolean isHappenedInSetUserVisibleHintMethod) {
        if (isVisibleToUser) {
            if (pageName != null) {
                MobclickAgent.onPageStart(pageName);
                Logger.d("umeng fragment " + pageName + " - display - " + (isHappenedInSetUserVisibleHintMethod ? "setUserVisibleHint" : "onResume"));
            }
        } else {
            if (pageName != null) {
                MobclickAgent.onPageEnd(pageName);
                Logger.d("umeng fragment " + pageName + " - hidden - " + (isHappenedInSetUserVisibleHintMethod ? "setUserVisibleHint" : "onPause"));
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainAplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new SpotsDialog(getContext(), message, R.style.CustomSpotDialog);
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
        messageDialog.show(getChildFragmentManager(), fragmentTag);
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
        messageDialog.show(getChildFragmentManager(), fragmentTag);
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
}
