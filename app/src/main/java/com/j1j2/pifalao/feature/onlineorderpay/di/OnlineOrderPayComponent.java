package com.j1j2.pifalao.feature.onlineorderpay.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.onlineorderpay.OnlineOrderPayActivity;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-5-8.
 */
@ActivityScope
@Subcomponent(modules = {OnlineOrderPayModule.class})
public interface OnlineOrderPayComponent {
    void inject(OnlineOrderPayActivity onlineOrderPayActivity);
}
