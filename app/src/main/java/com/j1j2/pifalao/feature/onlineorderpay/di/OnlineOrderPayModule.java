package com.j1j2.pifalao.feature.onlineorderpay.di;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-5-8.
 */
@Module
public class OnlineOrderPayModule {

    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    ActivityShopCartApi activityShopCartApi(Retrofit retrofit) {
        return retrofit.create(ActivityShopCartApi.class);
    }


    @Provides
    @ActivityScope
    ActivityApi activityApi(Retrofit retrofit) {
        return retrofit.create(ActivityApi.class);
    }

    @Provides
    @ActivityScope
    UserOrderApi userOrderApi(Retrofit retrofit) {
        return retrofit.create(UserOrderApi.class);
    }

    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }
}
