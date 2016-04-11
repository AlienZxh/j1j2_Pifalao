package com.j1j2.pifalao.feature.home.vegetablehome.di;

import com.j1j2.data.http.api.BannerActivityApi;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.vegetablehome.VegetableHomeFragment;
import com.j1j2.pifalao.feature.home.vegetablehome.VegetableHomeViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-5.
 */
@Module
public class VegetableHomeModule {

    private VegetableHomeFragment vegetableHomeFragment;

    public VegetableHomeModule(VegetableHomeFragment vegetableHomeFragment) {
        this.vegetableHomeFragment = vegetableHomeFragment;
    }

    @Provides
    @ActivityScope
    VegetableHomeFragment vegetableHomeFragment() {
        return vegetableHomeFragment;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    BannerActivityApi bannerActivityApi(Retrofit retrofit) {
        return retrofit.create(BannerActivityApi.class);
    }

    @Provides
    @ActivityScope
    VegetableHomeViewModel vegetableHomeViewModel(VegetableHomeFragment vegetableHomeFragment, ProductApi productApi, BannerActivityApi bannerActivityApi) {
        return new VegetableHomeViewModel(vegetableHomeFragment, productApi, bannerActivityApi);
    }
}
