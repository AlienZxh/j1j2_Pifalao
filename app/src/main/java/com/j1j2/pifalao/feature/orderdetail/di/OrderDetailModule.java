package com.j1j2.pifalao.feature.orderdetail.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-22.
 */
@Module
public class OrderDetailModule {

    private OrderDetailActivity orderDetailActivity;


    public OrderDetailModule(OrderDetailActivity orderDetailActivity) {
        this.orderDetailActivity = orderDetailActivity;

    }

    @Provides
    @ActivityScope
    UserOrderApi userOrderApi(Retrofit retrofit) {
        return retrofit.create(UserOrderApi.class);
    }

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }


    @Provides
    @ActivityScope
    OrderDetailActivity orderDetailActivity() {
        return orderDetailActivity;
    }

    @Provides
    @ActivityScope
    OrderDetailViewModel orderDetailViewModel(OrderDetailActivity orderDetailActivity, UserOrderApi userOrderApi, ServicePointApi servicePointApi) {
        return new OrderDetailViewModel(orderDetailActivity, userOrderApi, servicePointApi);
    }

}
