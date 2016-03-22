package com.j1j2.pifalao.feature.qrcode;

import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-22.
 */
public class QrCodeViewModel {
    private QrCodeActivity qrCodeActivity;
    private UserVipApi userVipApi;

    private Subscription subscription;

    public QrCodeViewModel(QrCodeActivity qrCodeActivity, UserVipApi userVipApi) {
        this.qrCodeActivity = qrCodeActivity;
        this.userVipApi = userVipApi;
    }

    public void queryQRCode() {

        Observable
                .interval(0, 1, TimeUnit.MINUTES)
                .compose(qrCodeActivity.<Long>bindToLifecycle())
                .flatMap(new Func1<Long, Observable<WebReturn<String>>>() {
                    @Override
                    public Observable<WebReturn<String>> call(Long aLong) {
                        return userVipApi.queryLoginDimensionalCode();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        qrCodeActivity.initQRCode(s);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });

    }


}
