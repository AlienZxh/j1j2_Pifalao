package com.j1j2.pifalao.feature.home.deliveryhome.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryHomeActivity;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryHomeViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-30.
 */
@Module
public class DeliveryHomeModule {
    private DeliveryHomeActivity deliveryHomeActivity;

    public DeliveryHomeModule(DeliveryHomeActivity deliveryHomeActivity) {
        this.deliveryHomeActivity = deliveryHomeActivity;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    DeliveryHomeActivity deliveryHomeActivity() {
        return deliveryHomeActivity;
    }

    @Provides
    @ActivityScope
    DeliveryHomeViewModel deliveryHomeViewModel(DeliveryHomeActivity deliveryHomeActivity) {
        return new DeliveryHomeViewModel(deliveryHomeActivity);
    }

}
