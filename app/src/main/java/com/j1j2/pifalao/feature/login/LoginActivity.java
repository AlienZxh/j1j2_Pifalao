package com.j1j2.pifalao.feature.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.RegisterSuccessEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.databinding.ActivityLoginBinding;
import com.j1j2.pifalao.feature.login.di.LoginModule;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

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

    InputMethodManager imm;

    String username;
    String password;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLoginViewModel(loginViewModel);
    }

    @Override
    protected void initViews() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (getResources().getBoolean(R.bool.can_auto_login)) {
            userLoginPreference.setIsAutoLogin(true);
            binding.autoLoginCeck.setChecked(true);

            binding.username.setText(userLoginPreference.getUsername(""));
            binding.password.setText(userLoginPreference.getPassWord(""));

            username = binding.username.getText().toString();
            password = binding.password.getText().toString();
        }

        binding.autoLoginCeck.setOnCheckedChangeListener(this);

    }


    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new LoginModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (binding.username.hasFocus())
            imm.hideSoftInputFromWindow(binding.username.getWindowToken(), 0);
        if (binding.password.hasFocus())
            imm.hideSoftInputFromWindow(binding.password.getWindowToken(), 0);
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.loginBtn) {
            username = binding.username.getText().toString();
            password = binding.password.getText().toString();
            if (EmptyUtils.isEmpty(username)) {
                toastor.showSingletonToast("账号不能为空");
                return;
            }
            if (EmptyUtils.isEmpty(password)) {
                toastor.showSingletonToast("密码不能为空");
                return;
            }
            Logger.d("login IsAutoLogin " + userLoginPreference.getIsAutoLogin(false));
            loginViewModel.login(username, password, binding.autoLoginCeck.isChecked());
        }
        if (v == binding.registerBtn) {
            navigate.navigateToRegisterStepOne(this, null, false);
        }
        if (v == binding.forgetPSW)
            navigate.navigateToFindPSW(this, null, false);


    }

    public void navigateToValidateAccount() {
        navigate.navigateToValidateAccountActivity(this, null, false, username, password, binding.autoLoginCeck.isChecked());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        userLoginPreference.setIsAutoLogin(isChecked);
    }

    @Subscribe
    public void onRegisterSuccessEvent(RegisterSuccessEvent event) {
        if (event.isLogin()) {
            finish();
        } else {
            binding.username.setText(event.getLoginAccount());
            binding.password.setText(event.getPassWord());
        }
    }
}
