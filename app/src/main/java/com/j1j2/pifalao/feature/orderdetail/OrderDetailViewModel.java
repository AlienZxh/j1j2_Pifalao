package com.j1j2.pifalao.feature.orderdetail;

import android.databinding.ObservableField;
import android.widget.Toast;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.OrderCancelEvent;

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

    public ObservableField<ServicePoint> servicePointObservableField = new ObservableField<>();
    public ObservableField<OrderSimple> orderDetailObservableField = new ObservableField<>();

    public OrderDetailViewModel(OrderDetailActivity orderDetailActivity, UserOrderApi userOrderApi, ServicePointApi servicePointApi) {
        this.orderDetailActivity = orderDetailActivity;
        this.userOrderApi = userOrderApi;
        this.servicePointApi = servicePointApi;

    }

    public void queryOrderDetail(int orderId) {
        userOrderApi.queryOrderByOrderId("" + orderId)
                .compose(orderDetailActivity.<WebReturn<OrderSimple>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderSimple>() {
                    @Override
                    public void onWebReturnSucess(OrderSimple orderSimple) {
                        orderDetailObservableField.set(orderSimple);
                        orderProductsAdapter = new OrderProductsAdapter(orderSimple.getProductDetails());
                        orderDetailActivity.setOrderProductListAdapter(orderProductsAdapter);
                        queryServiceoint(orderSimple.getServicePointId());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });

    }

    public void queryServiceoint(int servicepointId) {
        servicePointApi.queryServicePointById(servicepointId)
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


    public void cancleOrder(int orderId) {
        userOrderApi.cancleOrder(orderId)
                .compose(orderDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        Toast.makeText(orderDetailActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new OrderCancelEvent());
                        orderDetailActivity.finish();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(orderDetailActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void setOrderDetailObservableField(OrderSimple orderSimple) {
        this.orderDetailObservableField.set(orderSimple);
    }

    public OrderDetailActivity getOrderDetailActivity() {
        return orderDetailActivity;
    }
}
