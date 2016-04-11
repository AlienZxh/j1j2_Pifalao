package com.j1j2.pifalao.feature.couponselect.di;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.couponselect.CouponSelectActivity;
import com.j1j2.pifalao.feature.couponselect.CouponSelectViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-3.
 */
@Module
public class CouponSelectModule {

    private CouponSelectActivity couponSelectActivity;

    public CouponSelectModule(CouponSelectActivity couponSelectActivity) {
        this.couponSelectActivity = couponSelectActivity;
    }

    @Provides
    @ActivityScope
    UserCouponApi userCouponApi(Retrofit retrofit) {
        return retrofit.create(UserCouponApi.class);
    }

    @Provides
    @ActivityScope
    CouponSelectActivity couponSelectActivity() {
        return couponSelectActivity;
    }

    @Provides
    @ActivityScope
    CouponSelectViewModel couponSelectViewModel(CouponSelectActivity couponSelectActivity, UserCouponApi userCouponApi) {
        return new CouponSelectViewModel(couponSelectActivity, userCouponApi);
    }

}
