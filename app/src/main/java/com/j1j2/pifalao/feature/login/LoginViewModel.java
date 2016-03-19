package com.j1j2.pifalao.feature.login;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;

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
                    public void onWebReturnSucess(User user) {
                        MainAplication.get(loginActivity).createUserComponent(user);
                        userLoginPreference.setUserInfo(gson.toJson(user));
                        if (isAutoLogin) {
                            userLoginPreference.setIsAutoLogin(true);
                            userLoginPreference.setUserName(username);
                            userLoginPreference.setPassWord(password);
                        } else {
                            userLoginPreference.setUserName(username);
                            userLoginPreference.removeIsAutoLogin().removePassWord();
                        }
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

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
