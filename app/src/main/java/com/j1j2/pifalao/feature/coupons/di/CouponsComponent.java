package com.j1j2.pifalao.feature.coupons.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.coupons.CouponsActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-23.
 */
@ActivityScope
@Subcomponent(modules = {CouponsModule.class})
public interface CouponsComponent {
    void inject(CouponsActivity couponsActivity);
}
