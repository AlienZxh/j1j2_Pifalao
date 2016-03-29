package com.j1j2.pifalao.feature.servicepoint;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-25.
 */
public class ServicePointViewModel {

    private ServicePointActivity servicePointActivity;
    private ServicePointApi servicePointApi;
    private ServicePoint servicePoint;
    public ObservableField<String> moduletr = new ObservableField<>();

    public ServicePointViewModel(ServicePointActivity servicePointActivity, ServicePointApi servicePointApi, ServicePoint servicePoint) {
        this.servicePointActivity = servicePointActivity;
        this.servicePointApi = servicePointApi;
        this.servicePoint = servicePoint;
    }

    public ServicePoint getServicePoint() {
        return servicePoint;
    }

    public ServicePointActivity getServicePointActivity() {
        return servicePointActivity;
    }

    public void queryModule() {
        servicePointApi.queryServicePointServiceModules(servicePoint.getServicePointId())
                .compose(servicePointActivity.<WebReturn<List<Module>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<Module>>() {
                    @Override
                    public void onWebReturnSucess(List<Module> modules) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Module module : modules) {
                            stringBuilder.append(module.getModuleName() + "、");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length()-1);
                        stringBuilder.append("等");
                        moduletr.set(stringBuilder.toString());
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
