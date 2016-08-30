package com.j1j2.pifalao.feature.changepassword;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.databinding.ActivityChangepasswordBinding;
import com.j1j2.pifalao.feature.changepassword.di.ChangePasswordModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {


    ActivityChangepasswordBinding binding;

    @Inject
    ChangePasswordViewModel changePasswordViewModel;

    @Inject
    UserLoginPreference userLoginPreference;

    InputMethodManager imm;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_changepassword);
        binding.setChangePasswordViewModel(changePasswordViewModel);
    }

    @Override
    protected void initViews() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new ChangePasswordModule(this)).inject(this);
    }


    @Override
    public void onClick(View v) {
        if (binding.oldPSWEdit.hasFocus())
            imm.hideSoftInputFromWindow(binding.oldPSWEdit.getWindowToken(), 0);
        if (binding.confirmPSWEdit.hasFocus())
            imm.hideSoftInputFromWindow(binding.confirmPSWEdit.getWindowToken(), 0);
        if (binding.newPSWEdit.hasFocus())
            imm.hideSoftInputFromWindow(binding.newPSWEdit.getWindowToken(), 0);
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.confirmBtn) {
            if (binding.oldPSWEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("请输入原密码");
                return;
            } else if (binding.newPSWEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("请输入新密码");
                return;
            } else if (binding.confirmPSWEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("请确认新密码");
                return;
            } else if (binding.newPSWEdit.getText().toString().length() < 6 || binding.confirmPSWEdit.getText().toString().length() < 6) {
                toastor.showSingletonToast("新密码需要不小于６位");
                return;
            } else if (!binding.newPSWEdit.getText().toString().equals(binding.confirmPSWEdit.getText().toString())) {
                toastor.showSingletonToast("新密码与确认新密码不一致");
                return;
            }

            changePasswordViewModel.changePassword(binding.oldPSWEdit.getText().toString(), binding.confirmPSWEdit.getText().toString());
        }
    }

    public void finishChange(String newPSW) {
        MainAplication.get(this).loginOut();
        userLoginPreference.removePassWord();
        EventBus.getDefault().post(new LogStateEvent(false));
        finish();
    }
}
