package com.j1j2.pifalao.feature.orderproducts.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.ordermanager.OrderManagerActivity;
import com.j1j2.pifalao.feature.ordermanager.di.OrderManagerModule;
import com.j1j2.pifalao.feature.orderproducts.OrderProductsActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-2.
 */
@ActivityScope
@Subcomponent(modules = {OrderProductsModule.class})
public interface OrderProductsComponent {
    void inject(OrderProductsActivity orderProductsActivity);
}
