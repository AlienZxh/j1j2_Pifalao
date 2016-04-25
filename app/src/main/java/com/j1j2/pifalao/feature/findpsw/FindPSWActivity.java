package com.j1j2.pifalao.feature.findpsw;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityFindpswBinding;
import com.j1j2.pifalao.feature.findpsw.di.FindPSWModule;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-22.
 */
@RequireBundler
public class FindPSWActivity extends BaseActivity implements View.OnClickListener {

    ActivityFindpswBinding binding;

    @Inject
    FindPSWViewModel findPSWViewModel;

    String phone;
    String smscode;
    String psw;
    String confirmpsw;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_findpsw);
        binding.setFindPSWViewModel(findPSWViewModel);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new FindPSWModule(this)).inject(this);
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
            phone = binding.phoneEdit.getText().toString();
            if (phone.length() != 11) {
                toastor.showSingletonToast("请输入正确的手机号码");
                return;
            } else {
                binding.smsCodeBtn.setEnabled(false);
                binding.smsCodeBtn.setTextColor(getResources().getColor(R.color.colorTextaaa));
                binding.smsCodeBtn.setText("正在获取");
                findPSWViewModel.querySmsCode(phone);
                toastor.showSingletonToast("已发送请求");
            }
        }
        if (v == binding.nextBtn) {
            phone = binding.phoneEdit.getText().toString();
            smscode = binding.smsCodeEdit.getText().toString();
            psw = binding.pswEdit.getText().toString();
            confirmpsw = binding.confirmPswEdit.getText().toString();
            if (phone.length() != 11) {
                toastor.showSingletonToast("请输入正确的手机号码");
                return;
            }
            if (smscode.length() <= 0) {
                toastor.showSingletonToast("请输入正确的短信验证码");
                return;
            }
            if (psw.length() <= 0 || confirmpsw.length() <= 0) {
                toastor.showSingletonToast("请输入新密码并确认");
                return;
            }
            if (!psw.equals(confirmpsw)) {
                toastor.showSingletonToast("两次输入密码不一致");
                return;
            }
            findPSWViewModel.findPassWordStepTwo(phone, smscode, psw, confirmpsw);

        }
    }
}
