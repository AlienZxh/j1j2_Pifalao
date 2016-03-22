package com.j1j2.pifalao.feature.orders.di;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.orders.OrdersActivity;
import com.j1j2.pifalao.feature.orders.OrdersViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-22.
 */
@Module
public class OrdersModule {

    private OrdersActivity ordersActivity;
    private int orderType;

    public OrdersModule(OrdersActivity ordersActivity, int orderType) {
        this.ordersActivity = ordersActivity;
        this.orderType = orderType;
    }

    @Provides
    @ActivityScope
    UserOrderApi userOrderApi(Retrofit retrofit) {
        return retrofit.create(UserOrderApi.class);
    }

    @Provides
    @ActivityScope
    OrdersActivity ordersActivity() {
        return ordersActivity;
    }

    @Provides
    @ActivityScope
    int orderType() {
        return orderType;
    }

    @Provides
    @ActivityScope
    OrdersViewModel ordersViewModel(OrdersActivity ordersActivity, UserOrderApi userOrderApi, int orderType) {
        return new OrdersViewModel(ordersActivity, userOrderApi, orderType);
    }


}
