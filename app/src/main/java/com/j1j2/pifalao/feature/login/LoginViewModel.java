package com.j1j2.pifalao.feature.login;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-18.
 */
public class LoginViewModel {

    private UserLoginApi userLoginApi;
    private LoginActivity loginActivity;
    private UserLoginPreference userLoginPreference;
    private Gson gson;

    public LoginViewModel(LoginActivity loginActivity, UserLoginApi userLoginApi, UserLoginPreference userLoginPreference, Gson gson) {
        this.userLoginApi = userLoginApi;
        this.loginActivity = loginActivity;
        this.userLoginPreference = userLoginPreference;
        this.gson = gson;

    }

    public void login(@NonNull final String username, @NonNull final String password, final boolean isAutoLogin) {
        loginActivity.showProgress("登录中");
        LoginBody loginBody = new LoginBody();
        loginBody.setLoginAccount(username);
        loginBody.setPassWord(password);
        loginBody.setDeviceToken("");
        loginBody.setTerminalType(1);
        loginBody.setUserType(5);
        userLoginApi.login(loginBody).compose(loginActivity.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        loginActivity.dismissProgress();
                        loginActivity.toastor.showSingletonToast("登录失败");
                    }

                    @Override
                    public void onWebReturnSucess(User user) {
                        if (!user.isMobileVerfied()) {
                            loginActivity.dismissProgress();
                            loginActivity.navigateToValidateAccount();
                            return;
                        }
                        MainAplication.get(loginActivity).login(user);
                        if (isAutoLogin) {
                            userLoginPreference.setIsAutoLogin(true);
                        } else {
                            userLoginPreference.removeIsAutoLogin();
                        }
                        userLoginPreference.setUserName(username);
                        userLoginPreference.setPassWord(password);
                        EventBus.getDefault().postSticky(new LogStateEvent(true));
                        loginActivity.dismissProgress();
                        loginActivity.toastor.showSingletonToast("登录成功");
                        loginActivity.finish();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        loginActivity.dismissProgress();
                        loginActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public LoginActivity getLoginActivity() {
        return loginActivity;
    }
}
