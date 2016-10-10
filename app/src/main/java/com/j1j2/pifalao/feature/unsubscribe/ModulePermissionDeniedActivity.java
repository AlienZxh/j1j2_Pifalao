package com.j1j2.pifalao.feature.unsubscribe;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.common.util.ValidateUtils;
import com.j1j2.data.http.api.ApplyForApi;
import com.j1j2.data.model.ApplyForServiceCount;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ApplyForServicePointBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivityModulePermissiondeniedBinding;
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
 * Created by alienzxh on 16-9-27.
 */
@RequireBundler
public class ModulePermissionDeniedActivity extends BaseActivity implements View.OnClickListener {

    ActivityModulePermissiondeniedBinding binding;

    @Arg
    Module module;

    @Inject
    ApplyForApi applyForApi;

    UnReadInfoManager unReadInfoManager = null;
    User user = null;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ApplyForModule(this)).inject(this);
    }


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_module_permissiondenied);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);
        binding.applyBtn.setOnClickListener(this);
        binding.cancelBtn.setOnClickListener(this);
    }


    public void applyForService(final ApplyForServicePointBody applyForServicePointBody) {
        showProgress("数据提交中");
        applyForApi.applyForServicePoint(applyForServicePointBody)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        dismissProgress();
                        toastor.showSingletonToast(s);
                        queryApplyForServiceCount(applyForServicePointBody.getLoginMobile());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                        toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryApplyForServiceCount(String loginMobile) {
        applyForApi.applyForServicePointCount(loginMobile)
                .compose(this.<WebReturn<ApplyForServiceCount>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ApplyForServiceCount>() {
                    @Override
                    public void onWebReturnSucess(ApplyForServiceCount applyForServiceCount) {

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

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn || v == binding.cancelBtn)
            onBackPressed();
        if (v == binding.individualBtn) {
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigate.navigateToLogin(this, null, false);
        }

        if (v == binding.applyBtn) {
            String name = binding.nameEdit.getText().toString();
            String phoneNo = binding.phoneEdit.getText().toString();
            String address = binding.addressEdit.getText().toString();
            if (EmptyUtils.isEmpty(name)) {
                toastor.showSingletonToast("姓名不能为空");
                return;
            }
            if (EmptyUtils.isEmpty(phoneNo)) {
                toastor.showSingletonToast("联系电话不能为空");
                return;
            }
            if (!ValidateUtils.isMobileNO(phoneNo)) {
                toastor.showSingletonToast("请填写正确的手机号码");
                return;
            }
            if (EmptyUtils.isEmpty(address)) {
                toastor.showSingletonToast("详细地址不能为空");
                return;
            }

            ApplyForServicePointBody applyForServicePointBody = new ApplyForServicePointBody();
            applyForServicePointBody.setApplyPhone(phoneNo);
            applyForServicePointBody.setApplyAddress(address);
            applyForServicePointBody.setApplyerName(name);
            if (user != null)
                applyForServicePointBody.setLoginMobile(user.getLoginAccount());
            applyForService(applyForServicePointBody);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);

            user = MainAplication.get(this).getUserComponent().user();
            queryApplyForServiceCount(user.getLoginAccount());
        }

    }
}
