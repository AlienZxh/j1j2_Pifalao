package com.j1j2.pifalao.feature.showorders.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.showorders.ShowOrderListActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-9-18.
 */
@ActivityScope
@Subcomponent(modules = {ShowOrdersModule.class})
public interface ShowOrdersComponent {
    void inject(ShowOrderListActivity showOrderListActivity);
}
