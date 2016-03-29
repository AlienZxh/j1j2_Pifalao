package com.j1j2.pifalao.feature.ordermanager.di;

import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.ordermanager.OrderManagerActivity;
import com.j1j2.pifalao.feature.ordermanager.OrderManagerViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-22.
 */
@Module
public class OrderManagerModule {

    private OrderManagerActivity orderManagerActivity;

    public OrderManagerModule(OrderManagerActivity orderManagerActivity) {
        this.orderManagerActivity = orderManagerActivity;
    }

    @Provides
    @ActivityScope
    UserOrderApi userOrderApi(Retrofit retrofit) {
        return retrofit.create(UserOrderApi.class);
    }

    @Provides
    @ActivityScope
    OrderManagerActivity orderManagerActivity() {
        return orderManagerActivity;
    }

    @Provides
    @ActivityScope
    OrderManagerViewModel orderManagerViewModel(OrderManagerActivity orderManagerActivity,UserOrderApi userOrderApi) {
        return new OrderManagerViewModel(orderManagerActivity,userOrderApi);
    }

}
