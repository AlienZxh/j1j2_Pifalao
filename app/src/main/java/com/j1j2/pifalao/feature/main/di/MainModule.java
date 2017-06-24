package com.j1j2.pifalao.feature.main.di;

import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.main.MainActivityViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-16.
 */
@Module
public class MainModule {

    private MainActivity mainActivity;
    private ShopSubscribeService shopSubscribeService;

    public MainModule(MainActivity mainActivity, ShopSubscribeService shopSubscribeService) {
        this.mainActivity = mainActivity;
        this.shopSubscribeService = shopSubscribeService;
    }

    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    UserMessageApi userMessageApi(Retrofit retrofit) {
        return retrofit.create(UserMessageApi.class);
    }

    @Provides
    @ActivityScope
    MainActivity mainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    ShopSubscribeService module() {
        return shopSubscribeService;
    }


    @Provides
    @ActivityScope
    MainActivityViewModel mainActivityViewModel(MainActivity mainActivity, ShopCartApi shopCartApi, UserMessageApi userMessageApi) {
        return new MainActivityViewModel(mainActivity, shopCartApi, userMessageApi);
    }
}
