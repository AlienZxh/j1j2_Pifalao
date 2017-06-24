package com.j1j2.pifalao.feature.home.storestylehome.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeFragment;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-14.
 */
@Module
public class StoreStyleHomeModule {

    private StoreStyleHomeFragment storeStyleHomeFragment;
    private ShopSubscribeService shopSubscribeService;

    public StoreStyleHomeModule(StoreStyleHomeFragment storeStyleHomeFragment, ShopSubscribeService shopSubscribeService) {
        this.storeStyleHomeFragment = storeStyleHomeFragment;
        this.shopSubscribeService = shopSubscribeService;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    StoreStyleHomeFragment storeStyleHomeFragment() {
        return storeStyleHomeFragment;
    }

    @Provides
    @ActivityScope
    ShopSubscribeService module() {
        return shopSubscribeService;
    }

    @Provides
    @ActivityScope
    StoreStyleHomeViewModel storeStyleHomeViewModel(StoreStyleHomeFragment storeStyleHomeFragment, ProductApi productApi, ShopSubscribeService shopSubscribeService) {
        return new StoreStyleHomeViewModel(storeStyleHomeFragment, productApi, shopSubscribeService);
    }


}
