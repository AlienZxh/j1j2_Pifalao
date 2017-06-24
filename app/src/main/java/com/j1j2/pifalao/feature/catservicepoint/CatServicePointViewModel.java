package com.j1j2.pifalao.feature.catservicepoint;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Shop;
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

    public ObservableField<Shop> servicePointObservableField = new ObservableField<>();

    public CatServicePointViewModel(CatServicePointActivity catServicePointActivity, ServicePointApi servicePointApi) {
        this.catServicePointActivity = catServicePointActivity;
        this.servicePointApi = servicePointApi;
    }

    public void queryShop(int shopId) {
        servicePointApi.queryShopById(shopId)
                .compose(catServicePointActivity.<WebReturn<Shop>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<Shop>() {
                    @Override
                    public void onWebReturnSucess(Shop shop) {
                        servicePointObservableField.set(shop);
                        catServicePointActivity.addServicepointOverlay(shop);
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
