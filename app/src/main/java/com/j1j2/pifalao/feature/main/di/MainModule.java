package com.j1j2.pifalao.feature.main.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeFragment;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeViewModel;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.main.MainActivity;

import dagger.Module;
import dagger.Provides;
import in.workarounds.bundler.Bundler;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-16.
 */
@Module
public class MainModule {

    private MainActivity mainActivity;
    private com.j1j2.data.model.Module module;

    public MainModule(MainActivity mainActivity,  com.j1j2.data.model.Module module) {
        this.mainActivity = mainActivity;
        this.module = module;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    MainActivity mainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    com.j1j2.data.model.Module module() {
        return module;
    }

//    @Provides
//    @ActivityScope
//    StoreStyleHomeFragment storeStyleHomeFragment() {
//        return storeStyleHomeFragment;
//    }
//
//    @Provides
//    @ActivityScope
//    StoreStyleHomeViewModel storeStyleHomeFragmentViewModel(StoreStyleHomeFragment storeStyleHomeFragment, ProductApi productApi, com.j1j2.data.model.Module module) {
//        return new StoreStyleHomeViewModel(storeStyleHomeFragment, productApi, module);
//    }
//
//    @Provides
//    @ActivityScope
//    IndividualCenterFragment individualCenterFragment() {
//        return Bundler.individualCenterFragment().create();
//    }
}
