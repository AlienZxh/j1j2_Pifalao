package com.j1j2.pifalao.feature.couponselect.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.coupons.di.CouponsModule;
import com.j1j2.pifalao.feature.couponselect.CouponSelectActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-3.
 */
@ActivityScope
@Subcomponent(modules = {CouponSelectModule.class})
public interface CouponSelectComponent {
    void inject(CouponSelectActivity couponSelectActivity);
}
