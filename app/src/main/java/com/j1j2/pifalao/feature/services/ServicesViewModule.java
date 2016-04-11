package com.j1j2.pifalao.feature.services;

import android.widget.Toast;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-14.
 */
public class ServicesViewModule {

    private List<Module> modules;

    ServicePoint servicePoint;

    ServicePointApi servicePointApi;

    UserVipApi userVipApi;

    UserLoginApi userLoginApi;

    ServicesActivity servicesActivity;
    private Subscription subscription;
    private ServicesAdapter servicesAdapter;

    public ServicesViewModule(ServicesActivity servicesActivity, ServicePointApi servicePointApi, ServicePoint servicePoint, UserVipApi userVipApi, UserLoginApi userLoginApi) {
        this.servicePointApi = servicePointApi;
        this.servicesActivity = servicesActivity;
        this.servicePoint = servicePoint;
        this.userVipApi = userVipApi;
        this.userLoginApi = userLoginApi;
        this.modules = new ArrayList<>();
        this.servicesAdapter = new ServicesAdapter(modules);
    }

    public void queryServicePointServiceModules() {
        servicesActivity.startSearchAnimate();
        servicePointApi.queryServicePointServiceModules(servicePoint.getServicePointId())
                .compose(servicesActivity.<WebReturn<List<Module>>>bindToLifecycle())
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<List<Module>>, Observable<Module>>() {
                    @Override
                    public Observable<Module> call(WebReturn<List<Module>> listWebReturn) {
                        return Observable.from(listWebReturn.getDetail());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Module module = new Module();
                        module.setModuleName("更多");
                        module.setSubscribed(true);
                        module.setWareHouseModuleId(36);
                        module.setModuleType(Constant.ModuleType.MORE);
                        servicesAdapter.add(module);
                        servicesActivity.stopSearchAnimate();
                    }
                })
                .subscribe(new DefaultSubscriber<Module>() {

                    @Override
                    public void onNext(Module module) {
                        super.onNext(module);
                        servicesAdapter.add(module);

                    }
                });
    }


    public void queryQRCode() {
        if (null != subscription && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
        subscription = Observable
                .interval(0, 1, TimeUnit.MINUTES)
                .compose(servicesActivity.<Long>bindToLifecycle())
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
                        servicesActivity.setQRCode(s);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });

    }

    public void updateUserTerminalDetail(final String deviceToken) {
        LoginBody loginBody = new LoginBody();
        loginBody.setTerminalType(1);
        loginBody.setDeviceToken(deviceToken);
        userLoginApi.updateUserTerminalDetail(loginBody)
                .compose(servicesActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void stopQueryQRCode() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    public ServicesAdapter getServicesAdapter() {
        return servicesAdapter;
    }
}
