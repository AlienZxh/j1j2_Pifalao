package com.j1j2.pifalao.feature.launch;

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

import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-19.
 */
public class LaunchViewModel {

    private UserLoginApi userLoginApi;
    private LaunchActivity launchActivity;
    private Gson gson;
    private UserLoginPreference userLoginPreference;

    public LaunchViewModel(UserLoginApi userLoginApi, LaunchActivity launchActivity, Gson gson, UserLoginPreference userLoginPreference) {
        this.userLoginApi = userLoginApi;
        this.launchActivity = launchActivity;
        this.gson = gson;
        this.userLoginPreference = userLoginPreference;
    }

    public void initLoginState() {
        LoginBody loginBody = new LoginBody();
        loginBody.setLoginAccount(userLoginPreference.getUsername(""));
        loginBody.setPassWord(userLoginPreference.getPassWord(""));
        loginBody.setUserType(5);
        loginBody.setTerminalType(1);
        userLoginApi.login(loginBody)
                .compose(launchActivity.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        MainAplication.get(launchActivity).loginOut();
                        EventBus.getDefault().postSticky(new LogStateEvent(false));
                        while (!launchActivity.isAnimFinish()) {

                        }
                        launchActivity.navigateTo();
                    }

                    @Override
                    public void onWebReturnSucess(User user) {
                        if (userLoginPreference.getIsAutoLogin(false)) {
                            MainAplication.get(launchActivity).login(user);
                            EventBus.getDefault().postSticky(new LogStateEvent(true));
                        } else {
                            MainAplication.get(launchActivity).loginOut();
                            EventBus.getDefault().postSticky(new LogStateEvent(false));
                        }
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        MainAplication.get(launchActivity).loginOut();
                        EventBus.getDefault().postSticky(new LogStateEvent(false));
                    }

                    @Override
                    public void onWebReturnCompleted() {
                        while (!launchActivity.isAnimFinish()) {

                        }
                        launchActivity.navigateTo();
                    }
                });
    }

}
