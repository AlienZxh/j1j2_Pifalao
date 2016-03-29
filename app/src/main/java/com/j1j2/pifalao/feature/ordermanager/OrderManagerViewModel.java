package com.j1j2.pifalao.feature.ordermanager;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderStatistics;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderManagerViewModel {
    private OrderManagerActivity orderManagerActivity;
    private UserOrderApi userOrderApi;
    public ObservableField<OrderStatistics> statisticsObservableField = new ObservableField<>();

    public OrderManagerViewModel(OrderManagerActivity orderManagerActivity, UserOrderApi userOrderApi) {
        this.orderManagerActivity = orderManagerActivity;
        this.userOrderApi = userOrderApi;
    }

    public void queryOrderStatistics() {
        userOrderApi.queryProductOrderStatistics()
                .compose(orderManagerActivity.<WebReturn<OrderStatistics>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderStatistics>() {
                    @Override
                    public void onWebReturnSucess(OrderStatistics orderStatistics) {
                        statisticsObservableField.set(orderStatistics);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public OrderManagerActivity getOrderManagerActivity() {
        return orderManagerActivity;
    }
}
