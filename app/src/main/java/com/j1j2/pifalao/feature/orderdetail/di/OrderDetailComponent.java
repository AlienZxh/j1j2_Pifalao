package com.j1j2.pifalao.feature.orderdetail.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-22.
 */
@ActivityScope
@Subcomponent(modules = {OrderDetailModule.class})
public interface OrderDetailComponent {

    void inject(OrderDetailActivity orderDetailActivity);

}
