package com.j1j2.pifalao.feature.register.stepone;


import com.j1j2.data.http.api.ClientRegisterApi;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-28.
 */
public class RegisterStepOneViewModel {
    private RegisterStepOneActivity registerStepOneActivity;
    private ClientRegisterApi clientRegisterApi;

    public RegisterStepOneViewModel(RegisterStepOneActivity registerStepOneActivity, ClientRegisterApi clientRegisterApi) {
        this.registerStepOneActivity = registerStepOneActivity;
        this.clientRegisterApi = clientRegisterApi;
    }

    public void validatePhone(final String phone) {
        clientRegisterApi.validatePhone(phone)
                .compose(registerStepOneActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        registerStepOneActivity.toastor.showSingletonToast(s);

                        querySmsCode(phone);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        registerStepOneActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void countDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .compose(registerStepOneActivity.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        registerStepOneActivity.setSmsCodeBtnState(60 - aLong);
                        if (aLong == 60)
                            onCompleted();
                    }
                });
    }


    public void querySmsCode(final String phone) {
        clientRegisterApi.querySmsCode(phone)
                .compose(registerStepOneActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        registerStepOneActivity.toastor.showSingletonToast(s);
                        countDown();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        registerStepOneActivity.toastor.showSingletonToast(errorMessage);
                        registerStepOneActivity.setSmsCodeBtnState(0);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void validatePhoneSmsCode(String phone, String smsCode) {
        final ClientRegisterStepOneBody clientRegisterStepOneBody = new ClientRegisterStepOneBody();
        clientRegisterStepOneBody.setPhone(phone);
        clientRegisterStepOneBody.setSMSCode(smsCode);
        clientRegisterStepOneBody.setIsAgree(true);
        clientRegisterStepOneBody.setRegisterType(2);
        clientRegisterApi.validatePhoneSmsCode(clientRegisterStepOneBody)
                .compose(registerStepOneActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        registerStepOneActivity.next(clientRegisterStepOneBody);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        registerStepOneActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public RegisterStepOneActivity getRegisterStepOneActivity() {
        return registerStepOneActivity;
    }
}
