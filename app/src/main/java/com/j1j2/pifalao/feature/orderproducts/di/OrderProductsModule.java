package com.j1j2.pifalao.feature.orderproducts.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.ordermanager.OrderManagerActivity;
import com.j1j2.pifalao.feature.orderproducts.OrderProductsActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-4-2.
 */
@Module
public class OrderProductsModule {

    private OrderProductsActivity orderProductsActivity;

    public OrderProductsModule(OrderProductsActivity orderProductsActivity) {
        this.orderProductsActivity = orderProductsActivity;
    }

    @Provides
    @ActivityScope
    OrderProductsActivity orderProductsActivity() {
        return orderProductsActivity;
    }
}
