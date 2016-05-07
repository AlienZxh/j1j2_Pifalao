package com.j1j2.pifalao.feature.orderrate.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.orderrate.OrderRateActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-27.
 */
@ActivityScope
@Subcomponent(modules = {OrderRateModule.class})
public interface OrderRateComponent {
    void inject(OrderRateActivity orderRateActivity);
}
