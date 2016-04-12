package com.j1j2.pifalao.feature.home.deliveryhome;

import com.baidu.platform.comapi.map.A;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-30.
 */
public class DeliveryHomeViewModel {

    private DeliveryHomeActivity deliveryHomeActivity;
    private ShopCartApi shopCartApi;

    public DeliveryHomeViewModel(DeliveryHomeActivity deliveryHomeActivity, ShopCartApi shopCartApi) {
        this.deliveryHomeActivity = deliveryHomeActivity;
        this.shopCartApi = shopCartApi;
    }

    public void queryFreightType(int moduleId) {
        shopCartApi.queryValidFreight(moduleId)
                .compose(deliveryHomeActivity.<WebReturn<List<FreightType>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<FreightType>>() {
                    @Override
                    public void onWebReturnSucess(List<FreightType> freightTypes) {
                        deliveryHomeActivity.initFreightType(freightTypes.get(0));
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public DeliveryHomeActivity getDeliveryHomeActivity() {
        return deliveryHomeActivity;
    }
}
