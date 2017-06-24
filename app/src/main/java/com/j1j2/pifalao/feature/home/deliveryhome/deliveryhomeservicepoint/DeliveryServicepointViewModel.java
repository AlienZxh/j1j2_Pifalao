package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-8.
 */
public class DeliveryServicepointViewModel {
    private DeliveryHomeServicepointFragment deliveryHomeServicepointFragment;
    private ServicePointApi servicePointApi;
    public ObservableField<String> moduletr = new ObservableField<>();

    public DeliveryServicepointViewModel(DeliveryHomeServicepointFragment deliveryHomeServicepointFragment, ServicePointApi servicePointApi) {
        this.deliveryHomeServicepointFragment = deliveryHomeServicepointFragment;
        this.servicePointApi = servicePointApi;

    }


    public void queryModule(int shopId) {
        servicePointApi.queryServicePointServiceModules(shopId)
                .compose(deliveryHomeServicepointFragment.<WebReturn<List<ShopSubscribeService>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ShopSubscribeService>>() {
                    @Override
                    public void onWebReturnSucess(List<ShopSubscribeService> shopSubscribeServices) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (ShopSubscribeService shopSubscribeService : shopSubscribeServices) {
                            if (shopSubscribeService.isSubscribed())
                                stringBuilder.append(shopSubscribeService.getName() + "、");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
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

    public DeliveryHomeServicepointFragment getDeliveryHomeServicepointFragment() {
        return deliveryHomeServicepointFragment;
    }
}
