package com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailModule;
import com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline.OrderDetailTimeLineFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-21.
 */
@ActivityScope
@Subcomponent(modules = {OrderDetailTimeLineModule.class})
public interface OrderDetailTimeLineComponent {
    void inject(OrderDetailTimeLineFragment fragment);
}
