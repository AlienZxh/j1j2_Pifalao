package com.j1j2.pifalao.feature.orderdetail;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderDetailViewModel {
    private OrderDetailActivity orderDetailActivity;
    private UserOrderApi userOrderApi;
    private ServicePointApi servicePointApi;
    private OrderSimple orderSimple;
    private OrderProductsAdapter orderProductsAdapter;

    public ObservableField<ServicePoint> servicePointObservableField = new ObservableField<>();
    public ObservableField<OrderDetail> orderDetailObservableField = new ObservableField<>();

    public OrderDetailViewModel(OrderDetailActivity orderDetailActivity, UserOrderApi userOrderApi, ServicePointApi servicePointApi, OrderSimple orderSimple) {
        this.orderDetailActivity = orderDetailActivity;
        this.userOrderApi = userOrderApi;
        this.servicePointApi = servicePointApi;
        this.orderSimple = orderSimple;
    }

    public void queryOrderDetail() {
        userOrderApi.queryOrderByOrderId("" + orderSimple.getOrderId())
                .compose(orderDetailActivity.<WebReturn<OrderDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderDetail>() {
                    @Override
                    public void onWebReturnSucess(OrderDetail orderDetail) {
                        orderDetailObservableField.set(orderDetail);
                        orderProductsAdapter = new OrderProductsAdapter(orderDetail.getProductDetails());
                        orderDetailActivity.setOrderProductListAdapter(orderProductsAdapter);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
        servicePointApi.queryServicePointById(orderSimple.getServicePoint())
                .compose(orderDetailActivity.<WebReturn<ServicePoint>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ServicePoint>() {
                    @Override
                    public void onWebReturnSucess(ServicePoint mServicePoint) {
                        servicePointObservableField.set(mServicePoint);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public OrderSimple getOrderSimple() {
        return orderSimple;
    }
}
