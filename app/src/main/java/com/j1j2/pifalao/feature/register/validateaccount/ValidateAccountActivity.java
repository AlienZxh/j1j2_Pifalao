package com.j1j2.pifalao.feature.register.validateaccount;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;

import com.j1j2.common.util.Toastor;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.RegisterSuccessEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.databinding.ActivityValidateaccountBinding;
import com.j1j2.pifalao.feature.register.validateaccount.di.ValidateAccountModule;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-10-21.
 */
@RequireBundler
public class ValidateAccountActivity extends BaseActivity implements View.OnClickListener {

    ActivityValidateaccountBinding binding;

    @Inject
    UserLoginApi userLoginApi;
    @Inject
    Toastor toastor;
    @Inject
    UserLoginPreference userLoginPreference;

    @Arg
    String userName;
    @Arg
    String passWord;
    @Arg
    boolean isAutoLogin;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ValidateAccountModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_validateaccount);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.smsCodeBtn.setOnClickListener(this);
        binding.nextBtn.setOnClickListener(this);
        String subStr = userName.substring(3, 7);
        binding.phoneText.setText(userName.replace(subStr, "****"));
    }

    public void countDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        setSmsCodeBtnState(60 - aLong);
                        if (aLong == 60)
                            onCompleted();
                    }
                });
    }


    public void querySmsCode() {
        userLoginApi.sendMobileVerifyCode()
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        toastor.showSingletonToast(s);
                        setCodeExplainVisibility();
                        countDown();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        toastor.showSingletonToast(errorMessage);
                        setSmsCodeBtnState(0);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void validatePhoneSmsCode(String smsCode) {
        showProgress("数据提交中");

        userLoginApi.setUserMobileVerifyed(smsCode)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        login(userName, passWord, isAutoLogin);
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

    public void login(@NonNull final String username, @NonNull final String password, final boolean isAutoLogin) {
        LoginBody loginBody = new LoginBody();
        loginBody.setLoginAccount(username);
        loginBody.setPassWord(password);
        loginBody.setDeviceToken("");
        loginBody.setTerminalType(1);
        loginBody.setUserType(5);
        userLoginApi.login(loginBody).compose(this.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissProgress();
                        toastor.showSingletonToast("连接失败，请重试");
                    }

                    @Override
                    public void onWebReturnSucess(User user) {
                        MainAplication.get(ValidateAccountActivity.this).login(user);
                        if (isAutoLogin) {
                            userLoginPreference.setIsAutoLogin(true);
                        } else {
                            userLoginPreference.removeIsAutoLogin();
                        }
                        userLoginPreference.setUserName(username);
                        userLoginPreference.setPassWord(password);
                        EventBus.getDefault().postSticky(new LogStateEvent(true));
                        EventBus.getDefault().postSticky(new RegisterSuccessEvent(true, username, password));
                        dismissProgress();
                        toastor.showSingletonToast("登录成功");
                        finish();
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

    public void setCodeExplainVisibility() {
        binding.codeTitle.setVisibility(View.VISIBLE);
        binding.codeContent.setVisibility(View.VISIBLE);
    }

    public void setSmsCodeBtnState(long countDown) {
        if (countDown <= 0) {
            binding.smsCodeBtn.setEnabled(true);
            binding.smsCodeBtn.setTextColor(getResources().getColor(R.color.colorBlueBtn));
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
            binding.smsCodeBtn.setEnabled(false);
            binding.smsCodeBtn.setTextColor(getResources().getColor(R.color.colorTextaaa));
            binding.smsCodeBtn.setText("正在获取");
            toastor.showSingletonToast("已发送请求");
            querySmsCode();
        }
        if (v == binding.nextBtn) {
            if (binding.smsCodeEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("请输入正确的手机验证码");
                return;
            }
            validatePhoneSmsCode(binding.smsCodeEdit.getText().toString());
        }
    }
}
