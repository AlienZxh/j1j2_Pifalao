package com.j1j2.pifalao.feature.home.storestylehome.di;

import com.j1j2.data.http.api.ProductApi;
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
    private com.j1j2.data.model.Module module;

    public StoreStyleHomeModule(StoreStyleHomeFragment storeStyleHomeFragment, com.j1j2.data.model.Module module) {
        this.storeStyleHomeFragment = storeStyleHomeFragment;
        this.module = module;
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
    com.j1j2.data.model.Module module() {
        return module;
    }

    @Provides
    @ActivityScope
    StoreStyleHomeViewModel storeStyleHomeViewModel(StoreStyleHomeFragment storeStyleHomeFragment, ProductApi productApi, com.j1j2.data.model.Module module) {
        return new StoreStyleHomeViewModel(storeStyleHomeFragment, productApi, module);
    }


}
