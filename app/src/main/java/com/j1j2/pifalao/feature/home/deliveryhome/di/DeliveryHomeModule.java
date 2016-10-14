package com.j1j2.pifalao.feature.home.deliveryhome.di;

import android.app.Activity;

import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryHomeActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-30.
 */
@Module
public class DeliveryHomeModule {
    private Activity deliveryHomeActivity;

    public DeliveryHomeModule(Activity deliveryHomeActivity) {
        this.deliveryHomeActivity = deliveryHomeActivity;
    }

    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    Activity deliveryHomeActivity() {
        return deliveryHomeActivity;
    }



}
