package com.j1j2.pifalao.feature.register.stepone;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.RegisterSuccessEvent;
import com.j1j2.pifalao.app.event.VipUpdateSuccessEvent;
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

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.smsCodeBtn) {
            if (binding.phoneEdit.getText().toString().length() < 11) {
                Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            registerStepOneViewModel.querySmsCode(binding.phoneEdit.getText().toString());
        }
        if (v == binding.nextBtn) {
            if (binding.phoneEdit.getText().toString().length() < 11) {
                Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binding.smsCodeEdit.getText().toString().length() <= 0) {
                Toast.makeText(getApplicationContext(), "请输入正确的手机验证码", Toast.LENGTH_SHORT).show();
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
