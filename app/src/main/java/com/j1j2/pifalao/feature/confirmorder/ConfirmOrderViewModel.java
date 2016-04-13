package com.j1j2.pifalao.feature.confirmorder;

import android.widget.Toast;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.Address;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.DeliveryServiceTime;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.OrderSubmitBody;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ConfirmOrderSuccessEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-27.
 */
public class ConfirmOrderViewModel {
    private ConfirmOrderActivity confirmOrderActivity;
    private ShopCartApi shopCartApi;
    private CountDownApi countDownApi;
    private UserCouponApi userCouponApi;
    private UserAddressApi userAddressApi;
    private List<ShopCartItem> shopCartItems;

    public ConfirmOrderViewModel(ConfirmOrderActivity confirmOrderActivity, ShopCartApi shopCartApi, CountDownApi countDownApi, UserCouponApi userCouponApi, UserAddressApi userAddressApi, List<ShopCartItem> shopCartItems) {
        this.confirmOrderActivity = confirmOrderActivity;
        this.shopCartApi = shopCartApi;
        this.countDownApi = countDownApi;
        this.userCouponApi = userCouponApi;
        this.userAddressApi = userAddressApi;
        this.shopCartItems = shopCartItems;
    }

    public void commitOrder(OrderSubmitState orderSubmitState) {
        OrderSubmitBody orderSubmitBody = new OrderSubmitBody();
        orderSubmitBody.setModuleId(orderSubmitState.ModuleId);
        orderSubmitBody.setFreightID(orderSubmitState.FreightTypeDetail.get().getId());

        orderSubmitBody.setServicePointId(orderSubmitState.ServicePointDetail.get() == null ? 0 : orderSubmitState.ServicePointDetail.get().getServicePointId());
        orderSubmitBody.setAddressId(orderSubmitState.AddressDetail.get() == null ? 0 : orderSubmitState.AddressDetail.get().getAddressId());

        orderSubmitBody.setCouponCode(orderSubmitState.Coupon.get() == null ? "" : orderSubmitState.Coupon.get().getCouponCode());

        orderSubmitBody.setPredictSendTime(orderSubmitState.PredictSendTime.get());
        orderSubmitBody.setOrderMemo(orderSubmitState.OrderMemo);
        shopCartApi.submitOrder(orderSubmitBody)
                .compose(confirmOrderActivity.<WebReturn<Integer>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<Integer>() {
                    @Override
                    public void onWebReturnSucess(Integer orderId) {
                        confirmOrderActivity.clearShopCart();
                        EventBus.getDefault().post(new ConfirmOrderSuccessEvent());
                        EventBus.getDefault().post(new ShopCartChangeEvent());
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

    public void queryDeliveryTime(final OrderSubmitState orderSubmitState) {
        countDownApi.queryServiceTimeOfDliveryType(orderSubmitState.FreightTypeDetail.get().getId(), orderSubmitState.ModuleId)
                .compose(confirmOrderActivity.<WebReturn<List<DeliveryServiceTime>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<DeliveryServiceTime>>() {
                    @Override
                    public void onWebReturnSucess(List<DeliveryServiceTime> deliveryServiceTimes) {
                        confirmOrderActivity.initTimePicker(orderSubmitState.FreightTypeDetail.get().getSysDeliveryType(), deliveryServiceTimes);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryFreightType(int moduleId) {
        shopCartApi.queryValidFreight(moduleId)
                .compose(confirmOrderActivity.<WebReturn<List<FreightType>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<FreightType>>() {
                    @Override
                    public void onWebReturnSucess(List<FreightType> freightTypes) {
                        confirmOrderActivity.initFreightType(freightTypes);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryCoupons(int couponType, int moduleId) {
        userCouponApi.queryUserCoupon(couponType, "" + moduleId)
                .compose(confirmOrderActivity.<WebReturn<List<Coupon>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<Coupon>>() {
                    @Override
                    public void onWebReturnSucess(List<Coupon> couponList) {
                        confirmOrderActivity.initCoupons(couponList);

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryDefaultAddress() {
        userAddressApi.queryUserDefaultAddress()
                .compose(confirmOrderActivity.<WebReturn<Address>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<Address>() {
                    @Override
                    public void onWebReturnSucess(Address address) {
                        confirmOrderActivity.initAddress(address);
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
