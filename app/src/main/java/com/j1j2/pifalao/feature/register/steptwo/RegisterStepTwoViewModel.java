package com.j1j2.pifalao.feature.register.steptwo;


import com.j1j2.data.http.api.ClientRegisterApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.RegisterSuccessEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-28.
 */
public class RegisterStepTwoViewModel {

    private RegisterStepTwoActivity registerStepTwoActivity;
    private ClientRegisterApi clientRegisterApi;
    private UserLoginApi userLoginApi;

    private UserLoginPreference userLoginPreference;

    public RegisterStepTwoViewModel(RegisterStepTwoActivity registerStepTwoActivity, ClientRegisterApi clientRegisterApi, UserLoginApi userLoginApi, UserLoginPreference userLoginPreference) {
        this.registerStepTwoActivity = registerStepTwoActivity;
        this.clientRegisterApi = clientRegisterApi;
        this.userLoginApi = userLoginApi;
        this.userLoginPreference = userLoginPreference;
    }

    public void clientRegisterStepOne(final ClientRegisterStepOneBody clientRegisterStepOneBody) {
        registerStepTwoActivity.showProgress("注册中");
        clientRegisterApi.clientRegisterStepOne(clientRegisterStepOneBody)
                .compose(registerStepTwoActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        login(clientRegisterStepOneBody);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        registerStepTwoActivity.dismissProgress();
                        registerStepTwoActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerStepTwoActivity.dismissProgress();
                        registerStepTwoActivity.toastor.showSingletonToast("连接失败，请重试");
                    }
                });
    }

    public void login(final ClientRegisterStepOneBody clientRegisterStepOneBody) {
        LoginBody loginBody = new LoginBody();
        loginBody.setLoginAccount(clientRegisterStepOneBody.getPhone());
        loginBody.setPassWord(clientRegisterStepOneBody.getPassWord());
        loginBody.setDeviceToken("");
        loginBody.setTerminalType(1);
        loginBody.setUserType(5);
        userLoginApi.login(loginBody).compose(registerStepTwoActivity.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onWebReturnSucess(User user) {
                        registerStepTwoActivity.dismissProgress();
                        registerStepTwoActivity.toastor.showSingletonToast("注册成功");
                        MainAplication.get(registerStepTwoActivity).login(user);
                        userLoginPreference.setIsAutoLogin(true);
                        userLoginPreference.setUserName(clientRegisterStepOneBody.getPhone());
                        userLoginPreference.setPassWord(clientRegisterStepOneBody.getPassWord());
                        EventBus.getDefault().postSticky(new LogStateEvent(true));
                        EventBus.getDefault().post(new RegisterSuccessEvent(true, clientRegisterStepOneBody.getPhone(), clientRegisterStepOneBody.getPassWord()));
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        registerStepTwoActivity.dismissProgress();
                        registerStepTwoActivity.toastor.showSingletonToast("注册成功");
                        EventBus.getDefault().post(new RegisterSuccessEvent(false, clientRegisterStepOneBody.getPhone(), clientRegisterStepOneBody.getPassWord()));
                    }

                    @Override
                    public void onWebReturnCompleted() {
                        registerStepTwoActivity.completeRegister();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerStepTwoActivity.dismissProgress();
                        registerStepTwoActivity.toastor.showSingletonToast("连接失败，请重试");
                    }
                });
    }

    public RegisterStepTwoActivity getRegisterStepTwoActivity() {
        return registerStepTwoActivity;
    }
}
