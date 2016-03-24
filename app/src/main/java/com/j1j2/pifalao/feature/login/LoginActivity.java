package com.j1j2.pifalao.feature.login;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.databinding.ActivityLoginBinding;
import com.j1j2.pifalao.feature.login.di.LoginModule;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-18.
 */
@RequireBundler
public class LoginActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    ActivityLoginBinding binding;

    @Inject
    LoginViewModel loginViewModel;

    @Inject
    UserLoginPreference userLoginPreference;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLoginViewModel(loginViewModel);
    }

    @Override
    protected void initViews() {
        binding.autoLoginCeck.setChecked(userLoginPreference.getIsAutoLogin(false));
        if (userLoginPreference.getIsAutoLogin(false)) {
            binding.username.setText(userLoginPreference.getUsername(""));
            binding.password.setText(userLoginPreference.getPassWord(""));
            binding.autoLoginCeck.setChecked(true);
        }
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new LoginModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.loginBtn) {
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            if (EmptyUtils.isEmpty(username)) {
                Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (EmptyUtils.isEmpty(password)) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            loginViewModel.login(username, password, binding.autoLoginCeck.isChecked());
        }
        if (v == binding.registerBtn) {
            navigate.navigateToRegisterStepOne(this, null, false);
            navigate.navigateToRegisterStepTwo(this, null, false);
        }
        if (v == binding.forgetPSW)
            Toast.makeText(this, "找回密码", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
