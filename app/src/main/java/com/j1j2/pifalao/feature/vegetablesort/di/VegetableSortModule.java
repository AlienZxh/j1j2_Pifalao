package com.j1j2.pifalao.feature.vegetablesort.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.vegetablesort.VegetableSortFragment;
import com.j1j2.pifalao.feature.vegetablesort.VegetableSortViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-6.
 */
@Module
public class VegetableSortModule {

    private VegetableSortFragment vegetableSortFragment;

    public VegetableSortModule(VegetableSortFragment vegetableSortFragment) {
        this.vegetableSortFragment = vegetableSortFragment;
    }


    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    VegetableSortFragment vegetableSortFragment() {
        return vegetableSortFragment;
    }

    @Provides
    @ActivityScope
    VegetableSortViewModel vegetableSortViewModel(VegetableSortFragment vegetableSortFragment, ProductApi productApi) {
        return new VegetableSortViewModel(vegetableSortFragment,productApi);
    }
}
