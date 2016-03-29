package com.j1j2.pifalao.feature.confirmorder;

import android.widget.Toast;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.DeliveryServiceTime;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.OrderSubmitBody;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ConfirmOrderSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-27.
 */
public class ConfirmOrderViewModel {
    private ConfirmOrderActivity confirmOrderActivity;
    private ShopCartApi shopCartApi;
    private CountDownApi countDownApi;
    private List<ShopCartItem> shopCartItems;

    public ConfirmOrderViewModel(ConfirmOrderActivity confirmOrderActivity, ShopCartApi shopCartApi, List<ShopCartItem> shopCartItems, CountDownApi countDownApi) {
        this.confirmOrderActivity = confirmOrderActivity;
        this.shopCartApi = shopCartApi;
        this.shopCartItems = shopCartItems;
        this.countDownApi = countDownApi;
    }

    public void commitOrder(int moduleId, int servicepointId, String orderMemo, String predictSendTime) {
        OrderSubmitBody orderSubmitBody = new OrderSubmitBody();
        orderSubmitBody.setModuleId(moduleId);
        orderSubmitBody.setServicePointId(servicepointId);
        orderSubmitBody.setOrderMemo(orderMemo);
        orderSubmitBody.setPredictSendTime(predictSendTime);
        orderSubmitBody.setFreightType(2);
        shopCartApi.submitOrder(orderSubmitBody)
                .compose(confirmOrderActivity.<WebReturn<Integer>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<Integer>() {
                    @Override
                    public void onWebReturnSucess(Integer orderId) {
                        confirmOrderActivity.clearShopCart();
                        EventBus.getDefault().post(new ConfirmOrderSuccessEvent());
                        confirmOrderActivity.navigateToSuccess(orderId);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(confirmOrderActivity, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryDeliveryTime(int moduleId) {
        countDownApi.queryServiceTimeOfDliveryType(2, moduleId)
                .compose(confirmOrderActivity.<WebReturn<List<DeliveryServiceTime>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<DeliveryServiceTime>>() {
                    @Override
                    public void onWebReturnSucess(List<DeliveryServiceTime> deliveryServiceTimes) {
                        confirmOrderActivity.initTimePicker(deliveryServiceTimes);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public ConfirmOrderActivity getConfirmOrderActivity() {
        return confirmOrderActivity;
    }
}
