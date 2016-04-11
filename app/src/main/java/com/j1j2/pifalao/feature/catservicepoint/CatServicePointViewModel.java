package com.j1j2.pifalao.feature.catservicepoint;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-29.
 */
public class CatServicePointViewModel {
    private CatServicePointActivity catServicePointActivity;
    private ServicePointApi servicePointApi;

    public ObservableField<ServicePoint> servicePointObservableField = new ObservableField<>();

    public CatServicePointViewModel(CatServicePointActivity catServicePointActivity, ServicePointApi servicePointApi) {
        this.catServicePointActivity = catServicePointActivity;
        this.servicePointApi = servicePointApi;
    }

    public void queryServicePoint(int servicePointId) {
        servicePointApi.queryServicePointById(servicePointId)
                .compose(catServicePointActivity.<WebReturn<ServicePoint>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ServicePoint>() {
                    @Override
                    public void onWebReturnSucess(ServicePoint servicePoint) {
                        servicePointObservableField.set(servicePoint);
                        catServicePointActivity.addServicepointOverlay(servicePoint);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public CatServicePointActivity getCatServicePointActivity() {
        return catServicePointActivity;
    }
}
