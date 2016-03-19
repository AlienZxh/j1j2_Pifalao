package com.j1j2.pifalao.feature.services;

import android.widget.Toast;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.feature.servicepoint.ServicePointActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
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

    ServicesActivity servicesActivity;

    private ServicesAdapter servicesAdapter;

    public ServicesViewModule(ServicesActivity servicesActivity, ServicePointApi servicePointApi, ServicePoint servicePoint) {
        this.servicePointApi = servicePointApi;
        this.servicesActivity = servicesActivity;
        this.servicePoint = servicePoint;
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


    public ServicesAdapter getServicesAdapter() {
        return servicesAdapter;
    }
}
