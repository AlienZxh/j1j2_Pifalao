package com.j1j2.pifalao.feature.register.steptwo;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.RegisterSuccessEvent;
import com.j1j2.pifalao.databinding.ActivityRegistersteptwoBinding;
import com.j1j2.pifalao.feature.register.steptwo.di.RegisterStepTwoModule;
import com.j1j2.pifalao.feature.successresult.SuccessResultActivity;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class RegisterStepTwoActivity extends BaseActivity implements View.OnClickListener {

    ActivityRegistersteptwoBinding binding;

    @Arg
    ClientRegisterStepOneBody clientRegisterStepOneBody;

    @Inject
    RegisterStepTwoViewModel registerStepTwoViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registersteptwo);
        binding.setRegisterStepTwoViewModel(registerStepTwoViewModel);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new RegisterStepTwoModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.completeBtn) {
            String pswStr = binding.pswEdit.getText().toString();
            String confirmStr = binding.confirmPswEdit.getText().toString();
            if (pswStr.length() < 6 || confirmStr.length() < 6) {
                toastor.showSingletonToast("密码至少需要６位数");
                return;
            } else if (!pswStr.equals(confirmStr)) {
                toastor.showSingletonToast("两次输入密码不一致");
                return;
            } else {
                clientRegisterStepOneBody.setPassWord(pswStr);
                clientRegisterStepOneBody.setConfrimPwd(confirmStr);
                clientRegisterStepOneBody.setMarketerCode(binding.recommendEdit.getText().toString());
                registerStepTwoViewModel.clientRegisterStepOne(clientRegisterStepOneBody);
            }
        }
    }

    public void completeRegister() {
        navigate.navigateToSuccessResult(this, null, false, SuccessResultActivity.FROM_REGISTER);
    }

    @Subscribe
    public void onRegisterSuccessEvent(RegisterSuccessEvent event) {
        finish();
    }
}
