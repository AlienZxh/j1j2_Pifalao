package com.j1j2.pifalao.feature.launch;

import android.widget.Toast;

import com.google.gson.Gson;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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
        userLoginApi.queryUserDetail()
                .compose(launchActivity.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        MainAplication.get(launchActivity).loginOut();
                        while (!launchActivity.isAnimFinish()) {

                        }
                        launchActivity.navigateToGuide();
                    }

                    @Override
                    public void onWebReturnSucess(User user) {
                        if (userLoginPreference.getIsAutoLogin(false))
                            MainAplication.get(launchActivity).login(user);
                        else
                            MainAplication.get(launchActivity).loginOut();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        MainAplication.get(launchActivity).loginOut();
                    }

                    @Override
                    public void onWebReturnCompleted() {
                        while (!launchActivity.isAnimFinish()) {

                        }
                        launchActivity.navigateToGuide();
                    }
                });
    }

}
