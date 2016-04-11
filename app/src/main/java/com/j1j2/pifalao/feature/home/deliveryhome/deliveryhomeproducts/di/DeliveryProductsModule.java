package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.DeliveryHomeProductsFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.DeliveryProductsViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-8.
 */
@Module
public class DeliveryProductsModule {

    private DeliveryHomeProductsFragment deliveryHomeProductsFragment;

    public DeliveryProductsModule(DeliveryHomeProductsFragment deliveryHomeProductsFragment) {
        this.deliveryHomeProductsFragment = deliveryHomeProductsFragment;
    }

    @Provides
    @ActivityScope
    DeliveryHomeProductsFragment deliveryHomeProductsFragment() {
        return deliveryHomeProductsFragment;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    DeliveryProductsViewModel deliveryProductsViewModel(DeliveryHomeProductsFragment deliveryHomeProductsFragment, ProductApi productApi,ShopCartApi shopCartApi) {
        return new DeliveryProductsViewModel(deliveryHomeProductsFragment, productApi,shopCartApi);
    }

}
