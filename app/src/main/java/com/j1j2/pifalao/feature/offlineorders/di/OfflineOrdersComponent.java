package com.j1j2.pifalao.feature.offlineorders.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.offlineorders.OfflineOrdersActivity;
import com.j1j2.pifalao.feature.orders.OrdersActivity;
import com.j1j2.pifalao.feature.orders.di.OrdersModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-14.
 */
@ActivityScope
@Subcomponent(modules = {OfflineOrdersModule.class})
public interface OfflineOrdersComponent {
    void inject(OfflineOrdersActivity offlineOrdersActivity);
}
