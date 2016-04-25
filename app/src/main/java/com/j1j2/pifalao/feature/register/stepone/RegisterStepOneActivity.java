package com.j1j2.pifalao.feature.register.stepone;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.RegisterSuccessEvent;
import com.j1j2.pifalao.databinding.ActivityRegistersteponeBinding;
import com.j1j2.pifalao.feature.register.stepone.di.RegisterStepOneModule;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class RegisterStepOneActivity extends BaseActivity implements View.OnClickListener {

    ActivityRegistersteponeBinding binding;

    @Inject
    RegisterStepOneViewModel registerStepOneViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registerstepone);
        binding.setRegisterStepOneViewModel(registerStepOneViewModel);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new RegisterStepOneModule(this)).inject(this);
    }


    public void setSmsCodeBtnState(long countDown) {
        if (countDown <= 0) {
            binding.smsCodeBtn.setEnabled(true);
            binding.smsCodeBtn.setTextColor(getResources().getColor(R.color.colorAccent));
            binding.smsCodeBtn.setText("获取验证码");
        } else {
            binding.smsCodeBtn.setEnabled(false);
            binding.smsCodeBtn.setTextColor(getResources().getColor(R.color.colorTextaaa));
            binding.smsCodeBtn.setText(countDown + "s后重试");
        }

    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.smsCodeBtn) {
            if (binding.phoneEdit.getText().toString().length() < 11) {
                toastor.showSingletonToast("请输入正确的手机号码");
                return;
            }
            binding.smsCodeBtn.setEnabled(false);
            binding.smsCodeBtn.setTextColor(getResources().getColor(R.color.colorTextaaa));
            binding.smsCodeBtn.setText("正在获取");
            toastor.showSingletonToast("已发送请求");
            registerStepOneViewModel.querySmsCode(binding.phoneEdit.getText().toString());
        }
        if (v == binding.nextBtn) {
            if (binding.phoneEdit.getText().toString().length() < 11) {
                toastor.showSingletonToast("请输入正确的手机号码");
                return;
            }
            if (binding.smsCodeEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("请输入正确的手机验证码");
                return;
            }
            registerStepOneViewModel.validatePhoneSmsCode(binding.phoneEdit.getText().toString(), binding.smsCodeEdit.getText().toString());
        }
    }

    public void next(ClientRegisterStepOneBody clientRegisterStepOneBody) {
        navigate.navigateToRegisterStepTwo(this, null, false, clientRegisterStepOneBody);
    }

    @Subscribe
    public void onRegisterSuccessEvent(RegisterSuccessEvent event) {
        finish();
    }
}
