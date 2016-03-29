package com.j1j2.pifalao.feature.register.stepone;

import android.widget.Toast;

import com.j1j2.data.http.api.ClientRegisterApi;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

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
                        Toast.makeText(registerStepOneActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                        querySmsCode(phone);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(registerStepOneActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

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
                        Toast.makeText(registerStepOneActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(registerStepOneActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(registerStepOneActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
