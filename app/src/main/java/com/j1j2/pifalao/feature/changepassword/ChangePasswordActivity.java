package com.j1j2.pifalao.feature.changepassword;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.databinding.ActivityChangepasswordBinding;
import com.j1j2.pifalao.feature.changepassword.di.ChangePasswordModule;

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

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_changepassword);
        binding.setChangePasswordViewModel(changePasswordViewModel);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new ChangePasswordModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.confirmBtn) {
            if (binding.oldPSWEdit.getText().toString().length() <= 0) {
                Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.newPSWEdit.getText().toString().length() <= 0) {
                Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.confirmPSWEdit.getText().toString().length() <= 0) {
                Toast.makeText(this, "请确认新密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.newPSWEdit.getText().toString().length() < 6 || binding.confirmPSWEdit.getText().toString().length() < 6) {
                Toast.makeText(this, "新密码需要不小于６位", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.newPSWEdit.getText().toString().length() != binding.confirmPSWEdit.getText().toString().length()) {
                Toast.makeText(this, "新密码与确认新密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            changePasswordViewModel.changePassword(binding.oldPSWEdit.getText().toString(), binding.confirmPSWEdit.getText().toString());
        }
    }

    public void finishChange(String newPSW) {
        userLoginPreference.setPassWord(newPSW);
        finish();
    }
}
