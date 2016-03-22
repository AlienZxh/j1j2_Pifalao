package com.j1j2.pifalao.feature.confirmorder.di;

import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-21.
 */
@Module
public class ConfirmOrderModule {
    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    UserAddressApi userAddressApi(Retrofit retrofit) {
        return retrofit.create(UserAddressApi.class);
    }
}
