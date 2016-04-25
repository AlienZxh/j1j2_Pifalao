package com.j1j2.pifalao.feature.findpsw;

import com.j1j2.data.http.api.FindPwdBackApi;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.FindPwdStepOneBody;
import com.j1j2.data.model.requestbody.FindPwdStepTwoBody;
import com.j1j2.data.model.requestbody.RestUserPwdBody;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-22.
 */
public class FindPSWViewModel {
    private FindPSWActivity findPSWActivity;
    private FindPwdBackApi findPwdBackApi;


    public FindPSWViewModel(FindPSWActivity findPSWActivity, FindPwdBackApi findPwdBackApi) {
        this.findPSWActivity = findPSWActivity;
        this.findPwdBackApi = findPwdBackApi;

    }

    public void countDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .compose(findPSWActivity.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        findPSWActivity.setSmsCodeBtnState(60 - aLong);
                        if (aLong == 60)
                            onCompleted();
                    }
                });
    }

    public void querySmsCode(String phone) {
        findPwdBackApi.querySmsCode(phone)
                .compose(findPSWActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        findPSWActivity.toastor.showSingletonToast("验证码发送成功");
                        countDown();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        findPSWActivity.toastor.showSingletonToast(errorMessage);
                        findPSWActivity.setSmsCodeBtnState(0);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void findPassWordStepTwo(final String phone, String smscode, final String psw, final String confirmpsw) {
        FindPwdStepTwoBody findPwdStepTwoBody = new FindPwdStepTwoBody();
        findPwdStepTwoBody.setPhone(phone);
        findPwdStepTwoBody.setSMSCode(smscode);
        findPwdBackApi.findPassWordStepTwo(findPwdStepTwoBody)
                .compose(findPSWActivity.<WebReturn<Integer>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<Integer>() {
                    @Override
                    public void onWebReturnSucess(Integer s) {
                        findPassWordStepThird(phone, s, psw, confirmpsw);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        findPSWActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void findPassWordStepThird(String phone, int registerId, final String psw, String confirmpsw) {
        RestUserPwdBody restUserPwdBody = new RestUserPwdBody();
        restUserPwdBody.setPhone(phone);
        restUserPwdBody.setRegisterId(registerId);
        restUserPwdBody.setNewPwd(psw);
        restUserPwdBody.setConfrimPwd(confirmpsw);

        findPwdBackApi.findPassWordStepThird(restUserPwdBody)
                .compose(findPSWActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        findPSWActivity.toastor.showSingleLongToast("密码重置成功，新密码为：" + psw);
                        findPSWActivity.finish();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        findPSWActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {
                    }
                });
    }


    public FindPSWActivity getFindPSWActivity() {
        return findPSWActivity;
    }
}
