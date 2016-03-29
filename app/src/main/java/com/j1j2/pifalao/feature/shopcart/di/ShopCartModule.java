package com.j1j2.pifalao.feature.shopcart.di;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.feature.shopcart.ShopCartActivity;
import com.j1j2.pifalao.feature.shopcart.ShopCartViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-21.
 */
@Module
public class ShopCartModule {

    private ShopCartActivity shopCartActivity;
    private com.j1j2.data.model.Module module;

    public ShopCartModule(ShopCartActivity shopCartActivity, com.j1j2.data.model.Module module) {
        this.shopCartActivity = shopCartActivity;
        this.module = module;
    }

    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    CountDownApi countDownApi(Retrofit retrofit) {
        return retrofit.create(CountDownApi.class);
    }

    @Provides
    @ActivityScope
    ShopCartActivity shopCartActivity() {
        return shopCartActivity;
    }

    @Provides
    @ActivityScope
    com.j1j2.data.model.Module module() {
        return module;
    }

    @Provides
    @ActivityScope
    ShopCartViewModel shopCartViewModel(ShopCartApi shopCartApi, ShopCartActivity shopCartActivity, com.j1j2.data.model.Module module, CountDownApi countDownApi, ShopCart shopCart) {
        return new ShopCartViewModel(shopCartApi, shopCartActivity, module, countDownApi, shopCart);
    }
}
