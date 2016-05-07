package com.j1j2.pifalao.feature.orderrate.di;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-27.
 */
@Module
public class OrderRateModule {

    @Provides
    @ActivityScope
    UserOrderApi userOrderApi(Retrofit retrofit) {
        return retrofit.create(UserOrderApi.class);
    }
}
