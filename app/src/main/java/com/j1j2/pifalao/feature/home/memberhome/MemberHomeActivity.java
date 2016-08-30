package com.j1j2.pifalao.feature.home.memberhome;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.View;

import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeFrameLayout;
import com.j1j2.common.view.smarttablayout.SmartTabLayout;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.data.model.UnReadInfo;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.MainTab;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.LoginCookieTimeoutEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.databinding.ActivityMemberhomeBinding;
import com.j1j2.pifalao.feature.home.memberhome.di.MemberHomeModule;
import com.j1j2.pifalao.feature.home.morehome.MoreHomeFragment;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.main.MainAdapter;
import com.orhanobut.logger.Logger;
import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.provider.FragmentData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-8-23.
 */
@RequireBundler
public class MemberHomeActivity extends BaseActivity implements MemberHomeBannerFragment.MemberHomeBannerFragmentListener {

    ActivityMemberhomeBinding binding;

    @Inject
    UserMessageApi userMessageApi;
    @Inject
    UserLoginApi userLoginApi;
    @Inject
    Navigate navigate;

    UnReadInfoManager unReadInfoManager = null;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new MemberHomeModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memberhome);
    }

    @Override
    protected void initViews() {
        ItemBinderFactory itemBinderFactory = new ItemBinderFactory(getSupportFragmentManager());
        MultiTypeAdapter<Object> multiTypeAdapter = new MultiTypeAdapter<>(loadData(), itemBinderFactory);
        binding.multiTypeView.setAdapter(multiTypeAdapter);

    }

    private List<Object> loadData() {
        List<Object> data = new ArrayList<>();

        data.add(new FragmentData(MemberHomeBannerFragment.class, "MemberHomeBannerFragment"));
        data.add(new FragmentData(MemberHomePrizeFragment.class, "MemberHomePrizeFragment"));
        data.add(new FragmentData(MemberHomeFreeFragment.class, "MemberHomeFreeFragment"));
        data.add(new FragmentData(MemberHomeLuckyFragment.class, "MemberHomeLuckyFragment"));
        return data;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLoginCookieTimeoutEvent(LoginCookieTimeoutEvent event) {
        if (MainAplication.get(this).isLogin()) {
            MainAplication.get(this).loginOut();
            EventBus.getDefault().postSticky(new LogStateEvent(false));
        }
    }

    public void updateUserTerminalDetail(final String deviceToken) {
        LoginBody loginBody = new LoginBody();
        loginBody.setTerminalType(1);
        loginBody.setDeviceToken(deviceToken);
        userLoginApi.updateUserTerminalDetail(loginBody)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    @Override
    public void navigateToFree() {
        navigate.navigateToFreeConvertibilityActivity(this, null, false);
    }


}
