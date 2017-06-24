package com.j1j2.pifalao.feature.orderdetail;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderDetailViewModel {
    private OrderDetailActivity orderDetailActivity;
    private UserOrderApi userOrderApi;
    private ServicePointApi servicePointApi;

    private OrderProductsAdapter orderProductsAdapter;


    public ObservableField<OrderDetail> orderDetailObservableField = new ObservableField<>();

    public OrderDetailViewModel(OrderDetailActivity orderDetailActivity, UserOrderApi userOrderApi, ServicePointApi servicePointApi) {
        this.orderDetailActivity = orderDetailActivity;
        this.userOrderApi = userOrderApi;
        this.servicePointApi = servicePointApi;

    }

    public void queryOrderDetail(int orderId) {
        userOrderApi.queryOrderByOrderId("" + orderId)
                .compose(orderDetailActivity.<WebReturn<OrderDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderDetail>() {
                    @Override
                    public void onWebReturnSucess(OrderDetail orderDetail) {
                        orderDetailObservableField.set(orderDetail);
                        orderProductsAdapter = new OrderProductsAdapter(orderDetail.getOrderProductsInfo());
                        orderDetailActivity.initFragment();

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });

    }


    public void receiveOrder(int orderId) {
        userOrderApi.confrimReceive(orderId)
                .compose(orderDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        orderDetailActivity.toastor.showSingletonToast("确认收货成功");
                        EventBus.getDefault().post(new OrderStateChangeEvent(false, Constant.OrderType.ORDERTYPE_DELIVERY, Constant.OrderType.ORDERTYPE_WAITFORRATE));

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        orderDetailActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void cancleOrder(int orderId) {
        userOrderApi.cancleOrder(orderId)
                .compose(orderDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {

                        orderDetailActivity.toastor.showSingletonToast(s);
                        EventBus.getDefault().post(new OrderStateChangeEvent(false, Constant.OrderType.ORDERTYPE_SUBMIT, Constant.OrderType.ORDERTYPE_INVALID));
                        orderDetailActivity.finish();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        orderDetailActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

}
