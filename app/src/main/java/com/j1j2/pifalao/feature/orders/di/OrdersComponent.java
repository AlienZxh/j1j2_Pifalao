package com.j1j2.pifalao.feature.orders.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.ordermanager.di.OrderManagerModule;
import com.j1j2.pifalao.feature.orders.OrdersActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-22.
 */
@ActivityScope
@Subcomponent(modules = {OrdersModule.class})
public interface OrdersComponent {
    void inject(OrdersActivity ordersActivity);
}
