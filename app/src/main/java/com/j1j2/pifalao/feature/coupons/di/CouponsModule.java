package com.j1j2.pifalao.feature.coupons.di;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.coupons.CouponsActivity;
import com.j1j2.pifalao.feature.coupons.CouponsViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-23.
 */
@Module
public class CouponsModule {

    private CouponsActivity couponsActivity;


    public CouponsModule(CouponsActivity couponsActivity) {
        this.couponsActivity = couponsActivity;
    }

    @Provides
    @ActivityScope
    UserCouponApi userCouponApi(Retrofit retrofit) {
        return retrofit.create(UserCouponApi.class);
    }

    @Provides
    @ActivityScope
    CouponsActivity couponsActivity() {
        return couponsActivity;
    }


    @Provides
    @ActivityScope
    CouponsViewModel couponsViewModel(CouponsActivity couponsActivity, UserCouponApi userCouponApi) {
        return new CouponsViewModel(couponsActivity, userCouponApi);
    }

}
