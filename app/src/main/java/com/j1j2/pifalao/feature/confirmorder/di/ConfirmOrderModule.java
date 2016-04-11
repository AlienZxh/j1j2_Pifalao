package com.j1j2.pifalao.feature.confirmorder.di;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.confirmorder.ConfirmOrderActivity;
import com.j1j2.pifalao.feature.confirmorder.ConfirmOrderViewModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-21.
 */
@Module
public class ConfirmOrderModule {

    private ConfirmOrderActivity confirmOrderActivity;
    private List<ShopCartItem> shopCartItems;

    public ConfirmOrderModule(ConfirmOrderActivity confirmOrderActivity, List<ShopCartItem> shopCartItems) {
        this.confirmOrderActivity = confirmOrderActivity;
        this.shopCartItems = shopCartItems;
    }

    @Provides
    @ActivityScope
    ConfirmOrderActivity confirmOrderActivity(Retrofit retrofit) {
        return confirmOrderActivity;
    }

    @Provides
    @ActivityScope
    List<ShopCartItem> shopCartItems(Retrofit retrofit) {
        return shopCartItems;
    }

    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    UserAddressApi userAddressApi(Retrofit retrofit) {
        return retrofit.create(UserAddressApi.class);
    }

    @Provides
    @ActivityScope
    UserCouponApi userCouponApi(Retrofit retrofit) {
        return retrofit.create(UserCouponApi.class);
    }

    @Provides
    @ActivityScope
    CountDownApi countDownApi(Retrofit retrofit) {
        return retrofit.create(CountDownApi.class);
    }


    @Provides
    @ActivityScope
    ConfirmOrderViewModel confirmOrderViewModel(ConfirmOrderActivity confirmOrderActivity, ShopCartApi shopCartApi, List<ShopCartItem> shopCartItems, CountDownApi countDownApi, UserCouponApi userCouponApi, UserAddressApi userAddressApi) {
        return new ConfirmOrderViewModel(confirmOrderActivity, shopCartApi, countDownApi, userCouponApi, userAddressApi, shopCartItems);
    }
}
