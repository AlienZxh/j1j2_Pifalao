package com.j1j2.pifalao.feature.confirmorder;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.Address;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.DeliveryServiceTime;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.SubmitOrderReturn;
import com.j1j2.data.model.UserDeliveryTime;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.OrderSubmitBody;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ConfirmOrderSuccessEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.app.service.BackGroundService;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

    public ObservableField<String> hour = new ObservableField<>();
    public ObservableField<String> minute = new ObservableField<>();
    public ObservableField<String> second = new ObservableField<>();

    long remian = 0;

    public ConfirmOrderViewModel(ConfirmOrderActivity confirmOrderActivity, ShopCartApi shopCartApi, CountDownApi countDownApi, UserCouponApi userCouponApi, UserAddressApi userAddressApi, List<ShopCartItem> shopCartItems) {
        this.confirmOrderActivity = confirmOrderActivity;
        this.shopCartApi = shopCartApi;
        this.countDownApi = countDownApi;
        this.userCouponApi = userCouponApi;
        this.userAddressApi = userAddressApi;
        this.shopCartItems = shopCartItems;
    }

    public void commitOrder(final OrderSubmitState orderSubmitState) {
        confirmOrderActivity.showProgress("订单提交中");
        OrderSubmitBody orderSubmitBody = new OrderSubmitBody();
        orderSubmitBody.setModuleId(orderSubmitState.ModuleId);
        orderSubmitBody.setOrderPayType(orderSubmitState.OrderPayType.get());
        orderSubmitBody.setFreightID(orderSubmitState.FreightTypeDetail.get().getId());

        orderSubmitBody.setServicePointId(orderSubmitState.ServicePointDetail.get() == null ? 0 : orderSubmitState.ServicePointDetail.get().getServicePointId());

        if (orderSubmitState.AddressDetail.get() != null)
            orderSubmitBody.setAddressId("" + orderSubmitState.AddressDetail.get().getAddressId());

        orderSubmitBody.setCouponCode(orderSubmitState.Coupon.get() == null ? "" : orderSubmitState.Coupon.get().getCouponCode());

        orderSubmitBody.setPredictSendTime(orderSubmitState.PredictSendTime.get());
        orderSubmitBody.setOrderMemo(orderSubmitState.OrderMemo);
        shopCartApi.submitOrder(orderSubmitBody)
                .compose(confirmOrderActivity.<WebReturn<SubmitOrderReturn>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<SubmitOrderReturn>() {
                    @Override
                    public void onWebReturnSucess(SubmitOrderReturn submitOrderReturn) {
                        confirmOrderActivity.dismissProgress();
                        confirmOrderActivity.toastor.getSingletonToast("订单提交成功");
                        BackGroundService.updateUnRead(confirmOrderActivity);
                        confirmOrderActivity.clearShopCart();
                        EventBus.getDefault().post(new ConfirmOrderSuccessEvent());
                        EventBus.getDefault().post(new ShopCartChangeEvent());
//                        confirmOrderActivity.navigateToSuccess(orderId);
                        if (orderSubmitState.OrderPayType.get() == Constant.OrderPayType.CASHONDELIVERY)
                            confirmOrderActivity.navigateToOrderDetail(submitOrderReturn.getOrderId());
                        else
                            confirmOrderActivity.navigateToOnlineOrderPay(submitOrderReturn.getOrderId(), submitOrderReturn.getOrderNO());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        confirmOrderActivity.dismissProgress();
                        confirmOrderActivity.toastor.showSingletonToast(errorMessage);
                        confirmOrderActivity.setConfirmBtnEnable(true);
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

    public void CountDown(int moduleId) {
        countDownApi.QueryDeliveryCountDownOfModule(moduleId)
                .compose(confirmOrderActivity.<WebReturn<UserDeliveryTime>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<UserDeliveryTime>, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(WebReturn<UserDeliveryTime> userDeliveryTimeWebReturn) {

                        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date endDate = simple.parse(userDeliveryTimeWebReturn.getDetail().getYear()
                                    + "-" + userDeliveryTimeWebReturn.getDetail().getMonth()
                                    + "-" + userDeliveryTimeWebReturn.getDetail().getDay()
                                    + " " + userDeliveryTimeWebReturn.getDetail().getTimeSpan() + ":00");
                            Date beginDate = simple.parse(userDeliveryTimeWebReturn.getDetail().getNow());
                            remian = endDate.getTime() - beginDate.getTime();
                        } catch (ParseException e) {

                        }
                        return Observable.interval(1, TimeUnit.SECONDS);
                    }
                })
                .compose(confirmOrderActivity.<Long>bindToLifecycle())
                .observeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        remian -= 1000;
                        initDate(remian);
                    }
                });

    }

    private void initDate(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + days * 24;
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        hour.set(hours < 10 ? ("0" + hours) : ("" + hours));
        minute.set(minutes < 10 ? ("0" + minutes) : ("" + minutes));
        second.set(seconds < 10 ? ("0" + seconds) : ("" + seconds));
    }

    public ConfirmOrderActivity getConfirmOrderActivity() {
        return confirmOrderActivity;
    }
}
