package com.j1j2.pifalao.feature.unsubscribe;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.common.util.ValidateUtils;
import com.j1j2.data.http.api.ApplyForApi;
import com.j1j2.data.model.ApplyForServiceCount;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ApplyForServiceBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityUnsubscribeDeliveryBinding;
import com.j1j2.pifalao.feature.unsubscribe.di.ApplyForModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-8-30.
 */
@RequireBundler
public class UnsubscribeModuleActivity extends BaseActivity implements View.OnClickListener {

    ActivityUnsubscribeDeliveryBinding binding;

    User user;

    @Arg
    ShopSubscribeService shopSubscribeService;

    @Inject
    ApplyForApi applyForApi;
    @Inject
    UserRelativePreference userRelativePreference;

    UnReadInfoManager unReadInfoManager = null;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ApplyForModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_unsubscribe_delivery);
    }

    @Override
    protected void initViews() {

        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);
        binding.openBtn.setOnClickListener(this);
        binding.cancelBtn.setOnClickListener(this);

        binding.title.setText(shopSubscribeService.getName());
        binding.text.setText("亲～您选择的服务点，还未来得及开通" + shopSubscribeService.getName() + "服务！");


        if (shopSubscribeService.getServiceType() == Constant.ModuleType.SHOPSERVICE) {
            binding.img.setImageResource(R.drawable.unsubscribe_shop_img);
        } else {
            binding.img.setImageResource(R.drawable.unsubscribe_delivery_img);
        }
    }

    public void queryApplyForServiceCount(String mobile) {
        applyForApi.queryApplyForServiceCount(shopSubscribeService.getServiceId(), mobile)
                .compose(this.<WebReturn<ApplyForServiceCount>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ApplyForServiceCount>() {
                    @Override
                    public void onWebReturnSucess(ApplyForServiceCount applyForServiceCount) {
                        binding.num.setText("" + applyForServiceCount.getCount());
                        binding.setIsApplyed(applyForServiceCount.isCurrentUserApplyed());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void applyForService(final ApplyForServiceBody applyForServiceBody) {
        showProgress("数据提交中");
        applyForApi.applyForService(applyForServiceBody)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        dismissProgress();
                        toastor.showSingletonToast(s);
                        queryApplyForServiceCount(applyForServiceBody.getMobile());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                        toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissProgress();
                        toastor.showSingletonToast("连接失败，请重试");
                    }
                });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);

            user = MainAplication.get(this).getUserComponent().user();
            queryApplyForServiceCount(user.getUserName());
        } else {
            user = null;
            queryApplyForServiceCount("");
        }
        binding.setIsLogin(event.isLogin());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn || v == binding.cancelBtn)
            onBackPressed();
        if (v == binding.individualBtn) {
            navigate.navigateToIndividualCenter(this, null, false);
        }

        if (v == binding.openBtn) {
            String phoneNo;
            if (user == null) {
                phoneNo = binding.phoneEdit.getText().toString();
                if (EmptyUtils.isEmpty(phoneNo)) {
                    toastor.showSingletonToast("手机号码不能为空");
                    return;
                }

                if (!ValidateUtils.isMobileNO(phoneNo)) {
                    toastor.showSingletonToast("请填写正确的手机号码");
                    return;
                }
            } else {
                phoneNo = user.getUserName();
            }

            ApplyForServiceBody applyForServiceBody = new ApplyForServiceBody();
            applyForServiceBody.setMobile(phoneNo);
            applyForServiceBody.setServiceModuleId(shopSubscribeService.getServiceId());
            applyForServiceBody.setServicePointId(userRelativePreference.getSelectedServicePoint(null).getShopId());
            applyForService(applyForServiceBody);
        }
    }
}
